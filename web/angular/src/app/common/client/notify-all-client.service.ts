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

import { Injectable, OnDestroy } from "@angular/core";
import { Subject } from "rxjs";
import { NotifyAllEvent } from "../../em/dialog/notify-all-event";
import { EmUrlConstants } from "../constants/url/em-url-constants";
import { EventConstants } from "../constants/url/event-constants";
import { ProjectEvent } from "../socket/project-event";
import { SocketClient } from "../socket/socket-client";
import { SocketClientService } from "../socket/socket-client-service";
import { StompClientConnection } from "../socket/stomp-client-connection";
import { StompMessage } from "../socket/stomp-message";
import { NotificationChannel } from "./notification-channel";

@Injectable({
   providedIn: "root"
})
export class NotifyAllClientService implements OnDestroy {
   private connecting: boolean = false;
   private connection: StompClientConnection;
   private messageSubject = new Subject<string>();
   private messageChannel = new NotificationChannel(this.messageSubject);

   constructor(private socketClientService: SocketClientService)
   {
      this.connect();
   }

   ngOnDestroy(): void {
      if(!!this.connection) {
         this.connection.disconnect();
      }

      if(!!this.messageSubject) {
         this.messageSubject.unsubscribe();
      }
   }

   private getHeaders(): any {
      const headers: any = {
      };

      return headers;
   }

   connect(): void {
      if(!this.connecting && !this.connection) {
         this.connecting = true;
         this.socketClientService.connect("../jf-websocket-channel").subscribe((client) => {
            this.connecting = false;
            this.connection = client;
            this.subscribe();
         }, (error) => {
            console.error("Failed to connect to server: ", error);
         });
      }
   }

   notify(notification: string): void {
      const event = new NotifyAllEvent(notification);

      this.sendEvent(EmUrlConstants.NOTIFY_ALL, event);
   }

   sendEvent(url: string, event?: ProjectEvent): void {
      const body = SocketClient.parseProjectEvent(event);

      if(this.connection) {
         this.connection.send(EventConstants.APP_EVENT_PREFIX + url, this.getHeaders(), body);
      }
      else {
         console.log("No connect to server.");
      }
   }

   subscribe(): void {
      this.connection.subscribe(EventConstants.NOTIFY_ALL_TOPIC, (message: StompMessage) => {
         this.messageSubject.next(message.frame.body);
      });
   }

   getMessageChannel(): NotificationChannel {
      return this.messageChannel;
   }

}
