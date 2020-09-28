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

import { Directive, ElementRef, AfterViewChecked, NgZone, OnDestroy } from "@angular/core";
import { DomService } from "../dom-service/dom.service";

@Directive({
   selector: "[tooltipIf]"
})
export class TooltipIfDirective implements AfterViewChecked, OnDestroy {
   private oneeded: boolean = null;

   constructor(private host: ElementRef,
               private zone: NgZone,
               private domService: DomService) {
   }

   ngAfterViewChecked() {
      this.domService.requestRead(() => {
         const elem = this.host.nativeElement;
         const needed: boolean = elem.scrollWidth > elem.clientWidth ||
            elem.scrollHeight > elem.clientHeight * 1.5;

         if(this.oneeded != needed) {
            const hasTitle = elem.hasAttribute("title");

            this.domService.requestWrite(() => {
               this.oneeded = needed;

               if(needed && !hasTitle) {
                  const title = elem.textContent ? elem.textContent : elem.value;

                  if(title && title.trim()) {
                     elem.setAttribute("title", title.trim());
                  }
                  else {
                     elem.removeAttribute("title");
                  }
               }
               else if(!needed && hasTitle) {
                  elem.removeAttribute("title");
               }
            });
         }
      });
   }

   ngOnDestroy() {
      this.domService.cancelAnimationFrame();
   }
}
