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
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatTabsModule } from "@angular/material/tabs";
import { MatTreeModule } from "@angular/material/tree";
import { TranslateModule } from "@ngx-translate/core";
import { WidgetModule } from "../../../widget/widget.module";
import { EmSecurityManagerRoutingModule } from "./em-security-manager-routing.module";
import { ResourcesManagerComponent } from "./resources-manager/resources-manager.component";
import { SecurityManagerComponent } from "./security-manager.component";

@NgModule({
   declarations: [
      SecurityManagerComponent,
      ResourcesManagerComponent
   ],
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      EmSecurityManagerRoutingModule,
      MatSnackBarModule,
      MatSidenavModule,
      MatIconModule,
      MatTabsModule,
      MatCardModule,
      MatProgressSpinnerModule,
      WidgetModule,
      TranslateModule,
      MatTreeModule
   ],
   entryComponents: [
   ],
   providers: [
   ]
})
export class EmSecurityManagerModule {
}
