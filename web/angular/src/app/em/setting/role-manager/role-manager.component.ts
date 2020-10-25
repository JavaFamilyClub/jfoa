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
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { ContextHelp } from "../../../common/annotation/context-help";
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { Tool } from "../../../common/util/tool";
import { InputNameDialogComponent } from "../../../widget/dialog/input-name-dialog/input-name-dialog.component";
import { ModelService } from "../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../widget/tree/model/tree-node-model";
import { RoleManagerModel } from "../model/role-manager-model";


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
  selectedNodes: TreeNodeModel[];

  constructor(private snackBar: MatSnackBar,
              private modelService: ModelService,
              private dialog: MatDialog,
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

  selectNode(info: MatTreeSelectedInfo): void {
    info.event.stopPropagation();
    info.event.preventDefault();

    this.selectedNodes = info.nodes;
  }

  get selectEmpty(): boolean {
    return Tool.isEmpty(this.selectedNodes);
  }

  addRole(): void {
    this.dialog.open(InputNameDialogComponent).afterClosed().subscribe(name => {
      if(name) {
        this.modelService.sendModel(EmUrlConstants.ROLE + "/" + name, null).subscribe(result => {
          this.refresh();
        });
      }
    });
  }

}
