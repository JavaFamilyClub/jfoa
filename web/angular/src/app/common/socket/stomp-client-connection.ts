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

import { Subscription } from "rxjs";
import { StompClientChannel } from "./stomp-client-channel";
import { StompMessage } from "./stomp-message";

/**
 * Class that encapsulates an active reference to a StompJS client.
 */
export class StompClientConnection {
   private subscriptions: Subscription[]  = [];

   constructor(private client: StompClientChannel,
               private onDisconnect: () => any)
   {
   }

   get transport(): string {
      return this.client.transport;
   }

   subscribe(destination: string, next?: (value: StompMessage) => void,
             error?: (error: any) => void, complete?: () => void): Subscription
   {
      const subscription = this.client.subscribe(destination, next, error, complete);
      subscription.add(() => {
         this.subscriptions.filter((sub) => sub != subscription);
      });
      this.subscriptions.push(subscription);

      return subscription;
   }

   send(destination: string, headers: any, body: string): void {
      this.client.send(destination, headers, body);
   }

   disconnect(): void {
      this.subscriptions.forEach((sub) => sub.unsubscribe());
      this.subscriptions = [];
      this.onDisconnect();
   }
}

