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

import { Directive, Output, EventEmitter, ElementRef, AfterViewInit } from "@angular/core";

@Directive({
   selector: "[touchScroll]"
})
export class TouchScrollDirective implements AfterViewInit {
   @Output() vScroll: EventEmitter<number> = new EventEmitter<number>();
   @Output() hScroll: EventEmitter<number> = new EventEmitter<number>();
   @Output() scrollEnd: EventEmitter<any> = new EventEmitter<any>();

   touchmove = (event) => this.touchMove(event);
   touchend = (event) => this.touchEnd(event);
   downX: number;
   downY: number;

   constructor(private host: ElementRef) {
   }

   ngAfterViewInit() {
      const elem = this.host.nativeElement;
      elem.addEventListener("touchstart", (event) => this.touchStart(event), true);
   }

   touchStart(event: TouchEvent) {
      const elem = this.host.nativeElement;
      this.downX = event.touches[0].pageX;
      this.downY = event.touches[0].pageY;
      elem.addEventListener("touchmove", this.touchmove, true);
      elem.addEventListener("touchend", this.touchend, true);
   }

   touchMove(event: TouchEvent) {
      event.preventDefault();
      const elem = this.host.nativeElement;
      const deltaX = event.touches[0].pageX - this.downX;
      const deltaY = event.touches[0].pageY - this.downY;
      this.downX = event.touches[0].pageX;
      this.downY = event.touches[0].pageY;

      this.vScroll.emit(deltaY);
      this.hScroll.emit(deltaX);
   }

   touchEnd(event: TouchEvent) {
      const elem = this.host.nativeElement;
      elem.removeEventListener("touchmove", this.touchmove, true);
      elem.removeEventListener("touchend", this.touchend, true);
   }
}
