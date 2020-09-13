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

import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { MatDrawer } from "@angular/material/sidenav";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { map, takeUntil } from "rxjs/operators";
import { Searchable } from "../../common/annotation/searchable";
import { BaseSubscription } from "../../widget/base/BaseSubscription";
import { ModelService } from "../../widget/services/model.service";
import { SideNavService } from "../service/side-nav.service";
import { SearchResult } from "./search-result";

@Component({
   selector: "search-result",
   templateUrl: "search-result.component.html",
   styleUrls: ["search-result.component.scss"]
})
export class SearchResultComponent {
   searchModel: SearchResult;

   constructor(private route: ActivatedRoute) {
      this.route.data.pipe(
         map(data => data.searchResults)
      ).subscribe(
         searchResults => this.searchModel = searchResults
      );
   }
}
