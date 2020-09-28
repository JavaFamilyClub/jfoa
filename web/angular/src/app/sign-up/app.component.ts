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
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { InstallerClientUrlConstants } from "../common/constants/url/installer-client-url-constants";
import { ComponentTool } from "../common/util/component-tool";
import { FormValidators } from "../common/util/form-validators";
import { GuiTool } from "../common/util/gui-tool";
import { Tool } from "../common/util/tool";
import { ClientModelService } from "../widget/services/client-model.service";
import { PrincipalService } from "../widget/services/principal-service";
import { CustomerVo } from "./customer-vo";

@Component({
  selector: "app-signup",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class SignupAppComponent implements OnInit {
   model: CustomerVo = {
      account: "",
      email: ""
   };
   form: FormGroup;

   constructor(private clientModelService: ClientModelService,
               private principalService: PrincipalService,
               private translate: TranslateService,
               private modalService: NgbModal,
               private snackBar: MatSnackBar,
               private fb: FormBuilder,
               private router: Router)
   {
   }

   ngOnInit(): void {
      document.body.className += " app-loaded";

      this.form = this.fb.group({
         email: this.fb.control(this.model.email, [
            Validators.required, FormValidators.emailSpecialCharacters
         ])
      });

      this.form.get("email").valueChanges.subscribe((value) => {
         this.model.email = this.model.account = value;
      });
   }

   get isInstaller(): boolean {
      return Tool.isInstaller();
   }

   signup(): void {
      this.clientModelService.sendModel(InstallerClientUrlConstants.SIGN_UP_URI,
         this.model).subscribe((resp: any) =>
      {
         if(!!resp?.body) {
            this.snackBar.open(resp.body.message);
            return;
         }

         ComponentTool.showConfirmDialog(this.modalService,
            this.translate.instant("user.prompt.signUpSuccessTitle"),
            this.translate.instant("user.prompt.signUpSuccessInfo"))
            .then((result) => {
               if(result == "ok") {
                  this.router.navigateByUrl("/login").then();
               }
         });
      });
   }

   isInvalid(control: AbstractControl): boolean {
      return GuiTool.formInvalid(control);
   }
}
