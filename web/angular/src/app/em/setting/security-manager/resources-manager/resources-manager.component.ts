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

import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { TreeControlService } from "../../../../common/services/tree-control-service";
import { Tool } from "../../../../common/util/tool";
import { MatColumnIno } from "../../../../widget/mat-table-view/mat-column-ino";
import { ModelService } from "../../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../../widget/tree/model/tree-node-model";
import { ResourcesManagerModel } from "./resources-manager-model";
import { ResourcesManagerPermissionModel } from "./resources-manager-permission-model";

@Component({
   selector: "resources-manager",
   templateUrl: "./resources-manager.component.html",
   styleUrls: ["./resources-manager.component.scss"],
   providers: [
      TreeControlService
   ]
})
export class ResourcesManagerComponent implements OnInit {
   model: ResourcesManagerModel;
   permission: ResourcesManagerPermissionModel;
   oldPermission: ResourcesManagerPermissionModel;

   constructor(private modelService: ModelService,
               private translate: TranslateService,
               private treeControlService: TreeControlService)
   {
      this.refresh();
   }

   ngOnInit(): void {
   }

   refresh(): void {
      this.modelService.getModel<ResourcesManagerModel>(
         EmUrlConstants.SECURITY_RESOURCES).subscribe(model =>
      {
         this.model = model;
      });
   }

   get selectNodes(): TreeNodeModel[] {
      return this.treeControlService.selectNodes;
   }

   onSelectNodes(info: MatTreeSelectedInfo): void {
      this.treeControlService.onSelectNodes(info);

      let nodes = info.nodes;

      if(nodes?.length !== 1) {
         return;
      }

      const currentNode = nodes[0];

      this.modelService.getModel<ResourcesManagerPermissionModel>(
         EmUrlConstants.SECURITY_RESOURCES_PERMISSION + currentNode.value)
         .subscribe(permission =>
      {
         this.oldPermission = Tool.clone(this.permission);
         this.permission = permission;
      });
   }

   get cols(): MatColumnIno[] {
      return [
         {
            isCheckbox: true,
            checkboxHandle: () => {

            }
         },
         {
            label: this.translate.instant("Type"),
            name: "type"
         },
         {
            label: this.translate.instant("Name"),
            name: "name"
         },
         {
            label: this.translate.instant("Read"),
            name: "read"
         },
         {
            label: this.translate.instant("Write"),
            name: "write"
         },
         {
            label: this.translate.instant("Delete"),
            name: "delete"
         },
         {
            label: this.translate.instant("Access"),
            name: "access"
         }
      ];
   }

   get applyDisabled(): boolean {
      return false;
   }

   apply(): void {

   }

   reset(): void {

   }
}
