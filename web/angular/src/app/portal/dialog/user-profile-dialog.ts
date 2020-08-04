/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

import { Component, EventEmitter, Output } from "@angular/core";

@Component({
   selector: "user-profile-dialog",
   templateUrl: "user-profile-dialog.html",
   styleUrls: ["user-profile-dialog.scss"]
})
export class UserProfileDialog {
   @Output() onCommit = new EventEmitter<any>();
   @Output() onCancel = new EventEmitter<void>();
}

