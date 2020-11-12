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
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { PortalUrlConstants } from "../../../common/constants/url/portal-url-constants";
import { CreateSubjectModel } from "../model/create-subject-model";
import { ModelService } from "../../../widget/services/model.service";

@Component({
   selector: "subject-create",
   templateUrl: "subject-create.component.html",
   styleUrls: ["subject-create.component.scss"]
})
export class SubjectCreateComponent implements OnInit {
   model: CreateSubjectModel;
   form: FormGroup;

   constructor(private fb: FormBuilder,
               private router: Router,
               private route: ActivatedRoute,
               private modelService: ModelService)
   {
   }

   ngOnInit(): void {
      this.reset();
      this.initForm();
   }

   reset(): void {
      this.model = {
         subject: "",
         description: ""
      };
   }

   create(): void {
      this.modelService.sendModel(PortalUrlConstants.SUBJECT_REQUEST,
         this.model).subscribe(() =>
      {
         this.router.navigate(["../subject-list"], {
            relativeTo: this.route
         }).then(() => {
            this.reset();
         });
      });
   }

   private initForm(): void {
      this.form = this.fb.group({
         subject: this.fb.control(this.model.subject, [Validators.required]),
         description: this.fb.control(this.model.description, [Validators.required])
      });

      this.form.get("subject").valueChanges.subscribe(value => {
         this.model.subject = value;
      });

      this.form.get("description").valueChanges.subscribe(value => {
         this.model.description = value;
      });
   }
}
