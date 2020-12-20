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
import { MatTabsModule } from "@angular/material/tabs";
import { TranslateModule } from "@ngx-translate/core";
import { WidgetModule } from "../../../widget/widget.module";
import { EmSystemMonitorRoutingModule } from "./em-system-monitor-routing.module";
import { SystemMonitorComponent } from "./system-monitor.component";
import { SystemSummaryViewComponent } from "./system-summary-view/system-summary-view.component";

@NgModule({
   declarations: [
      SystemMonitorComponent,
      SystemSummaryViewComponent,
   ],
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      EmSystemMonitorRoutingModule,
      TranslateModule,
      WidgetModule,
      MatTabsModule,
      MatCardModule,
      MatButtonModule
   ],
   entryComponents: [
   ],
   providers: [
   ]
})
export class EmSystemMonitorModule {
}
