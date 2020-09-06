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

import { Injectable, NgZone } from "@angular/core";
import { Observable, of as observableOf } from "rxjs";
import { SocketClient } from "./socket-client";
import { StompClientConnection } from "./stomp-client-connection";

@Injectable({
   providedIn: "root"
})
export class SocketClientService {
   private clients: Map<string, SocketClient> = new Map<string, SocketClient>();

   constructor(private zone: NgZone) {
   }

   connect(endpoint: string): Observable<StompClientConnection> {
      return this.zone.runOutsideAngular(() => {
         let client = this.clients.get(endpoint);

         if(!client) {
            client = new SocketClient(endpoint, (key) => this.onDisconnect(key));
            this.clients.set(endpoint, client);
         }

         return client.connect();
      });
   }

   private onDisconnect(endpoint: string) {
      this.clients.delete(endpoint);
   }

}
