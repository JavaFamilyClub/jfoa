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
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from "@angular/forms";
import { ErrorStateMatcher } from "@angular/material/core";
import { FormValidators } from "../../common/util/form-validators";
import { BaseSubscription } from "../../widget/base/BaseSubscription";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { ChangePasswordModel } from "../model/dialog/change-password-model";

@Component({
   selector: "change-password",
   templateUrl: "change-password-dialog.html",
   styleUrls: ["change-password-dialog.scss"]
})
export class ChangePasswordDialog extends BaseSubscription implements OnInit {
   @Output() onCommit = new EventEmitter<void>();
   @Output() onCancel = new EventEmitter<void>();

   model: ChangePasswordModel;

   form: FormGroup;
   errorStateMatcher: ErrorStateMatcher;

   constructor(private fb: FormBuilder,
               private modelService: ModelService,
               private principalService: PrincipalService,
               private defaultErrorMatcher: ErrorStateMatcher)
   {
      super();
      this.model = {
         id: this.principal?.id,
         oldPwd: "",
         newPwd: "",
         confirmPwd: ""
      };

      this.form = this.fb.group({
         oldPwd: this.fb.control(this.model.oldPwd, [Validators.required]),
         pwds: this.fb.group({
            password: this.fb.control(this.model.newPwd, [Validators.required, FormValidators.passwordComplexity]),
            confirmPassword: this.fb.control(this.model.confirmPwd, [Validators.required])
         }, { validator: FormValidators.matchPwdValidator })
      });

      this.errorStateMatcher = {
         isErrorState: (control: FormControl | null, form: FormGroupDirective | NgForm | null) =>
            !!this.pwdFormGroup.errors && !!this.pwdFormGroup.errors.passwordMismatch ||
            defaultErrorMatcher.isErrorState(control, form)
      };

      this.subscriptions.add(this.form.get("oldPwd").valueChanges.subscribe(
         (value) => {
            this.model.oldPwd = value;
         })
      );

      this.subscriptions.add(this.form.get("pwds.password").valueChanges.subscribe(
         (value) => {
            this.model.newPwd = value;
         })
      );

      this.subscriptions.add(this.form.get("pwds.confirmPassword").valueChanges.subscribe(
         (value) => {
            this.model.confirmPwd = value;
         })
      );
   }

   ngOnInit(): void {
   }

   get pwdFormGroup(): FormGroup {
      return this.form.controls.pwds as FormGroup;
   }

   get principal(): JfPrincipal {
      return this.principalService?.principal;
   }

   ok(): void {
      console.log("======model======", this.model);
   }
}

