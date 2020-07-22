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

import { Directive, HostListener, Input, Renderer2, ElementRef } from "@angular/core";
import { DefaultValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";

@Directive({
   selector : "input[trim]",
})
export class InputTrimDirective extends DefaultValueAccessor {
   constructor(private renderer: Renderer2, private elementRef: ElementRef) {
      super(renderer, elementRef, false);
   }

   // set a new value to the field and model.
   private set value(val: any) {
      // update element
      this.writeValue(val);
      // update model
      this.onChange(val);
   }

   /**
    * Updates the value on the blur event.
    */
   @HostListener("blur", ["$event.type", "$event.target.value"])
   onBlur(event: string, value: string): void {
      this.updateValue(event, value);
   }

   /**
    * Trims an input value, and sets it to the model and element.
    *
    * @param {string} event - input event
    * @param {string} value - input value
    */
   private updateValue(event: string, value: string): void {
      this.value = value.trim();

      // this is necessary for reactive forms
      let evt: Event = document.createEvent("Event");
      evt.initEvent("input", true, true);
      Object.defineProperty(evt, "target", {
         value: this.elementRef.nativeElement,
         enumerable: true
      });
      this.elementRef.nativeElement.dispatchEvent(evt);
   }
}
