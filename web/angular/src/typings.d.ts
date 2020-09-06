/* tslint:disable */
/* SystemJS module definition */
declare var module: NodeModule;
interface NodeModule {
   id: string;
}

declare namespace Stomp {
   export function over(socket: any): Stomp.Client;

   export interface Client {
      connect(headers: any, connectCallback: (frame: Frame) => any, errorCallback?: (error: any) => any): void;
      disconnect(disconnectCallback: () => any): void;
      send(destination: string, headers: any, body: string): void;
      subscribe(destination: string, callback: (message: Stomp.Frame) => any, headers?: any): Stomp.Subscription;
      maxWebSocketFrameSize: number;
      connected: boolean;
      debug: any;
      ws: any; // SockJS
   }

   export interface Subscription {
      id: string;
      unsubscribe(): void;
   }

   export interface Frame {
      command: string;
      headers: any;
      body: string;
   }
}

declare class DocumentTouch {
}

declare module "*.json" {
   const value: any;
   export default value;
}

/* tslint:enable */
