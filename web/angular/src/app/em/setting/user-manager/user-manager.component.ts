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
import { GuiTool } from "../../../common/util/gui-tool";
import { MatColumnIno } from "../../../widget/mat-table-view/mat-column-ino";
import { ModelService } from "../../../widget/services/model.service";
import { PrincipalService } from "../../../widget/services/principal-service";
import { CustomerModel } from "../model/customer-model";
import { UserManagerModel } from "../model/user-manager-model";


@ContextHelp({
  route: "/em/setting/user-manager",
  link: "EmUserManager"
})
@Searchable({
  title: "User Manager",
  route: "/em/setting/user-manager",
  keywords: [
    "user", "user manager", "customer"
  ]
})
@Component({
  selector: "em-user-manager",
  templateUrl: "./user-manager.component.html",
  styleUrls: ["./user-manager.component.scss"]
})
export class UserManagerComponent implements OnInit {
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
      label: this.translate.instant("user.profile.type"),
      name: "type",
      valueFunc: (elem, prop) => GuiTool.parseUserType(elem[prop])
    },
    {
      label: this.translate.instant("user.profile.Gender"),
      name: "gender",
      valueFunc: (elem, prop) => GuiTool.parseGender(this.translate, elem[prop])
    },
    {
      label: this.translate.instant("user.status.active"),
      name: "active"
    },
    {
      label: this.translate.instant("registeredDate"),
      name: "registerDate"
    },
    {
      label: this.translate.instant("Delete"),
      name: "delete",
      btnElement: {
        label: this.translate.instant("Delete"),
        action: (user) => this.deleteUser(user)
      }
    }
  ];

  constructor(private snackBar: MatSnackBar,
              private modelService: ModelService,
              private translate: TranslateService,
              private principalService: PrincipalService)
  {
  }

  ngOnInit(): void {
    this.refresh();
  }

  private deleteUser(user: CustomerModel): void {
    if(!this.principalService.isAdmin()
       || this.principalService.isAdminUser(user.account))
    {
      this.snackBar.open(this.translate.instant("common.noPermission"));
      return;
    }

    this.modelService.deleteModel(EmUrlConstants.USER + "/" + user.id)
       .subscribe(() => {
         this.refresh();
       });
  }

  private refresh(): void {
    this.modelService.getModel<UserManagerModel>(EmUrlConstants.USERS)
      .subscribe(model =>
    {
      this.model = model;
    });
  }

}
