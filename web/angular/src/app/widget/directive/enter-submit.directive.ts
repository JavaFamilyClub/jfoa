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
   AfterContentInit,
   Directive,
   ElementRef,
   EventEmitter,
   Input,
   NgZone,
   OnDestroy,
   Output,
   Renderer2
} from "@angular/core";

@Directive({
   selector: "[enterSubmit]"
})
export class EnterSubmitDirective implements AfterContentInit, OnDestroy {
   @Input() enterSubmit: () => boolean;
   @Output() onEnter: EventEmitter<void> = new EventEmitter<void>();
   @Output() onEsc: EventEmitter<void> = new EventEmitter<void>();
   private keydownListener: () => void;
   // private enabled = true;
   // private keyDownListener: () => void;
   //
   // @Input()
   // set enterSubmit(value: boolean) {
   //    if(value === undefined) {
   //       this.enabled = true;
   //    }
   //    else {
   //       this.enabled = value;
   //    }
   //
   //    if(this.enabled) {
   //       this.addKeyDownListener();
   //    }
   //    else {
   //       this.removeKeyDownListener();
   //    }
   // }
   //
   // get enterSubmit(): boolean {
   //    return this.enabled;
   // }
   //
   // constructor(private element: ElementRef, private renderer: Renderer2) {
   //    this.addKeyDownListener();
   // }
   //
   // public ngOnDestroy(): void {
   //    this.removeKeyDownListener();
   // }
   //
   // private addKeyDownListener(): void {
   //    if(!this.keyDownListener) {
   //       this.keyDownListener = this.renderer.listen(
   //          this.element.nativeElement, "keydown", (event) => this.onKeyDown(event));
   //    }
   // }
   //
   // private removeKeyDownListener() {
   //    if(this.keyDownListener) {
   //       this.keyDownListener();
   //       this.keyDownListener = null;
   //    }
   // }

   constructor(private element: ElementRef, private renderer: Renderer2, private zone: NgZone) {
   }

   ngAfterContentInit(): void {
      if(!!this.element.nativeElement && !this.element.nativeElement.hasAttribute("tabindex")) {
         this.element.nativeElement.setAttribute("tabindex", -1);
      }

      if(!this.element.nativeElement.querySelector("[autofocus], [defaultFocus]")) {
         this.element.nativeElement.focus();
      }

      if(this.element.nativeElement) {
         this.zone.runOutsideAngular(() => {
            this.keydownListener = this.renderer.listen(
               this.element.nativeElement, "keydown", ($event) => this.onKeyDown($event));
         });
      }
   }

   ngOnDestroy(): void {
      if(this.keydownListener) {
         this.keydownListener();
      }
   }

   public onKeyDown(event: KeyboardEvent): void {
      if(this.onEsc && event.keyCode === 27) {
         event.preventDefault();
         this.onEsc.emit();
         return;
      }

      if((!!this.enterSubmit && this.enterSubmit()) && (event.keyCode === 13 || event.which === 13)) {
         // If the target is a link or button, it is unlikely the user is intending to close the dialog
         if(event.target instanceof HTMLButtonElement ||
            event.target instanceof HTMLAnchorElement ||
            (event.target instanceof HTMLTextAreaElement && !(event.ctrlKey || event.metaKey)))
         {
            return;
         }

         event.preventDefault();
         this.onEnter.emit();
      }
   }
}

