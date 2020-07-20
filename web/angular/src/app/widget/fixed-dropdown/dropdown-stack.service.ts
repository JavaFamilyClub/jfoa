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

import { Injectable } from "@angular/core";
import { FixedDropdownComponent } from "./fixed-dropdown.component";

/**
 * Tracks dropdown stack. Separate from FixedDropdownService to avoid recursive dependency.
 */
@Injectable()
export class DropdownStackService {
   private dropdowns: Array<any> = [];

   public push(dropdown: FixedDropdownComponent): void {
      this.dropdowns.push(dropdown);
   }

   public pop(dropdown: FixedDropdownComponent): void {
      if(this.isCurrent(dropdown)) {
         this.dropdowns.pop();
      }
      else {
         // this shouldn"t happen
         console.log("Pop dropdown is not on top, ignored!");
      }
   }

   // check if the dropdown is on top
   public isCurrent(dropdown: FixedDropdownComponent): boolean {
      return this.dropdowns[this.dropdowns.length - 1] == dropdown;
   }
}
