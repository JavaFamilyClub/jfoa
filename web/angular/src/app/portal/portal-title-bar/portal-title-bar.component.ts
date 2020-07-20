/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
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
import { GuiTool } from "../../common/util/gui-tool";

@Component({
   selector: "portal-title-bar",
   templateUrl: "portal-title-bar.component.html",
   styleUrls: ["portal-title-bar.component.scss"]
})
export class PortalTitleBarComponent {
   help(): void {
      GuiTool.openBrowserTab("https://dreamli1314.github.io/angboot/");
   }
}
