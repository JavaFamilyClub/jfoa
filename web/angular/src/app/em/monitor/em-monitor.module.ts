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
import { MatMomentDateModule } from "@angular/material-moment-adapter";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { AuditViewComponent } from "./audit/audit-view.component";
import { EmMonitorRoutingModule } from "./em-monitor-routing.module";
import { EmMonitorComponent } from "./em-monitor.component";

@NgModule({
   declarations: [
      EmMonitorComponent,
      AuditViewComponent,
   ],
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      EmMonitorRoutingModule,
      MatButtonModule,
      MatSnackBarModule,
      MatSidenavModule,
      MatInputModule,
      MatListModule,
      MatIconModule,
      MatCardModule,
      MatTableModule,
      MatSortModule,
      MatPaginatorModule,
      MatDatepickerModule,
      MatMomentDateModule
   ],
   entryComponents: [
   ],
   providers: [
   ]
})
export class EmMonitorModule {
}
