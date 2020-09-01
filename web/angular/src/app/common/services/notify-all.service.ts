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

import { Injectable } from "@angular/core";
import { SocketClient } from "../socket/socket-client";
import { SocketClientService } from "../socket/socket-client-service";

const COMMANDS_TOPIC = "/jf-commands";
const NOTIFY_ALL_EVENT = COMMANDS_TOPIC + "/notify-all";

@Injectable
export class NotifyAllService {

   client: SocketClient;

   constructor(private clientService: SocketClientService) {
      this.clientService.connect(NOTIFY_ALL_EVENT).subscribe(client => {
         this.client = client;
      });
   }

}
