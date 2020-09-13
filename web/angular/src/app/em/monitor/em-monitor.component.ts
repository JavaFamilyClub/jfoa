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

import { Component, OnInit, ViewChild } from "@angular/core";
import { MatDrawer } from "@angular/material/sidenav";
import { Searchable } from "../../common/annotation/searchable";
import { BaseSubscription } from "../../widget/base/BaseSubscription";
import { SideNavService } from "../service/side-nav.service";

@Searchable({
   title: "Monitor",
   route: "/em/monitor",
   keywords: [
      "monitor"
   ]
})
@Component({
   selector: "em-monitor",
   templateUrl: "em-monitor.component.html",
   styleUrls: ["em-monitor.component.scss"]
})
export class EmMonitorComponent extends BaseSubscription implements OnInit {
   @ViewChild("drawer", { static: true }) sidenav: MatDrawer;

   constructor(private sidenavService: SideNavService) {
      super();
   }

   ngOnInit(): void {
      this.subscriptions.add(this.sidenavService.onSidenavToggle.subscribe(() => {
         this.sidenav.toggle();
      }));
   }


}
