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
import { CustomerUrlConstants } from "../../common/constants/url/customer-url-constants";
import { JfPrincipal } from "../model/jf-principal";
import { ClientModelService } from "./client-model.service";
import { ModelService } from "./model.service";

@Injectable({
   providedIn: "root"
})
export class PrincipalService {
   principal: JfPrincipal;

   constructor(private modelService: ModelService,
               private clientService: ClientModelService)
   {
      this.refresh();
   }

   refresh(): Promise<void> {
      let promise = Promise.resolve(null);

      promise = promise.then(() => this.modelService.getModel<JfPrincipal>(
         CustomerUrlConstants.PRINCIPAL_URI).toPromise());

      promise = promise.then((principal) => {
         this.principal = principal;
         return Promise.resolve();
      });

      return promise;

      // this.modelService.getModel<JfPrincipal>(CustomerUrlConstants.PRINCIPAL_URI
      // ).subscribe((principal) => {
      //    this.principal = principal;
      // });
   }

   logout(): Promise<void> {
      let promise = this.clientService.getModel<void>(CustomerUrlConstants.LOGOUT_URL)
         .toPromise();

      promise = promise.then(() => this.refresh());

      return promise;
   }
}
