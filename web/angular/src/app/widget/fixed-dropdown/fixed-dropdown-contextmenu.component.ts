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

import { DOCUMENT } from "@angular/common";
import {
   ChangeDetectorRef,
   Component,
   ElementRef,
   Inject,
   OnDestroy,
   OnInit,
   Renderer2
} from "@angular/core";
import { FixedDropdownComponent } from "./fixed-dropdown.component";
import { DropdownStackService } from "./dropdown-stack.service";

@Component({
   selector: "fixed-dropdown-contextmenu",
   templateUrl: "fixed-dropdown-contextmenu.component.html"
})
export class FixedDropdownContextmenuComponent
   extends FixedDropdownComponent implements OnInit, OnDestroy
{
   private readonly contextmenuFn = (e: Event) => this.closeContextmenuEvent(e);
   private readonly scrollFn = (e: any) => {
      if(e.detail) {
         this.closeContextmenuEvent(e);
      }
   };

   constructor(protected hostRef: ElementRef,
               protected renderer: Renderer2,
               protected dropdownService: DropdownStackService,
               @Inject(DOCUMENT) protected document: Document,
               public changeDetectorRef: ChangeDetectorRef)
   {
      super(hostRef, renderer, document, dropdownService);
   }

   ngOnInit() {
      super.ngOnInit();
      this.document.addEventListener("contextmenu", this.contextmenuFn, true);
      this.document.addEventListener("touchstart", this.contextmenuFn, true);
      this.document.addEventListener("scroll", this.scrollFn, true);
   }

   ngOnDestroy() {
      super.ngOnDestroy();
      this.document.removeEventListener("contextmenu", this.contextmenuFn, true);
      this.document.removeEventListener("touchstart", this.contextmenuFn, true);
      this.document.removeEventListener("scroll", this.scrollFn, true);
   }

   closeContextmenuEvent(event: Event) {
      if(!this.elementContainsTarget(event)) {
         this.tryClose();
      }
      else {
         event.preventDefault();
      }
   }
}
