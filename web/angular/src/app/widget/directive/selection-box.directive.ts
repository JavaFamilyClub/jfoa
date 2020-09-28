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
   NgZone,
   OnDestroy,
   OnInit,
   Output,
   Renderer2
} from "@angular/core";
import { Rectangle } from "../../common/data/rectangle";
import { GuiTool } from "../../common/util/gui-tool";
import { DomService } from "../dom-service/dom.service";
import matches = GuiTool.matches;

export type SelectionBoxEvent = (MouseEvent|TouchEvent) & {box: Rectangle, clientRect: ClientRect};

@Directive({
   selector: "[selectionBox]"
})
export class SelectionBoxDirective implements OnInit, OnDestroy {
   @Input() selectionBoxClass: string = "selection-box";
   @Input() selectionBoxBannedSelector: string;
   @Input() selectionBoxScale: number = 1;
   @Input() enableTouch: boolean = false;
   @Input() selectOnMouseMove = false;
   @Output() onSelectionBox = new EventEmitter<SelectionBoxEvent>();
   @Output() onSelectionPoint = new EventEmitter<SelectionBoxEvent>();
   private selectionBox: HTMLDivElement;

   // selectionBox dimensions
   private top: number;
   private left: number;
   private width: number;
   private height: number;

   // initial values
   private initialTop: number;
   private initialLeft: number;
   private downX: number;
   private downY: number;

   // renderer window listeners
   private listeners: (() => void)[] = [];

   constructor(private zone: NgZone, private hostRef: ElementRef,
               private renderer: Renderer2,
               private domService: DomService)
   {
   }

   ngOnInit() {
      this.selectionBox = this.renderer.createElement("div");
      this.renderer.addClass(this.selectionBox, this.selectionBoxClass);
      this.hideSelectionBox();
      this.renderer.appendChild(this.hostRef.nativeElement, this.selectionBox);
   }

   ngOnDestroy() {
      this.removeListeners();
      this.renderer.removeChild(this.hostRef.nativeElement, this.selectionBox);
      this.domService.cancelAnimationFrame();
   }

   @HostListener("touchstart", ["$event"])
   @HostListener("mousedown", ["$event"])
   mouseDown(event: MouseEvent|TouchEvent) {
      if(GuiTool.isButton1(event) && (!GuiTool.isTouch(event) || this.enableTouch) &&
         this.allowedElement(event.target as HTMLElement))
      {
         const hostElement = this.hostRef.nativeElement;
         const rect = hostElement.getBoundingClientRect();

         this.downX = GuiTool.clientX(event);
         this.downY = GuiTool.clientY(event);

         this.initialTop = (this.downY + hostElement.scrollTop - rect.top);
         this.initialLeft = (this.downX + hostElement.scrollLeft - rect.left);

         this.resetDimensions();
         this.updateDimensions();
         this.addListeners(GuiTool.isTouch(event));
      }
   }

   private mouseMove(event: MouseEvent|TouchEvent, rect: ClientRect, scrollLeft: number,
                     scrollTop: number)
   {
      event.preventDefault();
      const clientX = GuiTool.clientX(event);
      const clientY = GuiTool.clientY(event);
      const width = clientX + scrollLeft - rect.left - this.initialLeft;
      const height = clientY + scrollTop - rect.top - this.initialTop;

      if(clientX != this.downX || clientY != this.downY) {
         this.width = (Math.abs(width) + 1) / this.selectionBoxScale;
         this.height = (Math.abs(height) + 1) / this.selectionBoxScale;
      }

      this.top = Math.min(height + this.initialTop, this.initialTop) / this.selectionBoxScale;
      this.left = Math.min(width + this.initialLeft, this.initialLeft) / this.selectionBoxScale;

      this.updateDimensions();
      this.showSelectionBox();

      if(this.selectOnMouseMove) {
         this.updateSelection(event, rect);
      }
   }

   private updateSelection(event: MouseEvent|TouchEvent, clientRect: ClientRect) {
      const box = new Rectangle(this.left, this.top, this.width, this.height);
      const selectionEvent = Object.assign(event, {box, clientRect});

      if(!box.isEmpty()) {
         this.onSelectionBox.emit(selectionEvent);
      }
      else {
         this.onSelectionPoint.emit(selectionEvent);
      }
   }

   private mouseUp(event: MouseEvent|TouchEvent) {
      const clientRect = this.selectionBox.getBoundingClientRect();
      this.updateSelection(event, clientRect);
      this.hideSelectionBox();
      this.removeListeners();
   }

   private resetDimensions() {
      this.top = this.initialTop / this.selectionBoxScale;
      this.left = this.initialLeft / this.selectionBoxScale;
      this.width = 0;
      this.height = 0;
   }

   private showSelectionBox() {
      this.renderer.setStyle(this.selectionBox, "display", "block");
   }

   private hideSelectionBox() {
      this.domService.requestWrite(() => {
         this.renderer.setStyle(this.selectionBox, "display", "none");
      });
   }

   private updateDimensions() {
      this.renderer.setStyle(this.selectionBox, "top", this.top + "px");
      this.renderer.setStyle(this.selectionBox, "left", this.left + "px");
      this.renderer.setStyle(this.selectionBox, "width", this.width + "px");
      this.renderer.setStyle(this.selectionBox, "height", this.height + "px");
   }

   private allowedElement(element: HTMLElement): boolean {
      if(!element) {
         return false;
      }

      do {
         if(element.draggable || matches(element, this.selectionBoxBannedSelector)) {
            return false;
         }

         if(element === this.hostRef.nativeElement) {
            return true;
         }

         element = element.parentElement ? element.parentElement : element;
      }
      while(element.parentElement != null);

      return true;
   }

   private addListeners(touch: boolean) {
      if(touch) {
         this.zone.runOutsideAngular(() => {
            this.listeners.push(this.renderer.listen(
               "window", "touchmove", (event) => {
                  this.domService.requestRead(() => {
                     const hostElement = this.hostRef.nativeElement;
                     const rect = hostElement.getBoundingClientRect();
                     this.mouseMove(event, rect, hostElement.scrollLeft, hostElement.scrollTop);
                  });
               }));
         });
         this.listeners.push(this.renderer.listen(
            "window", "touchend", (event) => this.mouseUp(event)));
      }
      else {
         this.zone.runOutsideAngular(() => {
            this.listeners.push(this.renderer.listen("window", "mousemove", (event) => {
               this.domService.requestRead(() => {
                  const hostElement = this.hostRef.nativeElement;
                  const rect = hostElement.getBoundingClientRect();
                  this.mouseMove(event, rect, hostElement.scrollLeft, hostElement.scrollTop);
               });
            }));
         });
         this.listeners.push(this.renderer.listen(
            "window", "mouseup", (event) => this.mouseUp(event)));
      }
   }

   private removeListeners() {
      this.listeners.forEach((listener) => listener());
      this.listeners = [];
   }
}
