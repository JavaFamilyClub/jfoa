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
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { TreeControlService } from "../../../../common/services/tree-control-service";
import { ModelService } from "../../../../widget/services/model.service";
import { MatTreeSelectedInfo } from "../../../../widget/tree/model/mat-tree-selected-info";
import { TreeNodeModel } from "../../../../widget/tree/model/tree-node-model";
import { ResourcesManagerModel } from "./resources-manager-model";

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

   constructor(private modelService: ModelService,
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
   }

}
