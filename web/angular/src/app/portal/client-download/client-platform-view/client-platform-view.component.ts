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
import { Component, Input, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { InstallerClientUrlConstants } from "../../../common/constants/url/installer-client-url-constants";
import { Platform } from "../../../common/enum/platform";
import { GuiTool } from "../../../common/util/gui-tool";
import { Tool } from "../../../common/util/tool";
import { DownloadService } from "../../../download/download.service";

@Component({
  selector: "c-client-platform-view",
  templateUrl: "./client-platform-view.component.html",
  styleUrls: ["./client-platform-view.component.scss"]
})
export class ClientPlatformViewComponent implements OnInit {
  @Input() platform: Platform = Platform.Docker;
  Platform = Platform;

  constructor(private snackBar: MatSnackBar,
              private translate: TranslateService,
              private downloadService: DownloadService)
  {
  }

  ngOnInit(): void {
  }

  download(): void {
    if(this.platform != Platform.Mac) {
      this.snackBar.open(this.translate.instant("portal.installer.unSupportError"));
      return;
    }

    let params = new HttpParams()
       .set("platform", this.platform + "")
       .set("version", "0.0.1")
       .set("fileName", "jfoa-client-darwin-x64.zip");

    const url = Tool.requestPrefix() + InstallerClientUrlConstants.CLIENT_DOWNLOAD;
    this.downloadService.download(GuiTool.appendParams(url, params));
  }

  get downloadLabel(): string {
    switch(this.platform) {
      case Platform.Mac:
        return "Download for Mac OS X";
      case Platform.Linux:
        return "Download for Linux";
      default:
        return "Download for Windows x64";
    }
  }

}
