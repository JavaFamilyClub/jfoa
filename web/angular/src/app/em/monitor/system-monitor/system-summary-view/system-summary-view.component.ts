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

import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { Observable, Subscription, timer as observableTimer } from "rxjs";
import { Searchable } from "../../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { DownloadService } from "../../../../download/download.service";
import { EChartModel } from "../../../../widget/chart/model/echart-model";
import { MatMessageDialog } from "../../../../widget/dialog/mat-message-dialog";
import { MatMsgModel } from "../../../../widget/dialog/mat-msg-model";
import { ModelService } from "../../../../widget/services/model.service";
import { SystemMonitorSummaryModel } from "../model/system-monitor-summary-model";

const HEARTBEAT_DELAY_TIME: number = 0;
const CHART_HEARTBEAT_DELAY_TIME: number = 200;
const PROPERTIES_HEARTBEAT_INTERVAL_TIME: number = 1000;
const CHART_HEARTBEAT_INTERVAL_TIME: number = 5000;
const CHART_MIN_WIDTH = 800;

@Searchable({
   title: "System Monitor Summary",
   route: "/em/monitor/system-monitor/summary",
   keywords: [
      "system summary", "server summary"
   ]
})
@Component({
   selector: "system-summary-view",
   templateUrl: "./system-summary-view.component.html",
   styleUrls: ["./system-summary-view.component.scss"]
})
export class SystemSummaryViewComponent implements OnInit, OnDestroy {
   @ViewChild("summaryContainer", { static: true }) container;
   model: SystemMonitorSummaryModel;
   heapMemoryChart: EChartModel;
   threadCountChart: EChartModel;
   memoryUsageChart: EChartModel;
   cpuUsageChart: EChartModel;
   private propertiesRefreshBeat: Observable<number>;
   private chartRefreshBeat: Observable<number>;
   private refreshSubjection: Subscription;
   private reconnectCount = 0;
   cols = 2;

   constructor(private dialog: MatDialog,
               private snackBar: MatSnackBar,
               private modelService: ModelService,
               private translate: TranslateService,
               private downloadService: DownloadService)
   {
   }

   private init() {
      this.propertiesRefreshBeat =
         observableTimer(HEARTBEAT_DELAY_TIME, PROPERTIES_HEARTBEAT_INTERVAL_TIME);
      this.chartRefreshBeat =
         observableTimer(CHART_HEARTBEAT_DELAY_TIME, CHART_HEARTBEAT_INTERVAL_TIME);

      this.refreshSubjection = this.propertiesRefreshBeat.subscribe(() => {
         this.refreshProperties((error => {
            this.reconnectCount++;

            if(this.reconnectCount < 3) {
               return;
            }

            this.stopRefreshBeat();

            this.dialog.open(MatMessageDialog, {
               data: {
                  title: this.translate.instant("Confirm"),
                  message: this.translate.instant("em.system.summary.reConnectMsg"),
                  confirm: true
               } as MatMsgModel
            }).afterClosed().subscribe(result => {
               if(result) {
                  this.init();
               }
            });
         }), () => {
            this.reconnectCount = 0;
         });
      });

      this.refreshSubjection.add(this.chartRefreshBeat.subscribe(() => {
         this.refreshChart();
      }));
   }

   ngOnInit(): void {
      this.init();
      this.calcGridCols();
   }

   ngOnDestroy(): void {
      this.stopRefreshBeat();
   }

   calcGridCols() {
      if(!!this.container) {
         const bounds = this.container.nativeElement.getBoundingClientRect();
         this.cols = bounds && bounds.width < CHART_MIN_WIDTH ? 1 : 2;
      }
   }

   private stopRefreshBeat(): void {
      if(this.refreshSubjection) {
         this.refreshSubjection.unsubscribe();
         this.refreshSubjection = null;
      }
   }

   refreshProperties(errorHandle: (error) => void, complete: () => void): void {
      this.modelService.getModel<SystemMonitorSummaryModel>(
         EmUrlConstants.MONITOR_SYSTEM_SUMMARY, null, false).subscribe(model =>
      {
         this.model = model;
      }, errorHandle, complete);
   }

   refreshChart(): void {
      this.modelService.getModel<EChartModel>(
         EmUrlConstants.SYSTEM_SUMMARY_HEAP_CHART, null, false)
         .subscribe(heapChart =>
      {
         this.heapMemoryChart = heapChart;
      }, error => {
         console.error("get heap chart model error:", error);
      });

      this.modelService.getModel<EChartModel>(
         EmUrlConstants.SYSTEM_SUMMARY_THREAD_CHART, null, false)
         .subscribe(threadChart =>
      {
         this.threadCountChart = threadChart;
      }, error => {
         console.error("get thread chart model error:", error);
      });

      this.modelService.getModel<EChartModel>(
         EmUrlConstants.SYSTEM_SUMMARY_MEMORY_CHART, null, false)
         .subscribe(memoryChart =>
      {
         this.memoryUsageChart = memoryChart;
      }, error => {
         console.error("get memory chart model error:", error);
      });

      this.modelService.getModel<EChartModel>(
         EmUrlConstants.SYSTEM_SUMMARY_CPU_CHART, null, false)
         .subscribe(cpuChart =>
      {
         this.cpuUsageChart = cpuChart;
      }, error => {
         console.error("get cpu chart model error:", error);
      });
   }

   downThreadDump(): void {
      this.downloadService.download(EmUrlConstants.MONITOR_SYSTEM_THREAD_DUMP);
   }

   gc(): void {
      this.modelService.getModel(EmUrlConstants.MONITOR_SYSTEM_GC).subscribe(() => {
         this.snackBar.open(this.translate.instant("em.system.summary.gcRunMsg"),
            this.translate.instant("Close"));
      });
   }
}
