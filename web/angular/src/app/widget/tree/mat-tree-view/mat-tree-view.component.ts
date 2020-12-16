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

import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { MatTreeNestedDataSource } from "@angular/material/tree";
import { Tool } from "../../../common/util/tool";
import { MatTreeSelectedInfo } from "../model/mat-tree-selected-info";
import { TreeNodeModel } from "../model/tree-node-model";
import { MainNestedTreeControl } from "./main-nested-tree-control";

@Component({
   selector: "mat-tree-view",
   templateUrl: "./mat-tree-view.component.html",
   styleUrls: ["./mat-tree-view.component.scss"]
})
export class MatTreeViewComponent implements OnInit {
   _data: TreeNodeModel;
   _selectedNodes: TreeNodeModel[];
   @Input() expandFirst = true;
   @Input() isDisabledNode: (node: TreeNodeModel) => boolean = (n) => false;
   @Input() showRoot = false;
   @Input() multipleSelect = false;
   @Input() onlyLeafSelect = false;
   @Input() dbClickExpand = true;

   @Input() set data(data: TreeNodeModel) {
      this._data = data;

      this.dataSource.data = this.showRoot ? [data] : data.children;
   }

   @Input() set selectedNodes(selectedNodes: TreeNodeModel[]) {
      this._selectedNodes = selectedNodes;

      selectedNodes?.forEach(node => {
         this.treeControl.expand(node);
      });
   }

   get selectedNodes(): TreeNodeModel[] {
      return this._selectedNodes;
   }

   @Output() onSelectNode = new EventEmitter<MatTreeSelectedInfo>();

   treeControl = new MainNestedTreeControl(node => node.children);
   dataSource = new MatTreeNestedDataSource<TreeNodeModel>();

   constructor() {
   }

   ngOnInit(): void {
      if(this.expandFirst && !Tool.isEmpty(this.dataSource.data)) {
         this.treeControl.expand(this.dataSource.data[0])
      }
   }

   hasChild = (_: number, node: TreeNodeModel) => !!node.children && node.children.length > 0;

   selectNode(event: MouseEvent, node: TreeNodeModel): void {
      if(this.onlyLeafSelect && !node.leaf
         || !!this.isDisabled && this.isDisabled(node))
      {
         return;
      }

      if(!this.multipleSelect || !Tool.pressControlKey(event)) {
         this.onSelectNode.emit({
            event,
            nodes: [node]
         });

         return;
      }

      let nodes = this._selectedNodes || [];

      nodes.push(node);

      this.onSelectNode.emit({
         event,
         nodes
      });
   }

   isSelected(node: TreeNodeModel): boolean {
      return this.selectedNodes?.some(n => n === node);
   }

   dbClickFolder(node: any, event: MouseEvent): void {
      if(this.dbClickExpand) {
         event.stopPropagation();
         event.stopPropagation();
         this.treeControl.toggle(node);
      }
   }

   isDisabled(node: TreeNodeModel): boolean {
      return this.isDisabledNode(node);
   }
}
