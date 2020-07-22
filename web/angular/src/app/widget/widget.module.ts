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
import { MODULE_COMPONENTS } from "./index.components";
import { MODULE_DIRECTIVES } from "./index.directives";
import { MODULE_PIPES } from "./index.pipes";
import { ModelService } from "./services/model.service";
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
      NgbModule
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
       ModelService,
       SlideOutService,
       TooltipService,
       DebounceService,
       DomService
   ]
})
export class WidgetModule {
}
