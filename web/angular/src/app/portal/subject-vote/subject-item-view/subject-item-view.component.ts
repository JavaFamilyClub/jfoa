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

import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { TranslateService } from "@ngx-translate/core";
import { PortalUrlConstants } from "../../../common/constants/url/portal-url-constants";
import { InputNameDialogComponent } from "../../../widget/dialog/input-name-dialog/input-name-dialog.component";
import { ModelService } from "../../../widget/services/model.service";
import { SubjectRequestVo } from "../model/subject-request-vo";

@Component({
   selector: "subject-item-view",
   templateUrl: "subject-item-view.component.html",
   styleUrls: ["subject-item-view.component.scss"]
})
export class SubjectItemViewComponent implements OnInit {
   @Input() model: SubjectRequestVo;
   @Output() onRefresh = new EventEmitter<void>();

   constructor(private translate: TranslateService,
               private modelService: ModelService,
               private dialog: MatDialog)
   {
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
               if(this.model.vote.supportProcessed) {
                  this.model.vote.support--;
               }
               else {
                  this.model.vote.support++;
               }

               this.model.vote.supportProcessed
                  = !this.model.vote.supportProcessed;
            }
            else {
               if(this.model.vote.opposeProcessed) {
                  this.model.vote.oppose--;
               }
               else {
                  this.model.vote.oppose++;
               }

               this.model.vote.opposeProcessed
                  = !this.model.vote.opposeProcessed;
            }
         });
   }

   get editable(): boolean {
      return this.model.deletable || this.model.canArchive;
   }

   delete(): void {
      this.modelService.deleteModel(
         PortalUrlConstants.SUBJECT_REQUEST + "/" + this.model.id)
         .subscribe(() => {
            this.onRefresh.emit();
         });
   }

   achieve(): void {
      this.dialog.open(InputNameDialogComponent, {
         minWidth: "30%",
         data: {
            title: this.translate.instant("portal.sr.achievedAddr"),
            placeholder: this.translate.instant("portal.sr.achievedAddrPlaceHolder"),
            validatorErrors: {
               required: this.translate.instant("portal.sr.achievedAddrRequired")
            }
         }
      }).afterClosed().subscribe(result => {
         this.modelService.putModel(PortalUrlConstants.SUBJECT_REQUEST
            + "/" + this.model.id + "/" + result).subscribe(() =>
         {
            this.onRefresh.emit();
         });
      });
   }
}
