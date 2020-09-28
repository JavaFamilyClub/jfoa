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
   Directive,
   ElementRef,
   EventEmitter,
   HostListener,
   Input,
   OnDestroy,
   Output,
   TemplateRef
} from "@angular/core";
import { Point } from "../../common/data/point";
import { DropdownOptions } from "../fixed-dropdown/dropdown-options";
import { DropdownRef } from "../fixed-dropdown/fixed-dropdown-ref";
import { FixedDropdownService } from "../fixed-dropdown/fixed-dropdown.service";

@Directive({
   selector: "[fixedDropdown]"
})
export class FixedDropdownDirective implements OnDestroy {
   @Input() fixedDropdown: TemplateRef<any>;
   @Input() dropdownPlacement: "mouse" | "bottom" | "right" = "mouse";
   @Input() disabled: boolean = false;
   @Output() openChange = new EventEmitter<boolean>();
   private _autoClose: boolean = true;
   private _closeOnOutsideClick: boolean = true;
   private _closeOnWindowResize: boolean = true;
   private _zIndex: number = null;
   private dropdown: DropdownRef = null;
   private isDropdownOpenOnMousedown: boolean;

   constructor(private dropdownService: FixedDropdownService,
               private elementRef: ElementRef)
   {
   }

   ngOnDestroy() {
      this.close();
   }

   @Input()
   set autoClose(flag: boolean) {
      this._autoClose = flag;

      if(this.dropdown) {
         this.dropdown.dropdownInstance.autoClose = this.autoClose;
      }
   }

   get autoClose(): boolean {
      return this._autoClose;
   }

   @Input()
   set zIndex(flag: number) {
      this._zIndex = flag;

      if(this.dropdown) {
         this.dropdown.dropdownInstance.zIndex = this.zIndex;
      }
   }

   get zIndex(): number {
      return this._zIndex;
   }

   @Input()
   set closeOnOutsideClick(flag: boolean) {
      this._closeOnOutsideClick = flag;

      if(this.dropdown) {
         this.dropdown.dropdownInstance.closeOnOutsideClick = this.closeOnOutsideClick;
      }
   }

   get closeOnOutsideClick(): boolean {
      return this._closeOnOutsideClick;
   }

   @Input()
   set closeOnWindowResize(flag: boolean) {
      this._closeOnWindowResize = flag;

      if(this.dropdown) {
         this.dropdown.dropdownInstance.closeOnWindowResize = this.closeOnWindowResize;
      }
   }

   get closeOnWindowResize(): boolean {
      return this._closeOnWindowResize;
   }

   @HostListener("mousedown")
   public mousedown() {
      // If dropdown is open on mousedown, don"t open it on click.
      this.isDropdownOpenOnMousedown = this.dropdown && !this.dropdown.closed;
   }

   @HostListener("click", ["$event"])
   public toggleDropdown(event: MouseEvent) {
      if(!this.disabled && (!this.dropdown || this.dropdown.closed) && !!this.fixedDropdown &&
         !this.isDropdownOpenOnMousedown)
      {
         const position = this.getDropdownPosition(event);
         const options: DropdownOptions = {
            position,
            contextmenu: false,
            autoClose: this.autoClose,
            closeOnOutsideClick: this.closeOnOutsideClick,
            closeOnWindowResize: this.closeOnWindowResize,
            zIndex: this.zIndex,
         };

         this.openChange.emit(true);
         this.dropdown = this.dropdownService.open(this.fixedDropdown, options);

         const sub = this.dropdown.closeEvent.subscribe(() => {
            this.openChange.emit(false);
            sub.unsubscribe();
         });
      }
      else if(this.dropdown) {
         this.dropdown.close();
      }
   }

   public close(): void {
      if(this.dropdown) {
         this.dropdown.close();
         this.dropdown = null;
      }
   }

   private getDropdownPosition(event: MouseEvent): Point {
      const box = this.elementRef.nativeElement.getBoundingClientRect();
      let position: Point;

      switch(this.dropdownPlacement) {
      case "mouse":
         position = {x: event.clientX, y: event.clientY};
         break;
      case "bottom":
         position = {x: box.left, y: box.bottom};
         break;
      case "right":
         position = {x: box.right, y: box.top};
         break;
      default:
         position = {x: 0, y: 0};
      }

      return position;
   }
}
