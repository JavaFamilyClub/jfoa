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

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule } from "@ngx-translate/core";
import { FroalaEditorModule, FroalaViewModule } from "angular-froala-wysiwyg";
import { WidgetModule } from "../../widget/widget.module";
import { AppRoutingModule } from "./app-routing.module";
import { MailAuthorComponent } from "./mail-author.component";

@NgModule({
   declarations: [
      MailAuthorComponent
   ],
   imports: [
      CommonModule,
      FormsModule,
      AppRoutingModule,
      WidgetModule,
      ReactiveFormsModule,
      NgbModule,
      MatSnackBarModule,
      MatFormFieldModule,
      MatInputModule,
      MatButtonModule,
      MatIconModule,
      MatProgressSpinnerModule,
      FroalaEditorModule,
      FroalaViewModule,
      TranslateModule
   ],
   bootstrap: [MailAuthorComponent],
   entryComponents: [
   ],
   providers: [
   ]
})
export class MailAuthorAppModule {
}

