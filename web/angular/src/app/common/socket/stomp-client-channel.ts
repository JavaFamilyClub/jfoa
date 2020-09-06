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

import { Observable ,  Subject ,  Subscription } from "rxjs";
import { StompMessage } from "./stomp-message";
import { StompTopic } from "./stomp-topic";

/**
 * Wrapper for the StompJs client that handles multiplexing topics, queueing messages while
 * reconnecting, and resuming operation after reconnecting.
 */
export class StompClientChannel {
   private client: any = null;
   private topics = new Map<string, StompTopic>();

   get transport(): string {
      return this.client.ws.transport;
   }

   constructor(client: Observable<Stomp.Client>) {
      client.subscribe((c) => {
         this.client = c;

         if(this.client) {
            this.topics.forEach((topic, destination) => {
               topic.clientSubscription = this.subscribeToTopic(destination, topic);
            });
         }
      });
   }

   subscribe(destination: string,
             next?: (value: StompMessage) => void,
             error?: (error: any) => void,
             complete?: () => void): Subscription
   {
      let topic = this.topics.get(destination);

      if(!topic) {
         topic = {
            referenceCount: 0,
            subject: new Subject<StompMessage>()
         };
         this.topics.set(destination, topic);

         if(this.client) {
            topic.clientSubscription = this.subscribeToTopic(destination, topic);
         }
      }

      topic.referenceCount = topic.referenceCount + 1;
      const subscription = topic.subject.subscribe(next, error, complete);
      subscription.add(() => {
         topic.referenceCount = topic.referenceCount - 1;

         if(topic.referenceCount == 0) {
            topic.subject.complete();
            subscription.add(topic.clientSubscription);
            this.topics.delete(destination);
         }
      });

      return subscription;
   }

   send(destination: string, headers: any, body: string): void {
      if(this.client) {
         this.sendToQueue(destination, headers, body);
      }
   }

   private sendToQueue(destination: string, headers: any, body: string): void {
      if(body && body.length >= 32768) {
         try {
            const bodyObject = JSON.parse(body);
            console.warn(
               "Sending large STOMP message body (" + body.length + " bytes): ", bodyObject);
         }
         catch(ignore) {
            // not JSON
            console.warn("Sending large STOMP message body (" + body.length + " bytes)");
         }
      }

      this.client.send(destination, headers, body);
   }

   private subscribeToTopic(destination: string, topic: StompTopic): Stomp.Subscription {
      return this.client.subscribe(destination, (message: Stomp.Frame) => {
         topic.subject.next({ frame: message });
      });
   }
}
