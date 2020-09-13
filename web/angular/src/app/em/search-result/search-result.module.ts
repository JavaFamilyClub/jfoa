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
import { SearchResultResolver } from "./search-result-resolver.service";
import { SearchResultRoutingModule } from "./search-result-routing.module";
import { SearchResultComponent } from "./search-result.component";
import { SearchService } from "./search.service";

@NgModule({
   declarations: [
      SearchResultComponent,
   ],
   imports: [
      CommonModule,
      FormsModule,
      ReactiveFormsModule,
      SearchResultRoutingModule,
      MatCardModule
   ],
   entryComponents: [
   ],
   providers: [
      SearchService,
      SearchResultResolver
   ]
})
export class SearchResultModule {
}
