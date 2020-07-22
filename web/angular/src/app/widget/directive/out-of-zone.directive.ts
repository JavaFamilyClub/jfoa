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

import {
   Directive,
   ElementRef,
   EventEmitter,
   NgZone,
   OnDestroy,
   OnInit,
   Output
} from "@angular/core";

@Directive({
   selector: "[outOfZone]"
})
export class OutOfZoneDirective implements OnInit, OnDestroy {
   @Output() onDragover = new EventEmitter<any>();
   @Output() onDragleave = new EventEmitter<any>();
   @Output() onMouseenter = new EventEmitter<any>();
   @Output() onMouseleave = new EventEmitter<any>();
   @Output() onMousedown = new EventEmitter<any>();
   @Output() onMouseup = new EventEmitter<any>();
   @Output() onDocMousemove = new EventEmitter<any>();
   @Output() onMousemove = new EventEmitter<any>();
   @Output() onMouseout = new EventEmitter<any>();
   @Output() onMouseover = new EventEmitter<any>();
   @Output() onScroll = new EventEmitter<any>();
   @Output() onDocKeydown = new EventEmitter<any>();
   @Output() onKeydown = new EventEmitter<any>();
   @Output() onClick = new EventEmitter<any>();

   docKeydown: (any) => void;
   docMousemove: (any) => void;

   constructor(private _ngZone: NgZone, private el: ElementRef) {
   }

   ngOnInit(): void {
      const handlers: any[] = [
         {event: "dragover", handler: this.onDragover},
         {event: "dragleave", handler: this.onDragleave},
         {event: "mouseenter", handler: this.onMouseenter},
         {event: "mouseleave", handler: this.onMouseleave},
         {event: "mousedown", handler: this.onMousedown},
         {event: "mouseup", handler: this.onMouseup},
         {event: "mousemove", handler: this.onMousemove},
         {event: "mouseout", handler: this.onMouseout},
         {event: "mouseover", handler: this.onMouseover},
         {event: "scroll", handler: this.onScroll},
         {event: "keydown", handler: this.onKeydown},
         {event: "click", handler: this.onClick}
      ];

      this._ngZone.runOutsideAngular(() => {
         const nativeElement = this.el.nativeElement;

         handlers.filter(e => e.handler.observers.length > 0).forEach(e => {
            const _handler: Function = $event => {
               e.handler.emit($event);
            };

            nativeElement.addEventListener(e.event, _handler, false);
         });

         if(this.onDocKeydown.observers.length > 0) {
            this.docKeydown = ($event) => this.onDocKeydown.emit($event);
            document.addEventListener("keydown", this.docKeydown, false);
         }

         if(this.onDocMousemove.observers.length > 0) {
            this.docMousemove = ($event) => this.onDocMousemove.emit($event);
            window.addEventListener("mousemove", this.docMousemove, false);
         }
      });
   }

   ngOnDestroy() {
      if(this.docKeydown) {
         document.removeEventListener("keydown", this.docKeydown);
      }

      if(this.docMousemove) {
         window.removeEventListener("mousemove", this.docMousemove);
      }
   }
}
