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

import { Component, OnInit, ViewChild } from "@angular/core";
import { MatDatepickerInputEvent, MatSingleDateSelectionModel } from "@angular/material/datepicker";
import { MatPaginator } from "@angular/material/paginator";
import { MatSnackBar } from "@angular/material/snack-bar";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Moment } from "moment";
import { DateRangeFilter } from "../../../common/filter/date-range-filter";
import { ComponentTool } from "../../../common/util/component-tool";
import { Tool } from "../../../common/util/tool";
import { ModelService } from "../../../widget/services/model.service";
import { Log } from "./model/log";

const LOG_ALL_URI = "/logs";

@Component({
  selector: "audit-view",
  templateUrl: "./audit-view.component.html",
  styleUrls: ["./audit-view.component.scss"]
})
export class AuditViewComponent implements OnInit {
  filter: DateRangeFilter;
  dataSource: MatTableDataSource<Log>;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  displayedColumns: string[]
     = ["id", "resource", "action", "customer", "date", "message"];

  constructor(private modelService: ModelService,
              private snackBar: MatSnackBar,
              private modalService: NgbModal)
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

  isEmptyFilter(): boolean {
    return !!!this.filter?.startDate && !!!this.filter?.endDate;
  }

  showMessage(msg: string): void {
    ComponentTool.showMessageDialog(this.modalService, "Error Detail", msg)
       .then(() => {});
  }

  filterChanged(event: any, end = false): void {
    console.log("=========event==============", event);
    const value: Moment = event.value;

    if(value.isValid()) {
      const date = value.toDate();

      if(end) {
        if(!!this.filter.startDate && value.isBefore(this.filter.startDate)) {
          this.snackBar.open("The end date must after start date.");
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

}
