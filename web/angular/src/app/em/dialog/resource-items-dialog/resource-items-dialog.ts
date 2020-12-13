import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from "@angular/material/dialog";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { TreeControlService } from "../../../common/services/tree-control-service";
import { ModelService } from "../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../widget/tree/model/tree-node-model";

@Component({
   selector: 'resource-items-dialog',
   templateUrl: './resource-items-dialog.html',
   styleUrls: ['./resource-items-dialog.scss'],
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
