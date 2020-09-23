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
import { Component, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { InstallerClientUrlConstants } from "../../common/constants/url/installer-client-url-constants";
import { Platform } from "../../common/enum/platform";
import { GuiTool } from "../../common/util/gui-tool";
import { Tool } from "../../common/util/tool";

@Component({
  selector: "app-client-download",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class ClientDownloadAppComponent implements OnInit {
   platform: Platform = Tool.userPlatform();
   Platform = Platform;

   docUrl = Tool.DOC_URL;

   constructor(private snackBar: MatSnackBar) {
   }

   ngOnInit(): void {
   }

   selectPlatform(platform: Platform = Tool.userPlatform()): void {
      this.platform = platform;
   }

   download(): void {
      if(this.platform != Platform.Mac) {
         this.snackBar.open("The platform client is under urgent development, " +
            "currently only supports Mac OS X client download, please continue" +
            " to pay attention to JavaFamily.");
         return;
      }

      let params = new HttpParams()
         .set("platform", this.platform + "")
         .set("version", "0.0.1")
         .set("fileName", "jfoa-client-darwin-x64.zip");

      GuiTool.openBrowserTab(Tool.requestPrefix() + InstallerClientUrlConstants.CLIENT_DOWNLOAD, params);
   }

   get downloadLabel(): string {
      switch(this.platform) {
         case Platform.Mac:
            return "Download for macOS";
         case Platform.Linux:
            return "Download for Linux";
         default:
            return "Download for Windows x64"
      }
   }

}
