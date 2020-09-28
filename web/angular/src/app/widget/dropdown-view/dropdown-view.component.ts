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

import {
   Component,
   ContentChild,
   ElementRef,
   EventEmitter,
   Input,
   Output,
   Renderer2,
   TemplateRef,
   ViewChild
} from "@angular/core";
import { FixedDropdownDirective } from "../directive/fixed-dropdown.directive";

@Component({
   selector: "dropdown-view",
   templateUrl: "dropdown-view.component.html",
   styleUrls: ["dropdown-view.component.scss"]
})
export class DropdownView {
   @Input() label: string;
   @Input() isOk: boolean = false;
   @Input() disabled: boolean = false;
   @Output() onToggle = new EventEmitter<string>();
   @Output() closed = new EventEmitter<boolean>();
   @ViewChild(FixedDropdownDirective, { static: true }) dropdown: FixedDropdownDirective;

   constructor(private elem: ElementRef, private renderer: Renderer2) {
   }

   public close(): void {
      this.dropdown.close();
   }

   public toggled(open: boolean): void {
      this.onToggle.emit(open.toString());

      if(!open) {
         this.closed.emit(true);
      }
   }
}
