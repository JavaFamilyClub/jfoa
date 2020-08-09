/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Gender } from "../../common/enum/gender";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { UserProfileDialogModel } from "../model/dialog/user-profile-dialog-model";

const USER_PROFILE_UTI = "/user/profile";

@Component({
   selector: "user-profile-dialog",
   templateUrl: "user-profile-dialog.html",
   styleUrls: ["user-profile-dialog.scss"]
})
export class UserProfileDialog implements OnInit {
   @Input() model: UserProfileDialogModel;

   @Output() onCommit = new EventEmitter<void>();
   @Output() onCancel = new EventEmitter<void>();

   form: FormGroup;

   readonly Gender = Gender;

   constructor(private fb: FormBuilder,
               private modelService: ModelService,
               private principalService: PrincipalService)
   {
   }

   ngOnInit(): void {
      this.form = this.fb.group({
         name: this.fb.control(this.model.name, [Validators.required]),
         email: this.fb.control(this.model.email, [Validators.email]),
         gender: this.fb.control(this.model.gender, [])
      });

      this.form.get("name").valueChanges.subscribe(val => {
         this.model.name = val;
      });

      this.form.get("email").valueChanges.subscribe(val => {
         this.model.email = val;
      });

      this.form.get("gender").valueChanges.subscribe(val => {
         this.model.gender = val;
      });
   }

   ok(): void {
      this.modelService.putModel(USER_PROFILE_UTI, this.model).subscribe(() => {
         this.principalService.refresh();
         this.onCommit.emit();
      });
   }
}

