/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { EmSettingRoutingModule } from "./em-setting-routing.module";
import { EmSettingComponent } from "./em-setting.component";

@NgModule({
   declarations: [
      EmSettingComponent,
   ],
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      EmSettingRoutingModule,
      MatButtonModule,
      MatSnackBarModule,
      MatSidenavModule,
      MatInputModule
   ],
   entryComponents: [
   ],
   providers: [
   ]
})
export class EmSettingModule {
}
