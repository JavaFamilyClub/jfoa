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
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { EChartModel } from "../../../widget/chart/model/echart-model";
import { ModelService } from "../../../widget/services/model.service";

@Searchable({
  title: "Subject Request",
  route: "/em/monitor/subject-request",
  keywords: [
    "subject request"
  ]
})
@Component({
  selector: "subject-request-monitor",
  templateUrl: "./subject-request-monitor.component.html",
  styleUrls: ["./subject-request-monitor.component.scss"]
})
export class SubjectRequestMonitor implements OnInit {
   chartModel: EChartModel;

   constructor(private modelService: ModelService) {
      this.refresh();
   }

   ngOnInit(): void {
   }

   private refresh(): void {
      this.modelService.getModel<EChartModel>(EmUrlConstants.SUBJECT_REQUEST_CHART)
         .subscribe((model) =>
      {
         this.chartModel = model;
      });
   }
}
