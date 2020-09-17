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

import { Component, ElementRef, ViewChild } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Tool } from "../../common/util/tool";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { MailAuthorModel } from "./mail-author-model";

const MAIL_AUTHOR_URI = "/mail-author";

@Component({
   selector: "mail-author",
   templateUrl: "mail-author.component.html",
   styleUrls: ["mail-author.component.scss"]
})
export class MailAuthorComponent {
   model: MailAuthorModel;
   @ViewChild("froalaContainer") froalaContainer: ElementRef;

   options = {
      language: "zh_cn",
      placeholderText: "Write your email content.",
      height: "280",
      toolbarButtonsXS: ["undo", "redo", "|", "bold", "italic", "underline", "|", "fontSize", "align", "color"],
      pasteAllowedStyleProps: ["font-size", "color"],
      htmlAllowComments: false,
      fontSizeDefaultSelection: "16",
      colorsHEXInput: false,
      toolbarSticky: false,
      colorsBackground: ["#2E2E2E", "#767676", "#DF281B", "#F4821C", "#46AC43", "#2E5BF7", "#A249B3", "REMOVE"],
      colorsText: ["#2E2E2E", "#767676", "#DF281B", "#F4821C", "#46AC43", "#2E5BF7", "#A249B3", "REMOVE"],
      fontSize: ["14", "16", "18", "20"],
      charCounterCount: true,
      quickInsertButtons: ["image", "table", "ol", "ul"],
      quickInsertEnabled: true,
      embedlyKey: "JavaFamily",
      embedlyEditButtons: []
   };

   loading = false;

   constructor(private snackBar: MatSnackBar,
               private modelService: ModelService,
               private principalService: PrincipalService)
   {
      this.reset();
   }

   private reset(): void {
      this.model = {
         subject: "",
         content: ""
      };
   }

   get principal(): JfPrincipal {
      return this.principalService.principal;
   }

   send(): void {
      if(!!!this.model.subject) {
         this.snackBar.open("Email Subject must not empty!", "Close");
         return;
      }

      if(!!!this.model.content) {
         this.snackBar.open("Email Content must not empty!", "Close");
         return;
      }

      const model = Tool.clone(this.model);

      model.content = model.content.replace(
         /<p .*>Powered by <a .*>Froala Editor<\/a><\/p>/, "");

      model.content += this.sign;

      this.loading = true;

      this.modelService.sendModel(MAIL_AUTHOR_URI, model).subscribe(() => {
         this.loading = false;
         this.snackBar.open("Email Send success!", "Close");
         this.reset();
      }, () => {
         this.loading = false;
         this.snackBar.open("Email Send failed, Please try again later!", "Close");
      });
   }

   private get sign(): string {
      return "<br><hr><p>---- "
         + this.principalService.principal?.userName
         + "(" + this.principalService.principal?.account + ")</p>";
   }
}
