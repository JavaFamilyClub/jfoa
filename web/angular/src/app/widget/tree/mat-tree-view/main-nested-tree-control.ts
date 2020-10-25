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
import { NestedTreeControlOptions } from "@angular/cdk/tree/control/nested-tree-control";
import { Observable } from "rxjs";
import { TreeNodeModel } from "../model/tree-node-model";

/**
 * Tree control for maintain tree node expand status.
 */
export class MainNestedTreeControl extends NestedTreeControl<TreeNodeModel> {
   constructor(getChildren: (dataNode: TreeNodeModel) => (Observable<TreeNodeModel[]> | TreeNodeModel[] | undefined | null), options?: NestedTreeControlOptions<TreeNodeModel, TreeNodeModel> | undefined) {
      super(getChildren, options);

      this.isExpandable = (node: TreeNodeModel): boolean => {
         return !node.leaf;
      };

      this.isExpanded = (node: TreeNodeModel): boolean => {
         return node.expanded;
      };

      this.expand = (node: TreeNodeModel): void => {
         super.expand(node);
         node.expanded = true;
      };

      this.collapse = (node: TreeNodeModel): void => {
         super.collapse(node);
         node.expanded = false;
      };

      this.toggle = (node: TreeNodeModel): void => {
        super.toggle(node);
        node.expanded = !node.expanded;
      };
   }

   expand(dataNode: TreeNodeModel): void {
      super.expand(dataNode);
      dataNode.expanded = true;
   }

   collapse(dataNode: TreeNodeModel): void {
      super.collapse(dataNode);
      dataNode.expanded = false;
   }

   toggle(dataNode: TreeNodeModel): void {
      super.toggle(dataNode);
      dataNode.expanded = !dataNode.expanded;
   }

   isExpanded(dataNode: TreeNodeModel): boolean {
      return dataNode.expanded;
   }

}
