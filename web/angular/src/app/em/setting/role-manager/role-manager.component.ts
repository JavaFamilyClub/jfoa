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

import { NestedTreeControl } from "@angular/cdk/tree";
import { Component, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { ContextHelp } from "../../../common/annotation/context-help";
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { MatColumnIno } from "../../../widget/mat-table-view/mat-column-ino";
import { ModelService } from "../../../widget/services/model.service";
import { PrincipalService } from "../../../widget/services/principal-service";
import { CustomerModel } from "../model/customer-model";
import { RoleManagerModel } from "../model/role-manager-model";
import { UserManagerModel } from "../model/user-manager-model";


@ContextHelp({
  route: "/em/setting/role-manager",
  link: "EmRoleManager"
})
@Searchable({
  title: "Role Manager",
  route: "/em/setting/role-manager",
  keywords: [
    "role", "role manager"
  ]
})
@Component({
  selector: "em-role-manager",
  templateUrl: "./role-manager.component.html",
  styleUrls: ["./role-manager.component.scss"]
})
export class RoleManagerComponent implements OnInit {
  model: RoleManagerModel;

  constructor(private snackBar: MatSnackBar,
              private modelService: ModelService,
              private translate: TranslateService)
  {
  }

  ngOnInit(): void {
    this.refresh();
  }

  private refresh(): void {
    this.modelService.getModel<RoleManagerModel>(EmUrlConstants.ROLES_TREE)
      .subscribe(model =>
    {
      this.model = model;
    });
  }

}
