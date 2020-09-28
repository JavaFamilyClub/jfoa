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

import { Directive, HostListener, Input, TemplateRef } from "@angular/core";

import { FixedDropdownService } from "./fixed-dropdown.service";
import { DropdownOptions } from "./dropdown-options";
import { DropdownRef } from "./fixed-dropdown-ref";

@Directive({
   selector: "[contextmenuDropdownAnchor]"
})
export class ContextmenuDropdownAnchorDirective {
   @Input() contextmenuRef: TemplateRef<any>;
   private dropdownRef: DropdownRef;

   constructor(private dropdownService: FixedDropdownService) {
   }

   @HostListener("contextmenu", ["$event"])
   onContextmenu(e: MouseEvent) {
      e.preventDefault();
      e.stopPropagation();

      let options: DropdownOptions = {
         position: {x: e.clientY, y: e.clientY},
         contextmenu: true
      };

      this.dropdownRef = this.dropdownService.open(this.contextmenuRef, options);
   }

   close() {
      if(!!this.dropdownRef) {
         this.dropdownRef.close();
         this.dropdownRef = null;
      }
   }
}
