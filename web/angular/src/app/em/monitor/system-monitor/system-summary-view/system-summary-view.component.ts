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

import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, timer as observableTimer } from "rxjs";
import { Searchable } from "../../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { BaseSubscription } from "../../../../widget/base/BaseSubscription";
import { ModelService } from "../../../../widget/services/model.service";
import { SystemMonitorSummaryModel } from "../model/system-monitor-summary-model";

const HEARTBEAT_DELAY_TIME: number = 0;
const HEARTBEAT_INTERVAL_TIME: number = 1000;

@Searchable({
   title: "System Monitor Summary",
   route: "/em/monitor/system-monitor/summary",
   keywords: [
      "system summary"
   ]
})
@Component({
   selector: 'system-summary-view',
   templateUrl: './system-summary-view.component.html',
   styleUrls: ['./system-summary-view.component.scss']
})
export class SystemSummaryViewComponent extends BaseSubscription implements OnInit, OnDestroy {
   model: SystemMonitorSummaryModel;
   private refreshBeat: Observable<number>;

   constructor(private modelService: ModelService) {
      super();
      this.refreshBeat = observableTimer(HEARTBEAT_DELAY_TIME, HEARTBEAT_INTERVAL_TIME);

      this.subscriptions.add(this.refreshBeat.subscribe(() => {
         this.refresh();
      }));
   }

   ngOnInit(): void {
   }

   ngOnDestroy(): void {
      super.ngOnDestroy();
   }

   refresh(): void {
      this.modelService.getModel<SystemMonitorSummaryModel>(
         EmUrlConstants.MONITOR_SYSTEM_SUMMARY).subscribe(model =>
      {
         this.model = model;
      });
   }
}
