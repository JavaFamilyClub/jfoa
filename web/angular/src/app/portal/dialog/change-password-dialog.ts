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
import { MatSnackBar } from "@angular/material/snack-bar";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { CustomerUrlConstants } from "../../common/constants/url/customer-url-constants";
import { ComponentTool } from "../../common/util/component-tool";
import { FormValidators } from "../../common/util/form-validators";
import { BaseSubscription } from "../../widget/base/BaseSubscription";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { ChangePasswordDialogModel } from "../model/dialog/change-password-dialog-model";

@Component({
   selector: "change-password",
   templateUrl: "change-password-dialog.html",
   styleUrls: ["change-password-dialog.scss"]
})
export class ChangePasswordDialog extends BaseSubscription implements OnInit {
   @Output() onCommit = new EventEmitter<void>();
   @Output() onCancel = new EventEmitter<void>();

   model: ChangePasswordDialogModel;

   form: FormGroup;
   errorStateMatcher: ErrorStateMatcher;

   constructor(private fb: FormBuilder,
               private snackBar: MatSnackBar,
               private modalService: NgbModal,
               private modelService: ModelService,
               private principalService: PrincipalService,
               private defaultErrorMatcher: ErrorStateMatcher)
   {
      super();
      this.model = {
         account: this.principal?.account,
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
      this.modelService.putModel<boolean>(CustomerUrlConstants.PASSWORD_VERIFY,
         this.model).subscribe((response) =>
      {
         if(response.body) {
            this.modelService.putModel(CustomerUrlConstants.PASSWORD_CHANGE,
               this.model).subscribe(() => {
                  this.snackBar.open("Password changed successfully.");
                  this.onCommit.emit();
            });
         }
         else {
            ComponentTool.showMessageDialog(
               this.modalService, "Error", "The old password is error.")
               .then(() => {});
         }
      });
   }
}

