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
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ContextHelp } from "../../common/annotation/context-help";
import { EmUrlConstants } from "../../common/constants/url/em-url-constants";
import { ExportType } from "../../common/enum/export-type";
import { ExportTool } from "../../common/util/export-tool";
import { GuiTool } from "../../common/util/gui-tool";
import { Tool } from "../../common/util/tool";
import { DownloadService } from "../../download/download.service";
import { ExportModel } from "../model/export-model";

@Component({
  selector: "export-dialog",
  templateUrl: "./export-dialog.html",
  styleUrls: ["./export-dialog.scss"]
})
export class ExportDialog implements OnInit {
  @Input() model: ExportModel;
  @Input() exportFormats: ExportType[] = [
     ExportType.Excel,
     ExportType.Excel_2003,
     ExportType.PDF
  ];

  form: FormGroup;

  @Output() onCommit = new EventEmitter<ExportModel>();
  @Output() onCancel = new EventEmitter<void>();

  constructor(private fb: FormBuilder,
              private downloadService: DownloadService)
  {
    this.model = {
      type: ExportType.Excel
    };

    this.form = this.fb.group({
      exportType: this.fb.control(this.model.type, [Validators.required])
    });
  }

  ngOnInit(): void {
  }

  getExportLabel(type: ExportType): string {
    return ExportTool.getExportLabel(type);
  }

  export(): void {
    let params = new HttpParams()
       .set("format", this.model.type + "");

    const url = GuiTool.appendParams(
       Tool.API_VERSION + EmUrlConstants.AUDIT_EXPORT, params);

    this.downloadService.download(url);

    this.onCommit.emit(this.model);
  }

}
