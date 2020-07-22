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

import { AfterViewInit, Directive, ElementRef, Input } from "@angular/core";
import { concat } from "rxjs/operators";

@Directive({
   selector: "[wExpandString]"
})
export class ExpandStringDirective implements AfterViewInit {
   @Input()
   set wExpandString(values: string[]) {
      this.values = values;
      this.expandString0();
   }

   @Input()
   set wExpandStringAttr(attribute: string) {
      this.attribute = attribute;
      this.expandString0();
   }

   private values: string[] = [];
   private attribute: string = null;
   private template: string = null;

   constructor(private host: ElementRef) {
   }

   ngAfterViewInit(): void {
      if(this.host.nativeElement) {
         const element = this.host.nativeElement;

         if(this.attribute) {
            this.template = element.getAttribute(this.attribute);
         }
         else {
            this.template = "";

            while(element.firstChild) {
               if(element.firstChild.nodeType === Node.TEXT_NODE) {
                  this.template += element.firstChild.nodeValue;
               }

               element.removeChild(element.firstChild);
            }
         }
      }

      this.expandString0();
   }

   private expandString0() {
      const content = ExpandStringDirective.expandString(this.template, this.values);

      if(content && this.host.nativeElement) {
         const element = this.host.nativeElement;

         if(this.attribute) {
            element.setAttribute(this.attribute, content);
         }
         else {
            while(element.firstChild) {
               element.removeChild(element.firstChild);
            }

            element.appendChild(element.ownerDocument.createTextNode(content));
         }
      }
   }

   public static expandString(template: string, values: string[]): string {
      let content = "";

      if(template) {
         let re = /[^%]?%s(\$\d+)?/g;
         let ch = 0;
         let index = 0;
         let match;

         while((match = re.exec(template)) != null) {
            if(match[0][0] != "%") {
               content += template.substring(ch, re.lastIndex - match[0].length + 1);
            }
            else {
               content += template.substring(ch, re.lastIndex - match[0].length);
            }

            let value;

            if(match[1]) {
               value = values[parseInt(match[1].substring(1), 10)];
            }
            else {
               value = values[index++];
            }

            content += value;
            ch = re.lastIndex;
         }

         if(ch < template.length) {
            content += template.substring(ch);
         }

      }

      return content;
   }
}
