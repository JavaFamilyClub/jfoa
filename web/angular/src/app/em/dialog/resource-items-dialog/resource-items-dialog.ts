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
import { MatDialogRef } from "@angular/material/dialog";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { TreeControlService } from "../../../common/services/tree-control-service";
import { ModelService } from "../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../widget/tree/model/tree-node-model";

@Component({
   selector: "resource-items-dialog",
   templateUrl: "./resource-items-dialog.html",
   styleUrls: ["./resource-items-dialog.scss"],
   providers: [
      TreeControlService
   ]
})
export class ResourceItemsDialog implements OnInit {
   tree: TreeNodeModel;

   constructor(private modelService: ModelService,
               private treeControl: TreeControlService,
               private dialogRef: MatDialogRef<ResourceItemsDialog>)
   {
   }

   ngOnInit(): void {
      this.modelService.getModel<TreeNodeModel>(
         EmUrlConstants.SECURITY_RESOURCES_PERMISSION_TREE).subscribe(tree =>
      {
         this.tree = tree;
      });
   }

   get selectedTreeNodes(): TreeNodeModel[] {
      return this.treeControl.selectNodes;
   }

   onSelectNode(info: MatTreeSelectedInfo): void {
      this.treeControl.onSelectNodes(info);
   }

   apply(): void {
      this.dialogRef.close(this.selectedTreeNodes);
   }

   cancel(): void {
      this.dialogRef.close(null);
   }
}
