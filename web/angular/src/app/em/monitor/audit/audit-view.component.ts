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

import { HttpParams } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatPaginator } from "@angular/material/paginator";
import { MatSnackBar } from "@angular/material/snack-bar";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { Moment } from "moment";
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { DateRangeFilter } from "../../../common/filter/date-range-filter";
import { ComponentTool } from "../../../common/util/component-tool";
import { GuiTool } from "../../../common/util/gui-tool";
import { Tool } from "../../../common/util/tool";
import { DownloadService } from "../../../download/download.service";
import { ExportDialog } from "../../../widget/export-dialog/export-dialog";
import { ExportModel } from "../../../widget/model/export-model";
import { ModelService } from "../../../widget/services/model.service";
import { Log } from "./model/log";

const LOG_ALL_URI = "/logs";

@Searchable({
  title: "Audit",
  route: "/em/monitor/audit",
  keywords: [
    "audit"
  ]
})
@Component({
  selector: "audit-view",
  templateUrl: "./audit-view.component.html",
  styleUrls: ["./audit-view.component.scss"]
})
export class AuditView implements OnInit {
  filter: DateRangeFilter;
  dataSource: MatTableDataSource<Log>;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  displayedColumns: string[]
     = ["id", "resource", "action", "customer", "ip", "date", "message"];

  constructor(private snackBar: MatSnackBar,
              private modalService: NgbModal,
              private modelService: ModelService,
              private translate: TranslateService,
              private downloadService: DownloadService)
  {
    this.resetFilter();
  }

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.modelService.putModel<Log[]>(LOG_ALL_URI, this.filter).subscribe((response) => {
      this.dataSource = new MatTableDataSource<Log>(response.body);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }

  resetFilter(): void {
    this.filter = {
      startDate: null,
      endDate: null
    };
  }

  showMessage(msg: string): void {
    // TODO translate buttonOptions
    ComponentTool.showMessageDialog(this.modalService,
       this.translate.instant("em.audit.errorDetail"), msg,
       {"ok": this.translate.instant("OK")}).then(() => {});
  }

  filterChanged(event: any, end = false): void {
    const value: Moment = event.value;

    if(value.isValid()) {
      const date = value.toDate();

      if(end) {
        if(!!this.filter.startDate && value.isBefore(this.filter.startDate)) {
          this.snackBar.open(this.translate.instant("em.audit.endBeforeStartError"));
          return;
        }

        this.filter.endDate = date;
      }
      else {
        this.filter.startDate = date;
      }

      this.refresh();
    }
  }

  clearFilter(end: boolean = false): void {
    if(end) {
      this.filter.endDate = null;
    }
    else {
      this.filter.startDate = null;
    }

    this.refresh();
  }

  openExportDialog(event: MouseEvent): void {
    event.stopPropagation();
    event.preventDefault();

    const exportDialog = ComponentTool.showDialog(this.modalService,
       ExportDialog, (model: ExportModel) =>
       {
         let params = new HttpParams()
            .set("format", model.type + "")
            .set("startDate", (this.filter?.startDate?.getTime() ?? -1) + "")
            .set("endDate", (this.filter?.endDate?.getTime() ?? -1) + "")
         ;

         const url = GuiTool.appendParams(
            Tool.API_VERSION + EmUrlConstants.AUDIT_EXPORT, params);

         this.downloadService.download(url);
       });
  }
}
