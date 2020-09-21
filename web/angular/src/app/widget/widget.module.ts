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
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSelectModule } from "@angular/material/select";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { MatMessageDialog } from "./dialog/mat-message-dialog";
import { ExportDialog } from "./export-dialog/export-dialog";
import { HelpUrlService } from "./help-link/help-url.service";
import { MODULE_COMPONENTS } from "./index.components";
import { MODULE_DIRECTIVES } from "./index.directives";
import { MODULE_PIPES } from "./index.pipes";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { DomService } from "./dom-service/dom.service";
import { DropdownStackService } from "./fixed-dropdown/dropdown-stack.service";
import { SlideOutService } from "./slide-out/slide-out.service";
import { FixedDropdownService } from "./fixed-dropdown/fixed-dropdown.service";
import { TooltipService } from "./tooltip/tooltip.service";
import { DebounceService } from "./services/debounce.service";
import { SlideOutComponent } from "./slide-out/slide-out.component";
import { TooltipComponent } from "./tooltip/tooltip.component";
import { MessageDialog } from "./dialog/message-dialog.component";
import { FixedDropdownContextmenuComponent } from "./fixed-dropdown/fixed-dropdown-contextmenu.component";
import { FixedDropdownComponent } from "./fixed-dropdown/fixed-dropdown.component";

@NgModule({
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      NgbModule,
      MatSelectModule,
      MatDialogModule,
      MatFormFieldModule,
      MatInputModule,
      MatButtonModule,
      MatTableModule,
      MatPaginatorModule,
      MatSortModule
   ],
   declarations: [
      ...MODULE_COMPONENTS,
      ...MODULE_PIPES,
      ...MODULE_DIRECTIVES
   ],
   entryComponents: [
      FixedDropdownComponent,
      FixedDropdownContextmenuComponent,
      MessageDialog,
      MatMessageDialog,
      ExportDialog,
      TooltipComponent,
      SlideOutComponent
   ],
   exports: [
      ...MODULE_COMPONENTS,
      ...MODULE_PIPES,
      ...MODULE_DIRECTIVES
   ],
   providers: [
      FixedDropdownService,
      DropdownStackService,
      SlideOutService,
      TooltipService,
      DebounceService,
      DomService,
      HelpUrlService
   ]
})
export class WidgetModule {
}
