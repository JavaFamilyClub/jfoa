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
import { TranslateService } from "@ngx-translate/core";
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { ResourceSettingType } from "../../../../common/enum/resource-setting-type";
import { TreeControlService } from "../../../../common/services/tree-control-service";
import { GuiTool } from "../../../../common/util/gui-tool";
import { Tool } from "../../../../common/util/tool";
import { MatColumnIno } from "../../../../widget/mat-table-view/mat-column-ino";
import { ModelService } from "../../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../../widget/tree/model/tree-node-model";
import { BaseTreeSelectDialog } from "../../../dialog/base-tree-select-dialog/base-tree-select-dialog";
import { ResourceItemSettingModel } from "./resource-item-setting-model";
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
   selectedItems: ResourceItemSettingModel[] = [];

   constructor(private dialog: MatDialog,
               private modelService: ModelService,
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

      this.resetSelectedItems();
      this.refreshPermission();
   }

   refreshPermission(): void {
      let nodes = this.selectNodes;

      if(nodes?.length !== 1) {
         return;
      }

      const currentNode = nodes[0];

      this.modelService.getModel<ResourcesManagerPermissionModel>(
         EmUrlConstants.SECURITY_RESOURCES_PERMISSION + currentNode.value)
         .subscribe(permission =>
         {
            this.oldPermission = Tool.clone(permission);
            this.permission = permission;
         });
   }

   get cols(): MatColumnIno[] {
      return [
         {
            headerCheckbox: true,
            cellCheckbox: true,
            name: "headerCheckbox",
            headerCheckboxHandle: (value: boolean) => {
               if(value) {
                  this.selectedItems = this.permission.items;
               }
               else {
                  this.resetSelectedItems();
               }
            },
            headerCheckboxChecked: () => {
               return !Tool.isEmpty(this.selectedItems)
                  && this.selectedItems === this.permission.items;
            }
         },
         {
            label: this.translate.instant("Type"),
            name: "type",
            onlyIcon: true,
            iconFunc: type => {
               return GuiTool.getResourceSettingIcon(type);
            }
         },
         {
            label: this.translate.instant("Name"),
            name: "name"
         },
         {
            cellCheckbox: true,
            label: this.translate.instant("Read"),
            name: "read"
         },
         {
            cellCheckbox: true,
            label: this.translate.instant("Write"),
            name: "write"
         },
         {
            cellCheckbox: true,
            label: this.translate.instant("Delete"),
            name: "delete"
         },
         {
            cellCheckbox: true,
            label: this.translate.instant("Access"),
            name: "access"
         },
         {
            cellCheckbox: true,
            label: this.translate.instant("Admin"),
            name: "admin"
         }
      ];
   }

   get noChanged(): boolean {
      return Tool.isEquals(this.permission, this.oldPermission);
   }

   apply(): void {
      this.modelService.putModel(EmUrlConstants.SECURITY_RESOURCES_PERMISSION, this.permission)
         .subscribe(() =>
      {
         this.refreshPermission();
      });
   }

   reset(): void {
      this.permission = Tool.clone(this.oldPermission);
   }

   add(): void {
      this.dialog.open(BaseTreeSelectDialog, {
         height: "45vh",
         minWidth: "30%",
         data: {
            treeUrl: EmUrlConstants.SECURITY_RESOURCES_PERMISSION_TREE,
            title: this.translate.instant("em.security.dialog.addPermission"),
            isDisabledNode: (node): boolean => {
               return this.permission?.items.some(
                  item => item.roleId == node.data.id);
            }
         }
      }).afterClosed().subscribe((selectNodes: TreeNodeModel[]) => {
         if(Tool.isEmpty(selectNodes)) {
            return;
         }

         const newItems: ResourceItemSettingModel[] = [];

         for(let node of selectNodes) {
            newItems.push({
               roleId: node.data.id,
               type: ResourceSettingType.Role,
               name: node.data.name,
               read: false,
               write: false,
               delete: false,
               access: false,
               admin: false
            });
         }

         this.permission.items = this.permission.items.concat(newItems);
      });
   }

   selectItem(item: ResourceItemSettingModel): void {
      this.selectedItems.push(item);
   }

   onRowUnSelected(item: ResourceItemSettingModel): void {
      this.selectedItems = this.selectedItems.filter(i => i != item);
   }

   deleteSelected(): void {
      let currentItems = Tool.clone(this.permission.items);

      this.selectedItems.forEach(item => {
         currentItems = currentItems.filter(it => !Tool.isEquals(it, item));
      });

      this.permission.items = currentItems;
      this.resetSelectedItems();
   }

   resetSelectedItems(): void {
      this.selectedItems = [];
   }

}
