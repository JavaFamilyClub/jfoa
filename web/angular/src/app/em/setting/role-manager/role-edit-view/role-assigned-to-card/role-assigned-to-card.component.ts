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
import { MatDialog } from "@angular/material/dialog";
import { TranslateService } from "@ngx-translate/core";
import { EmUrlConstants } from "../../../../../common/constants/url/em-url-constants";
import { Tool } from "../../../../../common/util/tool";
import { MatColumnIno } from "../../../../../widget/mat-table-view/mat-column-ino";
import { TreeNodeModel } from "../../../../../widget/tree/model/tree-node-model";
import { BaseTreeSelectDialog } from "../../../../dialog/base-tree-select-dialog/base-tree-select-dialog";
import { AssignedToItem } from "./assigned-to-item";
import { RoleAssignedToModel } from "./role-assigned-to-model";

@Component({
   selector: "role-assigned-to-card",
   templateUrl: "./role-assigned-to-card.component.html",
   styleUrls: ["./role-assigned-to-card.component.scss"]
})
export class RoleAssignedToCardComponent implements OnInit {
   @Input() model: RoleAssignedToModel;
   selectedItems: AssignedToItem[] = [];

   constructor(private dialog: MatDialog,
               private translate: TranslateService)
   {
   }

   ngOnInit(): void {
   }

   get cols(): MatColumnIno[] {
      return [
         {
            headerCheckbox: true,
            cellCheckbox: true,
            name: "headerCheckbox",
            headerCheckboxHandle: (value: boolean) => {
               if(value) {
                  this.selectedItems = this.model.items;
               }
               else {
                  this.resetSelectedItems();
               }
            },
            headerCheckboxChecked: () => {
               return !Tool.isEmpty(this.selectedItems)
                  && this.selectedItems === this.model.items;
            }
         },
         {
            label: this.translate.instant("Type"),
            name: "type"
         },
         {
            label: this.translate.instant("Name"),
            name: "label"
         }
      ];
   }

   private resetSelectedItems(): void {
      this.selectedItems = [];
   }

   openAddUserDialog(): void {
      this.dialog.open(BaseTreeSelectDialog, {
         height: "45vh",
         minWidth: "30%",
         data: {
            treeUrl: EmUrlConstants.USERS_TREE,
            title: this.translate.instant("em.user.dialog.addUser"),
            isDisabledNode: (node): boolean => {
               return this.model?.items.some(
                  item => item.id == node.data.id);
            }
         }
      }).afterClosed().subscribe((selectNodes: TreeNodeModel[]) => {
         if(Tool.isEmpty(selectNodes)) {
            return;
         }

         const newItems: AssignedToItem[] = [];

         for(let node of selectNodes) {
            newItems.push({
               id: node.data.id,
               label: node.label,
               type: node.type
            });
         }

         this.model.items = this.model.items.concat(newItems);
      });
   }

   deleteSelectedUser(): void {
      let currentItems = Tool.clone(this.model.items);

      this.selectedItems.forEach(item => {
         currentItems = currentItems.filter(it => !Tool.isEquals(it, item));
      });

      this.model.items = currentItems;
      this.resetSelectedItems();
   }

   selectItem(item: AssignedToItem): void {
      this.selectedItems.push(item);
   }

   onRowUnSelected(item: AssignedToItem): void {
      this.selectedItems = this.selectedItems.filter(i => i != item);
   }
}
