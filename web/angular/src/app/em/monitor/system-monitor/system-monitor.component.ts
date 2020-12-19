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

@Searchable({
   title: "System Monitor",
   route: "/em/monitor/system-monitor",
   keywords: [
      "system"
   ]
})
@Component({
  selector: "system-monitor",
  templateUrl: "./system-monitor.component.html",
  styleUrls: ["./system-monitor.component.scss"]
})
export class SystemMonitorComponent implements OnInit {

   model: any;

  constructor(private modelService: ModelService) { }

  ngOnInit(): void {
  }

  refresh(): void {
      this.modelService.getModel(EmUrlConstants.MONITOR_SYSTEM).subscribe(model => {
         this.model = model;
      });
  }

}
