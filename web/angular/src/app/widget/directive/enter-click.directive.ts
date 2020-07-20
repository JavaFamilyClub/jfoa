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

import { Directive, ElementRef, HostListener, Input } from "@angular/core";

@Directive({
   selector : "[enterClick]",
})
export class EnterClickDirective {
   @Input() hasKeys: boolean = false;

   constructor(private elementRef: ElementRef) {
   }

   /**
    * Trigger click event on the element if user clicks enter.
    */
   @HostListener("keydown", ["$event"])
   onEnter(event: KeyboardEvent): void {
      if(event.keyCode == 13) {
         if(this.hasKeys) {
            const init: MouseEventInit = {
               shiftKey: event.shiftKey,
               ctrlKey: event.ctrlKey,
               metaKey: event.metaKey
            };
            const click: Event = new MouseEvent("click", init);
            this.elementRef.nativeElement.dispatchEvent(click);
         }
         else {
            this.elementRef.nativeElement.click();
         }
      }
   }
}