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

import { Component, Input, OnInit } from "@angular/core";
import { PortalUrlConstants } from "../../../common/constants/url/portal-url-constants";
import { ModelService } from "../../../widget/services/model.service";
import { SubjectRequestVo } from "../model/subject-request-vo";

@Component({
   selector: "subject-item-view",
   templateUrl: "subject-item-view.component.html",
   styleUrls: ["subject-item-view.component.scss"]
})
export class SubjectItemViewComponent implements OnInit {
   @Input() model: SubjectRequestVo;

   constructor(private modelService: ModelService) {
   }

   ngOnInit(): void {
   }

   changeVote(event: MouseEvent, like: boolean): void {
      event.preventDefault();
      event.stopPropagation();

      this.modelService.putModel(PortalUrlConstants.SUBJECT_REQUEST_VOTE +
         this.model.id + "/" + like)
         .subscribe(() => {
            if(like) {
               this.model.vote.support++;
            }
            else {
               this.model.vote.oppose++;
            }
         });
   }
}
