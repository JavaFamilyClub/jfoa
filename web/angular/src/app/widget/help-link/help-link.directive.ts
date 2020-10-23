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

import { AfterViewInit, Directive, ElementRef, HostListener, Input } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { HelpUrlService } from "./help-url.service";

@Directive({
   selector: "[helpLink]"
})
export class HelpLinkDirective implements AfterViewInit {
   @Input() helpLink: string;
   private _helpUrl: string;

   constructor(private element: ElementRef,
               private helpService: HelpUrlService,
               private translate: TranslateService)
   {
      this.helpService.getHelpUrl()
         .subscribe((url) => this._helpUrl = url);
   }

   ngAfterViewInit() {
      let elem = this.element.nativeElement;
      elem.setAttribute("title", this.translate.instant("Help"));
      let classAttr = elem.getAttribute("class");
      elem.setAttribute("class", "help-question-mark-icon cursor-pointer " + classAttr);
   }

   @HostListener("click", [])
   showSubDocument() {
      if(!!this._helpUrl) {
         window.open(this._helpUrl + this.helpLink);
      }
   }
}
