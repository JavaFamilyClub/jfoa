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
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { ContextHelp } from "../../../common/annotation/context-help";
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { MatColumnIno } from "../../../widget/mat-table-view/mat-column-ino";
import { ModelService } from "../../../widget/services/model.service";
import { PrincipalService } from "../../../widget/services/principal-service";
import { CustomerModel } from "../model/customer-model";
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
  model: UserManagerModel;

  displayCols: MatColumnIno[] = [
    {
      label: this.translate.instant("ID"),
      name: "id"
    },
    {
      label: this.translate.instant("Account"),
      name: "account"
    },
    {
      label: this.translate.instant("user.profile.name"),
      name: "name"
    },
    {
      label: this.translate.instant("portal.email.Email"),
      name: "email"
    },
    {
      label: this.translate.instant("user.profile.Gender"),
      name: "gender"
    },
    {
      label: this.translate.instant("user.status.active"),
      name: "active"
    },
    {
      label: this.translate.instant("registeredDate"),
      name: "registerDate"
    }
  ];

  constructor(private snackBar: MatSnackBar,
              private translate: TranslateService)
  {
  }

  ngOnInit(): void {
    this.refresh();
  }

  private refresh(): void {
    // this.modelService.getModel<UserManagerModel>(EmUrlConstants.USERS)
    //   .subscribe(model =>
    // {
    //   this.model = model;
    // });
  }

}
