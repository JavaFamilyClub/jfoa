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
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ComponentTool } from "../../common/util/component-tool";
import { GuiTool } from "../../common/util/gui-tool";
import { JfPrincipal } from "../../widget/model/jf-principal";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
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
               private modelService: ModelService,
               private principalService: PrincipalService)
   {
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
               backdrop: 'static',
               size: 'lg'
            });
         dialog.model = model;
      });
   }
}
