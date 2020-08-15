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

import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ModelService } from "../../widget/services/model.service";
import { MailAuthorModel } from "./mail-author-model";

const MAIL_AUTHOR_URI = "/mail-author";

@Component({
   selector: "mail-author",
   templateUrl: "mail-author.component.html",
   styleUrls: ["mail-author.component.scss"]
})
export class MailAuthorComponent {
   model: MailAuthorModel = {
      subject: "",
      content: ""
   };
   loading = false;

   constructor(private modelService: ModelService,
               private snackBar: MatSnackBar)
   {
   }

   send(): void {
      if(!!!this.model.content) {
         this.snackBar.open("Email Content must not empty!", "Close");
         return;
      }

      this.loading = true;

      this.modelService.sendModel(MAIL_AUTHOR_URI, this.model).subscribe(() => {
         this.loading = false;
      }, () => { this.loading = false; });
   }
}
