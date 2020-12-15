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
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { Tool } from "../../../../common/util/tool";
import { Role } from "../../../../domain/role";
import { ModelService } from "../../../../widget/services/model.service";
import { RoleEditViewModel } from "./role-edit-view-model";

@Component({
   selector: "role-edit-view",
   templateUrl: "./role-edit-view.component.html",
   styleUrls: ["./role-edit-view.component.scss"]
})
export class RoleEditViewComponent implements OnInit {
   _roleId: number;

   @Input() set roleId(roleId: number) {
      this._roleId = roleId;
      this.refresh();
   }

   get roleId(): number {
      return this._roleId;
   }

   model: RoleEditViewModel;
   oldModel: RoleEditViewModel;
   form: FormGroup;

   constructor(private modelService: ModelService,
               private fb: FormBuilder)
   {
   }

   ngOnInit(): void {
   }

   private refresh(): void {
      if(this.roleId === null || this.roleId === undefined) {
         return;
      }

      this.modelService.getModel<RoleEditViewModel>(EmUrlConstants.ROLE + this.roleId)
         .subscribe(model =>
      {
         this.model = model;
         this.oldModel = Tool.clone(model);
         this.initForm();
      });
   }

   private initForm() {
      this.form = this.fb.group({
         "name": this.fb.control(this.role.name, [Validators.required])
      });

      this.form.get("name").valueChanges.subscribe(value => {
         this.role.name = value;
      });
   }

   apply(): void {
      this.modelService.putModel(EmUrlConstants.ROLE + this.roleId,
         this.model).subscribe(() =>
      {
        this.refresh();
      });
   }

   get role(): Role {
      return this.model?.role;
   }

   get applyDisabled(): boolean {
      return Tool.isEquals(this.model, this.oldModel);
   }

   reset(): void {
      this.model = Tool.clone(this.oldModel);
   }
}
