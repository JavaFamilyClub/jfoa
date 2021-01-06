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
import { ModelService } from "../../../../widget/services/model.service";
import { MonitorJvmDetailModel } from "../model/monitor-jvm-detail-model";

@Component({
   selector: "jvm-detail-view",
   templateUrl: "./jvm-detail-view.component.html",
   styleUrls: ["./jvm-detail-view.component.scss"]
})
export class JvmDetailViewComponent implements OnInit {
   model: MonitorJvmDetailModel;

   constructor(private modelService: ModelService) { }

   ngOnInit(): void {
      this.modelService.getModel<MonitorJvmDetailModel>(
         EmUrlConstants.MONITOR_JVM_DETAIL).subscribe(model =>
      {
         this.model = model;
      });
   }

}
