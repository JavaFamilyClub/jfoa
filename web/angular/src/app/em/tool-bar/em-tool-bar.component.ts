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
import { EmTitleBarService } from "../service/em-title-bar.service";
import { SideNavService } from "../service/side-nav.service";

@Component({
   selector: "em-tool-bar",
   templateUrl: "em-tool-bar.component.html",
   styleUrls: ["em-tool-bar.component.scss"]
})
export class EmToolBarComponent {
   constructor(private sidenavService: SideNavService,
               private titleBarService: EmTitleBarService)
   {
   }

   get isToggle(): boolean {
      return this.sidenavService.isToggle;
   }

   toggle(): void {
      this.sidenavService.toggle();
   }

   get tabDesc(): string {
      return this.titleBarService.tabDesc;
   }

}
