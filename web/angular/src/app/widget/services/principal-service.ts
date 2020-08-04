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

import { Injectable, OnInit } from "@angular/core";
import { BaseSubscription } from "../base/BaseSubscription";
import { JfPrincipal } from "../model/jf-principal";
import { ModelService } from "./model.service";

const GET_PRINCIPAL_URI = "/public/principal";

@Injectable()
export class PrincipalService extends BaseSubscription {
   principal: JfPrincipal;

   constructor(private modelService: ModelService) {
      super();

      this.refresh();
   }

   private refresh(): void {
      this.subscriptions.add(this.modelService.getModel<JfPrincipal>(GET_PRINCIPAL_URI).subscribe((principal) => {
         this.principal = principal;
      }));
   }
}
