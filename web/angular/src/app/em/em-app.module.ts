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
import { MatCardModule } from "@angular/material/card";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatMenuModule } from "@angular/material/menu";
import { MatToolbarModule } from "@angular/material/toolbar";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule } from "@ngx-translate/core";
import { DownloadModule } from "../download/download.module";
import { ModelService } from "../widget/services/model.service";
import { WidgetModule } from "../widget/widget.module";
import { EMAppComponent } from "./app.component";
import { MODULE_COMPONENTS } from "./index.components";
import { EMAppRoutingModule } from "./app-routing.module";
import { EmMonitorModule } from "./monitor/em-monitor.module";
import { EmTitleBarService } from "./service/em-title-bar.service";
import { SideNavService } from "./service/side-nav.service";
import { EmSettingModule } from "./setting/em-setting.module";

@NgModule({
   declarations: [
      EMAppComponent,
      ...MODULE_COMPONENTS
   ],
   imports: [
      CommonModule,
      WidgetModule,
      FormsModule,
      NgbModule,
      ReactiveFormsModule,
      EMAppRoutingModule,
      EmMonitorModule,
      EmSettingModule,
      MatToolbarModule,
      MatButtonModule,
      MatMenuModule,
      MatDividerModule,
      MatIconModule,
      DownloadModule,
      MatDialogModule,
      MatFormFieldModule,
      MatInputModule,
      TranslateModule,
      MatCardModule
   ],
   bootstrap: [
      EMAppComponent
   ],
   entryComponents: [
   ],
   providers: [
      SideNavService,
      EmTitleBarService,
      ModelService
   ]
})
export class EmAppModule {
}
