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

import { Component, OnDestroy, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { Observable, Subscription, timer as observableTimer } from "rxjs";
import { Searchable } from "../../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../../common/constants/url/em-url-constants";
import { DownloadService } from "../../../../download/download.service";
import { MatMessageDialog } from "../../../../widget/dialog/mat-message-dialog";
import { MatMsgModel } from "../../../../widget/dialog/mat-msg-model";
import { ModelService } from "../../../../widget/services/model.service";
import { SystemMonitorSummaryModel } from "../model/system-monitor-summary-model";

const HEARTBEAT_DELAY_TIME: number = 0;
const HEARTBEAT_INTERVAL_TIME: number = 5000;

@Searchable({
   title: "System Monitor Summary",
   route: "/em/monitor/system-monitor/summary",
   keywords: [
      "system summary"
   ]
})
@Component({
   selector: "system-summary-view",
   templateUrl: "./system-summary-view.component.html",
   styleUrls: ["./system-summary-view.component.scss"]
})
export class SystemSummaryViewComponent implements OnInit, OnDestroy {
   model: SystemMonitorSummaryModel;
   private refreshBeat: Observable<number>;
   private refreshSubjection: Subscription;
   private reconnectCount = 0;

   constructor(private dialog: MatDialog,
               private snackBar: MatSnackBar,
               private modelService: ModelService,
               private translate: TranslateService,
               private downloadService: DownloadService)
   {
   }

   private init() {
      this.refreshBeat = observableTimer(HEARTBEAT_DELAY_TIME, HEARTBEAT_INTERVAL_TIME);

      this.refreshSubjection = this.refreshBeat.subscribe(() => {
         this.refresh((error => {
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
   }

   ngOnInit(): void {
      this.init();
   }

   ngOnDestroy(): void {
      this.stopRefreshBeat();
   }

   private stopRefreshBeat(): void {
      if(this.refreshSubjection) {
         this.refreshSubjection.unsubscribe();
         this.refreshSubjection = null;
      }
   }

   refresh(errorHandle: (error) => void, complete: () => void): void {
      this.modelService.getModel<SystemMonitorSummaryModel>(
         EmUrlConstants.MONITOR_SYSTEM_SUMMARY, null, false).subscribe(model =>
      {
         this.model = model;
      }, errorHandle, complete);
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
