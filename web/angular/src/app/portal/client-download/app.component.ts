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

import { Component } from "@angular/core";
import { Platform } from "../../common/enum/platform";
import { Tool } from "../../common/util/tool";

@Component({
  selector: "app-client-download",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class ClientDownloadAppComponent {
   platform: Platform = Tool.userPlatform();
   Platform = Platform;

   docUrl = Tool.DOC_URL;

   selectPlatform(platform: Platform): void {
      this.platform = platform;
   }

   get images(): {src: string, alt: string}[] {
      return [
         {
            src: "assets/img/demo-index.jpg",
            alt: "Screenshot of jfoa index"
         },
         {
            src: "assets/img/demo-subject-request-m.jpg",
            alt: "Screenshot of jfoa subject request monitor"
         },
         {
            src: "assets/img/demo-subject-request.jpg",
            alt: "Screenshot of jfoa subject request"
         },
         {
            src: "assets/img/demo-login.jpg",
            alt: "Screenshot of jfoa login"
         },
         {
            src: "assets/img/demo-em-client.jpg",
            alt: "Screenshot of jfoa client"
         },
         {
            src: "assets/img/demo-user-m.jpg",
            alt: "Screenshot of jfoa user monitor"
         },
         {
            src: "assets/img/demo-role-m.jpg",
            alt: "Screenshot of jfoa role monitor"
         },
         {
            src: "assets/img/demo-em-audit.jpg",
            alt: "Screenshot of jfoa audit"
         },
         {
            src: "assets/img/demo-dev-doc.jpg",
            alt: "Screenshot of jfoa user dev doc"
         }
      ];
   }
}
