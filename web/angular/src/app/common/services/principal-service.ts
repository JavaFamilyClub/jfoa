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
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";

const GET_PRINCIPAL_URI = "/public/principal";

@Injectable({
   providedIn: "any"
})
export class PrincipalService {
   principal: JfPrincipal;

   constructor(private modelService: ModelService) {
      this.refresh();
   }

   refresh(): void {
      this.modelService.getModel<JfPrincipal>(
         GET_PRINCIPAL_URI).subscribe((principal) =>
      {
         this.principal = principal;
      });
   }
}
