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

export class EventConstants {
   /**
    * App event prefix.
    */
   public static readonly APP_EVENT_PREFIX = "/jf-events";

   /**
    * jf stomp endpoint
    */
   public static readonly JF_STOMP_ENDPOINT = "/jf-websocket-channel";

   /**
    * JF commands topic.
    */
   public static readonly JF_COMMANDS_TOPIC = "/jf-commands";

   /**
    * notify all topic.
    */
   public static readonly NOTIFY_ALL_TOPIC = "/notifications";

}
