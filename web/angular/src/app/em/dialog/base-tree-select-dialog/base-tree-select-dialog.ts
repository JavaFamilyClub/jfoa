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

import { Component, Inject, OnInit } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { TreeControlService } from "../../../common/services/tree-control-service";
import { ModelService } from "../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../widget/tree/model/tree-node-model";

@Component({
   selector: "base-tree-select-dialog",
   templateUrl: "./base-tree-select-dialog.html",
   styleUrls: ["./base-tree-select-dialog.scss"],
   providers: [
      TreeControlService
   ]
})
export class BaseTreeSelectDialog implements OnInit {
   private treeUrl: string;
   title: string;
   tree: TreeNodeModel;
   isDisabledNode: (node: TreeNodeModel) => boolean;

   constructor(private modelService: ModelService,
               private treeControl: TreeControlService,
               @Inject(MAT_DIALOG_DATA) private data: any,
               private dialogRef: MatDialogRef<BaseTreeSelectDialog>)
   {
      this.treeUrl = data.treeUrl;
      this.title = data.title;
      this.isDisabledNode = !!data.isDisabledNode
         ? data.isDisabledNode : (n) => false;
   }

   ngOnInit(): void {
      this.modelService.getModel<TreeNodeModel>(this.treeUrl)
         .subscribe(tree =>
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
