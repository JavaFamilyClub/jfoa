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
import { MatChipsModule } from "@angular/material/chips";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatMenuModule } from "@angular/material/menu";
import { MatRadioModule } from "@angular/material/radio";
import { MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBarModule } from "@angular/material/snack-bar";
import { MatTabsModule } from "@angular/material/tabs";
import { MatToolbarModule } from "@angular/material/toolbar";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule } from "@ngx-translate/core";
import { MdEditorAppModule } from "../md-editor/md-editor-app.module";
import { PortalAppComponent } from "./app.component";
import { WidgetModule } from "../widget/widget.module";
import { ChangePasswordDialog } from "./dialog/change-password-dialog";
import { UserProfileDialog } from "./dialog/user-profile-dialog";
import { MODULE_COMPONENTS } from "./index.components";
import { PortalAppRoutingModule } from "./app-routing.module";

@NgModule({
   declarations: [
      PortalAppComponent,
      ...MODULE_COMPONENTS
   ],
   imports: [
      CommonModule,
      FormsModule,
      WidgetModule,
      ReactiveFormsModule,
      NgbModule,
      PortalAppRoutingModule,
      MatToolbarModule,
      MatButtonModule,
      MatIconModule,
      MatMenuModule,
      MatDividerModule,
      MatRadioModule,
      MatFormFieldModule,
      MatInputModule,
      MatChipsModule,
      MatSnackBarModule,
      TranslateModule,
      MatTabsModule,
      MdEditorAppModule
   ],
   bootstrap: [
      PortalAppComponent
   ],
   entryComponents: [
      UserProfileDialog,
      ChangePasswordDialog
   ],
   providers: [
      {
         provide: MAT_SNACK_BAR_DEFAULT_OPTIONS,
         useValue: {
            duration: 2500
         }
      }
   ]
})
export class PortalAppModule {
}
