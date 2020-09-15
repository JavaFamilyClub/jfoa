/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import { Observable, Subject, of as observableOf, AsyncSubject } from "rxjs";
import { Tool } from "../util/tool";
import { ProjectEvent } from "./project-event";
import { StompClientChannel } from "./stomp-client-channel";
import { StompClientConnection } from "./stomp-client-connection";

const SockJS = require("sockjs-client");
const Stomp = require("stompjs");

export class SocketClient {
   stompClient: Stomp.Client;
   private clientSubject = new Subject<Stomp.Client>();
   private clientChannel = new StompClientChannel(this.clientSubject);
   private referenceCount = 0;
   private connected = false;
   private pendingConnections: Subject<StompClientConnection>[] = [];

   constructor(private endpoint: string,
               private onDisconnect: (endpoint: string) => any)
   {
      const socket = new SockJS(resolveURL(this.endpoint));
      this.stompClient = Stomp.over(socket);

      this.stompClient.maxWebSocketFrameSize = 64 * 1024;
      this.stompClient.debug = null;

      this.stompClient.connect({}, (frame) => {
         console.log("Connected: " + frame);

         this.clientSubject.next(this.stompClient);

         this.pendingConnections.forEach((sub) => {
            sub.next(this.createConnection());
            sub.complete();
         });
         this.pendingConnections = null;

         this.connected = true;
      }, (error) => {
         console.log("WebSocket Error: ", error);

         if(!!this.pendingConnections) {
            console.error("Disconnected from server: ", error);
            this.pendingConnections.forEach((sub) => {
               sub.error(error);
            });

            this.connected = false;
            this.pendingConnections = [];
            this.onDisconnect(this.endpoint);
            this.clientSubject.next(null);
            this.clientSubject.complete();
         }
      });
   }

   connect(): Observable<StompClientConnection> {
      this.referenceCount = this.referenceCount + 1;

      if(this.connected) {
         return observableOf(this.createConnection());
      }
      else {
         const subject: AsyncSubject<StompClientConnection> =
            new AsyncSubject<StompClientConnection>();
         this.pendingConnections.push(subject);
         return subject.asObservable();
      }
   }

   private createConnection(): StompClientConnection {
      return new StompClientConnection(
         this.clientChannel, this.onConnectionDisconnect);
   }

   private onConnectionDisconnect(): void {
      this.referenceCount = this.referenceCount - 1;

      if(this.referenceCount <= 0) {
         this.connected = false;
         this.stompClient.disconnect(() => {});
         this.stompClient = null;
         this.clientSubject.next(null);
         this.clientSubject.complete();
         this.onDisconnect(this.endpoint);
      }
   }

   public static parseProjectEvent(event: ProjectEvent): string {
      let dto: {[name: string]: string} = {};

      for(let property in event) {
         if(event.hasOwnProperty(property)) {
            let value: any = event[property];

            if((typeof value) !== "function") {
               dto[property] = value;
            }
         }
      }

      return JSON.stringify(dto);
   }

}

function resolveURL(url: string): string {
   let base: HTMLBaseElement = <HTMLBaseElement> window.document.querySelector("base");
   const installer = Tool.isInstaller();

   if(installer) {
      url = url.startsWith("../") ? url.substring(3) : url;
      return Tool.INSTALLER_URI + url;
   }

   let baseHref: string = base.href.replace(/\/$/, "");
   return baseHref + "/" + url;
}
