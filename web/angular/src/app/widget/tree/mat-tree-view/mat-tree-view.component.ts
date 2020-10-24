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
import { Component, Input, OnInit } from "@angular/core";
import { MatTreeNestedDataSource } from "@angular/material/tree";
import { TreeNodeModel } from "../model/tree-node-model";

@Component({
  selector: "mat-tree-view",
  templateUrl: "./mat-tree-view.component.html",
  styleUrls: ["./mat-tree-view.component.scss"]
})
export class MatTreeViewComponent implements OnInit {
  _data: TreeNodeModel;
  @Input() showRoot = true;

  @Input() set data(data: TreeNodeModel) {
    this._data = data;

    this.dataSource.data = this.showRoot ? [data] : data.children;
  }

  treeControl = new NestedTreeControl<TreeNodeModel>(node => node.children);
  dataSource = new MatTreeNestedDataSource<TreeNodeModel>();

  constructor() {
  }

  ngOnInit(): void {
  }

  hasChild = (_: number, node: TreeNodeModel) => !!node.children && node.children.length > 0;

}
