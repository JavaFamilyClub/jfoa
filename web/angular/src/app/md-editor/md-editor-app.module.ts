/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FroalaEditorModule, FroalaViewModule } from "angular-froala-wysiwyg";
import { MODULE_COMPONENTS } from "./index.components";
import { MdEditorComponent } from "./md-editor.component";

@NgModule({
   declarations: [
      MODULE_COMPONENTS
   ],
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      NgbModule,
      MatFormFieldModule,
      MatInputModule,
      MatButtonModule,
      MatIconModule,
      FroalaEditorModule,
      FroalaViewModule
   ],
   bootstrap: [
      MdEditorComponent
   ],
   entryComponents: [
   ],
   exports: [
      MODULE_COMPONENTS
   ],
   providers: [
   ]
})
export class MdEditorAppModule {
}

