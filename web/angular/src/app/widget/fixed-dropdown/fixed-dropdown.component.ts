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

import { DOCUMENT } from "@angular/common";
import {
   AfterViewInit,
   Component,
   ElementRef,
   EventEmitter,
   HostBinding,
   Inject,
   Input,
   OnDestroy,
   OnInit,
   Output,
   Renderer2
} from "@angular/core";
import { Point } from "../../common/data/point";
import { Rectangle } from "../../common/data/rectangle";
import { GuiTool } from "../../common/util/gui-tool";
import { DropdownStackService } from "./dropdown-stack.service";

@Component({
   selector: "fixed-dropdown",
   templateUrl: "fixed-dropdown.component.html"
})
export class FixedDropdownComponent implements OnInit, AfterViewInit, OnDestroy {
   @Input() container: Element;
   @Input() autoClose: boolean = true;
   @Input() closeOnOutsideClick: boolean = true;
   @Input() closeOnWindowResize: boolean = true;
   @Input() zIndex: number;
   @Output() onOpen = new EventEmitter<void>(true);
   @Output() onClose = new EventEmitter<void>();
   @HostBinding("class.fixed-dropdown") fixedDropdownCSS: boolean = true;
   protected listeners: Function[] = [];
   private listenerTick: any;
   private dropdownPosition: Point;
   private mobile = GuiTool.isMobileDevice();

   constructor(protected elementRef: ElementRef,
               protected renderer: Renderer2,
               @Inject(DOCUMENT) protected document: Document,
               protected dropdownService: DropdownStackService)
   {
      this.dropdownPosition = new Point();
   }

   ngOnInit() {
      // Wait a tick as the event that may have triggered the dynamic creation of this
      // component may not have bubbled to the document layer yet
      this.listenerTick = setTimeout(
         () => {
            this.listeners.push(
               this.renderer.listen("document", "mousedown", (e) => this.documentMousedown(e)),
               this.renderer.listen("document", "click", (e) => this.documentClick(e)),
               this.renderer.listen("document", "keyup.esc", (e) => this.closeFromOutsideEsc(e)),
               this.renderer.listen("window", "resize", (e) => this.closeFromWindowResize(e)),
            );
         }, 0);
   }

   ngAfterViewInit() {
      this.onOpen.emit();
   }

   ngOnDestroy() {
      clearTimeout(this.listenerTick);
      this.listeners.forEach((callback) => callback());
   }

   @HostBinding("style.top.px")
   get topPosition() {
      return this.dropdownPosition.y;
   }

   @HostBinding("style.left.px")
   get leftPosition() {
      return this.dropdownPosition.x;
   }

   @HostBinding("style.z-index")
   get _zIndex() {
      return this.zIndex;
   }

   /**
    * Set the boundaries of the dropdown. If a container element defined then use those boundaries
    * to calculate the position
    */
   set dropdownBounds(dropdownBounds: Rectangle) {
      // set the position of the dropdown
      this.dropdownPosition = new Point(dropdownBounds.x, dropdownBounds.y);

      // adjust to the given container element
      if(this.container) {
         const containerElement = this.container;
         const restrictBounds = Rectangle.fromClientRect(containerElement.getBoundingClientRect());

         // Restricting to an empty rectangle (such as when the body size is 0) doesn"t really
         // make sense so use the viewport size instead
         if(restrictBounds.isEmpty()) {
            [restrictBounds.x, restrictBounds.y] = [0, 0];
            [restrictBounds.width, restrictBounds.height] = GuiTool.getViewportSize();
         }

         const dropdownRight = dropdownBounds.x + dropdownBounds.width;
         const dropdownBottom = dropdownBounds.y + dropdownBounds.height;
         const restrictRight = restrictBounds.x + restrictBounds.width;
         const restrictBottom = restrictBounds.y + restrictBounds.height;

         if(dropdownRight > restrictRight) {
            this.dropdownPosition.x = Math.max(0, restrictRight - dropdownBounds.width);
         }

         if(dropdownBottom > restrictBottom) {
            this.dropdownPosition.y = Math.max(0, restrictBottom - dropdownBounds.height);
         }
      }
   }

   // check if target is the backdrop of a modal dialog
   private isBackdrop(target: Element): boolean {
      return target.classList.contains("modal") && target.classList.contains("fade");
   }

   private isDropdown(element: Element): boolean {
      return GuiTool.parentContainsClass(element, "fixed-dropdown");
   }

   private documentMousedown(event: MouseEvent) {
      // wait until a newly popped up fixed dropdown to set the current dropdown so
      // the isCurrent() check would work properly
      setTimeout(() => this.documentMousedown0(event), 0);
   }

   private documentMousedown0(event: MouseEvent) {
      const target = event.target as Element;

      if(event.button !== 2 && this.closeOnOutsideClick && !this.elementContainsTarget(event) &&
         !this.isBackdrop(target) &&
         (!this.isDropdown(target) || this.dropdownService.isCurrent(this)))
      {
         this.onClose.emit();
         event.stopPropagation();
      }
   }

   private documentClick(event: MouseEvent) {
      const target = event.target as Element;

      if(event.button !== 2 && this.autoClose && this.dropdownService.isCurrent(this)) {
         this.onClose.emit();
         event.stopPropagation();
      }
   }

   private closeFromOutsideEsc(event: KeyboardEvent) {
      this.onClose.emit();
   }

   private closeFromWindowResize(event: Event) {
      // opening keyboard on mobile causes window resize
      if(this.closeOnWindowResize && !this.mobile) {
         this.onClose.emit();
      }
   }

   tryClose() {
      if(this.autoClose) {
         this.onClose.emit();
      }
   }

   protected elementContainsTarget(event: Event): boolean {
      return this.elementRef.nativeElement.contains(event.target);
   }
}
