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

import { Injectable } from "@angular/core";
import { Observable, of as observableOf } from "rxjs";
import { tap } from "rxjs/operators";
import { CommonUrlConstants } from "../../common/constants/url/common-url-constants";
import { ModelService } from "../services/model.service";

@Injectable()
export class HelpUrlService {
   private _helpUrl: string;

   constructor(private modelService: ModelService) {
   }

   getHelpUrl(): Observable<string> {
      if(!!this._helpUrl) {
         return observableOf(this._helpUrl);
      }

      return this.modelService.getModel<string>(CommonUrlConstants.HELP_URL).pipe(
         tap((data) => this._helpUrl = data)
      );
   }
}
