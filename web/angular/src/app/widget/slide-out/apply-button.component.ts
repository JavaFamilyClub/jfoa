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

import { Component, Input, Output, EventEmitter } from "@angular/core";

@Component({
   selector: "apply-button",
   templateUrl: "apply-button.component.html",
   styleUrls: ["apply-button.component.scss"]
})
export class ApplyButtonComponent {
   @Input() disabled: boolean = false;
   // true if collapse slide out pane
   @Output() onApply = new EventEmitter<boolean>();
}
