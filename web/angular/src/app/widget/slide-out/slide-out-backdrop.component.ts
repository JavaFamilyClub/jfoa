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

import { Component, Input } from "@angular/core";

@Component({
   selector: "w-slide-out-backdrop",
   template: "",
   host: { // tslint:disable-line:no-host-metadata-property
      "[class]": '"modal-backdrop fade show" + (backdropClass ? " " + backdropClass : "")',
      "style": "z-index: 1050"
   }
})
export class SlideOutBackdropComponent {
   @Input() backdropClass: string;
}
