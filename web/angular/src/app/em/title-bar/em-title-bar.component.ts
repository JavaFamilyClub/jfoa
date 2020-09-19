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

import { Component } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ComponentTool } from "../../common/util/component-tool";
import { GuiTool } from "../../common/util/gui-tool";
import { Tool } from "../../common/util/tool";
import { UserProfileDialog } from "../../portal/dialog/user-profile-dialog";
import { UserProfileDialogModel } from "../../portal/model/dialog/user-profile-dialog-model";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { NotifyAllDialog } from "../dialog/notify-all-dialog";
import { EmTab, EmTitleBarService } from "../service/em-title-bar.service";

const USER_PROFILE_UTI = "/user/profile";

@Component({
   selector: "em-title-bar",
   templateUrl: "em-title-bar.component.html",
   styleUrls: ["em-title-bar.component.scss"]
})
export class EmTitleBarComponent {

   constructor(private router: Router,
               private dialog: MatDialog,
               private modalService: NgbModal,
               private modelService: ModelService,
               private principalService: PrincipalService,
               private titleBarService: EmTitleBarService)
   {
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

   get principal(): JfPrincipal {
      return this.principalService.principal;
   }

   help(): void {
      GuiTool.openBrowserTab("https://javafamilyclub.github.io/jfoa");
   }

   editProfile(event: MouseEvent): void {
      this.modelService.getModel<UserProfileDialogModel>(USER_PROFILE_UTI)
         .subscribe((model) =>
         {
            const dialog = ComponentTool.showDialog(this.modalService,
               UserProfileDialog, () => {}, {
                  backdrop: "static",
                  size: "lg"
               });
            dialog.model = model;
         });
   }

   openNotifyDialog(): void {
      this.dialog.open(NotifyAllDialog, {
         minWidth: "30%"
      });
   }

   get isInstaller(): boolean {
      return Tool.isInstaller();
   }

   logout(): void {
      this.principalService.logout().then(() => {
         this.router.navigateByUrl("/portal").then();
      });
   }
}
