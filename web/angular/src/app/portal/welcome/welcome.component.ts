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

import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { Tool } from "../../common/util/tool";

@Component({
   selector: "portal-welcome",
   templateUrl: "welcome.component.html",
   styleUrls: ["welcome.component.scss"]
})
export class WelcomeComponent implements OnInit {
   @ViewChild("welcomeFrame", {static: true}) welcomeFrame: ElementRef;

   ngOnInit(): void {
      if(!!this.welcomeFrame) {
         this.welcomeFrame.nativeElement.setAttribute("src", Tool.DOC_URL);
      }
      else {
         console.warn("Welcome page loadding failed.");
      }
   }
}
