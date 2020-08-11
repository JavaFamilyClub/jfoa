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

import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from "@angular/material/table";
import { ModelService } from "../../../widget/services/model.service";
import { Log } from "./model/log";

const LOG_ALL_URI = "/logs";

@Component({
  selector: 'audit-view',
  templateUrl: './audit-view.component.html',
  styleUrls: ['./audit-view.component.scss']
})
export class AuditViewComponent implements OnInit {
  dataSource: MatTableDataSource<Log>;

  constructor(private modelService: ModelService) {
  }

  ngOnInit(): void {
    this.modelService.getModel<Log[]>(LOG_ALL_URI).subscribe((logs) => {
      this.dataSource = new MatTableDataSource<Log>(logs);
    });
  }

  displayedColumns: string[]
     = ["id", "resource", "action", "customer", "date", "message"];

}
