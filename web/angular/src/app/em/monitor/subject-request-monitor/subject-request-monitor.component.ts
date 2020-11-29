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
import { ModelService } from "../../../widget/services/model.service";
import { SubjectRequestMonitorModel } from "./model/subject-request-monitor-model";

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
   model: SubjectRequestMonitorModel;

   constructor() {
   }

   ngOnInit(): void {
   }

   get summaryChart(): string {
      return EmUrlConstants.SUBJECT_REQUEST_CHART_SUMMARY;
   }

   get supportChart(): string {
      return EmUrlConstants.SUBJECT_REQUEST_CHART_SUPPORT + "?support=" + true;
   }

   get opposeChart(): string {
      return EmUrlConstants.SUBJECT_REQUEST_CHART_SUPPORT + "?support=" + false;
   }
}
