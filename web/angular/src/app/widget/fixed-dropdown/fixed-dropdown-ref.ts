/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

import { ComponentRef, EventEmitter } from "@angular/core";
import { Point } from "../../common/data/point";
import { Rectangle } from "../../common/data/rectangle";
import { FixedDropdownComponent } from "./fixed-dropdown.component";
import { ContentRef } from "./fixed-dropdown.service";
import { DropdownStackService } from "./dropdown-stack.service";

/**
 * Class which contains a reference to the dynamically created dropdown component.
 */
export class DropdownRef {
   private _closed: boolean = false;

   constructor(private dropdownCmptRef: ComponentRef<FixedDropdownComponent>,
               private stackService: DropdownStackService,
               private contentRef: ContentRef)
   {
      const dropdown = dropdownCmptRef.instance;

      dropdown.onClose.subscribe(() => {
         this.close();
      });

      dropdown.onOpen.subscribe(() => {
         const elem = this.dropdownCmptRef.location.nativeElement;
         const bounds = elem.getBoundingClientRect();
         const width = bounds.width > 0 ? bounds.width : elem.scrollWidth;
         const height = bounds.height > 0 ? bounds.height : elem.scrollHeight;
         dropdown.dropdownBounds = new Rectangle(bounds.left, bounds.top, width, height);
         this.stackService.push(dropdown);
      });
   }

   /**
    * The instance of component used as dropdown"s content.
    * Undefined when a TemplateRef is used as dropdown"s content.
    */
   get componentInstance(): any {
      if(this.contentRef.componentRef) {
         return this.contentRef.componentRef.instance;
      }
   }

   get dropdownInstance(): FixedDropdownComponent {
      return this.dropdownCmptRef.instance;
   }

   get closed(): boolean {
      return this._closed;
   }

   public close() {
      if(!this._closed) {
         this._closed = true;
         this.removeDropdownElements();
         this.stackService.pop(this.dropdownInstance);
      }
   }

   public get closeEvent(): EventEmitter<any> {
      return this.dropdownCmptRef.instance.onClose;
   }

   public set dropdownPosition(point: Point) {
      this.dropdownCmptRef.instance.dropdownBounds = new Rectangle(point.x, point.y, 0, 0);
   }

   private removeDropdownElements() {
      const dropdownNativeEl = this.dropdownCmptRef.location.nativeElement;

      if(!!dropdownNativeEl.parentNode) {
         dropdownNativeEl.parentNode.removeChild(dropdownNativeEl);
      }

      this.dropdownCmptRef.destroy();

      if(this.contentRef && this.contentRef.viewRef) {
         this.contentRef.viewRef.destroy();
      }

      this.contentRef = null;
   }
}
