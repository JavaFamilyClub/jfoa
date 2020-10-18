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

import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from "@angular/core";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Tool } from "../../common/util/tool";
import { MatColumnIno } from "./mat-column-ino";

@Component({
  selector: "mat-table-view",
  templateUrl: "./mat-table-view.component.html",
  styleUrls: ["./mat-table-view.component.scss"]
})
export class MatTableView <T> implements OnInit {
   @Input() selectedItems: T[] = [];
   @Input() set data(dataSource: T[]) {
      this._dataSource = new MatTableDataSource(dataSource);

      if(dataSource?.length > 0) {
         this.dataSource.sort = this.sort;
         this.dataSource.paginator = this.paginator;
      }
   }

   @Input() set cols(cols: MatColumnIno[]) {
      this._cols = cols;

      this._displayedColumns = cols.map(col => col.name);
   }

   @Output() onRowSelected = new EventEmitter<T>();
   @ViewChild(MatSort, {static: true}) sort: MatSort;
   @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

   _dataSource: MatTableDataSource<T>;
   _cols: MatColumnIno[];
   _displayedColumns: string[];

   ngOnInit(): void {
   }

   get dataSource(): MatTableDataSource<T> {
      return this._dataSource;
   }

   get cols(): MatColumnIno[] {
      return this._cols;
   }

   get displayedColumns(): string[] {
      return this._displayedColumns;
   }

   isSelected(item: T): boolean {
      return this.selectedItems.some(it => Tool.isEquals(it, item));
   }

   selectRow(row: T): void {
      this.onRowSelected.emit(row);
   }

}
