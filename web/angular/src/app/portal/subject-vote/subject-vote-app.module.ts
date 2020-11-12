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
import { MatExpansionModule } from "@angular/material/expansion";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatTabsModule } from "@angular/material/tabs";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule } from "@ngx-translate/core";
import { WidgetModule } from "../../widget/widget.module";
import { AppRoutingModule } from "./app-routing.module";
import { SubjectCreateComponent } from "./subject-create/subject-create.component";
import { SubjectItemViewComponent } from "./subject-item-view/subject-item-view.component";
import { SubjectListComponent } from "./subject-list/subject-list.component";
import { SubjectVoteComponent } from "./subject-vote.component";

@NgModule({
   declarations: [
      SubjectVoteComponent,
      SubjectListComponent,
      SubjectCreateComponent,
      SubjectItemViewComponent
   ],
   imports: [
      CommonModule,
      FormsModule,
      AppRoutingModule,
      WidgetModule,
      ReactiveFormsModule,
      NgbModule,
      TranslateModule,
      MatTabsModule,
      MatCardModule,
      MatButtonModule,
      MatFormFieldModule,
      MatInputModule,
      MatExpansionModule,
      MatIconModule
   ],
   bootstrap: [SubjectVoteComponent],
   entryComponents: [
   ],
   providers: [
   ]
})
export class SubjectVoteAppModule {
}

