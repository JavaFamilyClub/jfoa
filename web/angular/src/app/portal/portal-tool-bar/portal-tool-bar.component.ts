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
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { JfoaEnvConstants } from "../../common/constants/jfoa-env-constants";
import { UserType } from "../../common/enum/user-type";
import { ComponentTool } from "../../common/util/component-tool";
import { GuiTool } from "../../common/util/gui-tool";
import { LocalStorage } from "../../common/util/local-storage.util";
import { Tool } from "../../common/util/tool";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { ChangePasswordDialog } from "../dialog/change-password-dialog";
import { UserProfileDialog } from "../dialog/user-profile-dialog";
import { UserProfileDialogModel } from "../model/dialog/user-profile-dialog-model";

const USER_PROFILE_UTI = "/user/profile";

@Component({
   selector: "portal-tool-bar",
   templateUrl: "portal-tool-bar.component.html",
   styleUrls: ["portal-tool-bar.component.scss"]
})
export class PortalToolBarComponent {

   constructor(private modalService: NgbModal,
               private router: Router,
               private modelService: ModelService,
               private translate: TranslateService,
               private principalService: PrincipalService)
   {
   }

   get principal(): JfPrincipal {
      return this.principalService.principal;
   }

   get lang(): string {
      const lang = this.translate.getBrowserLang();
      const userLang = LocalStorage.getItem(LocalStorage.USER_DEFINE_LANG);

      return userLang || lang || this.translate.getDefaultLang();
   }

   get isEN(): boolean {
      return this.lang === JfoaEnvConstants.LANG_EN;
   }

   get isZH(): boolean {
      return this.lang === JfoaEnvConstants.LANG_ZH;
   }

   changeLanguage(): void {
      let lang = this.isEN ? JfoaEnvConstants.LANG_ZH
         : JfoaEnvConstants.LANG_EN;

      this.principalService.changeLocale(lang).then(() => {
         location.reload();
      });
   }

   help(): void {
      GuiTool.openBrowserTab(Tool.DOC_URL);
   }

   devDoc(): void {
      GuiTool.openBrowserTab(Tool.DEV_DOC_URL);
   }

   editProfile(event: MouseEvent): void {
      if(!this.principal?.authenticated) {
         this.principalService.refresh();
         return;
      }

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

   get disableChangePassword(): boolean {
      return !this.principal?.authenticated
         || (this.principal?.type != UserType.User);
   }

   openChangePasswordDialog(): void {
      ComponentTool.showDialog(this.modalService,
         ChangePasswordDialog, () => {}, {
            backdrop: "static",
            size: "lg"
         });
   }

   get isInstaller(): boolean {
      return Tool.isInstaller();
   }

   logout(): void {
      this.principalService.logout().then();
   }
}
