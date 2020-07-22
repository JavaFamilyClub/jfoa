/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { EmUrlConstants } from "../../common/constants/url/em-url-constants";
import { GuiTool } from "../../common/util/gui-tool";
import { ModelService } from "../../widget/services/model.service";
import { EmTab, EmTitleBarService } from "../service/em-title-bar.service";

@Component({
   selector: "em-title-bar",
   templateUrl: "em-title-bar.component.html",
   styleUrls: ["em-title-bar.component.scss"]
})
export class EmTitleBarComponent {

   constructor(private router: Router,
               private modelService: ModelService,
               private titleBarService: EmTitleBarService) {
   }

   readonly MONITOR_PAGE = EmTab.MONITOR;
   readonly SETTING_PAGE = EmTab.SETTING;

   get currentTab(): EmTab {
      return this.titleBarService.currentTab;
   }

   changeTab(tab: EmTab): void {
      this.titleBarService.changeTab(tab);

      let tabURL = "em/monitor";

      if(tab == EmTab.SETTING) {
         tabURL = "em/setting";
      }

      this.router.navigateByUrl(tabURL);
   }

   logout(): void {
      // TODO redirect to login page by interceptor.
      this.modelService.getModel(EmUrlConstants.LOGOUT_URL).subscribe((status) => {
      });
   }

   help(): void {
      GuiTool.openBrowserTab("https://dreamli1314.github.io/angboot/");
   }
}
