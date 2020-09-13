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

import { HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EmUrlConstants } from "../../common/constants/url/em-url-constants";
import { ModelService } from "../../widget/services/model.service";
import { SearchResult } from "./search-result";

@Injectable({
   providedIn: "root"
})
export class SearchService {
   constructor(private modelService: ModelService) {
   }

   search(query: string): Observable<SearchResult> {
      const params = new HttpParams().set("searchWords", query);
      return this.modelService.getModel<SearchResult>(EmUrlConstants.SEARCH, params);
   }
}
