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

import { Component, OnInit } from "@angular/core";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { PrincipalService } from "../../widget/services/principal-service";

@Component({
   selector: "subject-vote",
   templateUrl: "subject-vote.component.html",
   styleUrls: ["subject-vote.component.scss"]
})
export class SubjectVoteComponent implements OnInit {

   constructor(private principalService: PrincipalService) {
   }

   ngOnInit(): void {
   }

   get principal(): JfPrincipal {
      return this.principalService.principal;
   }

}
