/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import { Component, Input, Output, EventEmitter, ViewChildren, ElementRef,
         AfterViewInit, QueryList } from "@angular/core";

/**
 * Component that display a simple message or confirmation dialog.
 */
@Component({
   selector: "message-dialog",
   templateUrl: "message-dialog.component.html",
   styleUrls: ["message-dialog.component.scss"]
})
export class MessageDialog implements AfterViewInit {
   @Input() title = "";
   @Input() message = "";
   @Input() options: DialogOption[] = [];
   @Input() showProgress = false;
   @Input() expandValues: string[] = [];
   @Output() onCommit = new EventEmitter<string>();
   @ViewChildren("btn") buttons: QueryList<ElementRef>;

   static lastMessage: string;
   static lastMessageTS: number = 0;

   ngAfterViewInit() {
      if(this.buttons && this.buttons.first) {
         this.buttons.first.nativeElement.focus();
      }
   }

   clickButton(symbol: string) {
      this.onCommit.emit(symbol);
   }

   errorSource(): string {
      let url: string = "assets/error.png";
      return url;
   }
}

export interface DialogOption {
   symbol: string;
   label: string;
}
