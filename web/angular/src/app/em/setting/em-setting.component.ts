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

import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MatDrawer } from "@angular/material/sidenav";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { Searchable } from "../../common/annotation/searchable";
import { BaseSubscription } from "../../widget/base/BaseSubscription";
import { SideNavService } from "../service/side-nav.service";

@Searchable({
   title: "Setting",
   route: "/em/setting",
   keywords: [
      "setting"
   ]
})
@Component({
   selector: "em-setting",
   templateUrl: "em-setting.component.html",
   styleUrls: ["em-setting.component.scss"]
})
export class EmSettingComponent extends BaseSubscription implements OnInit, OnDestroy {
   @ViewChild("splitePane", { static: true }) splitePane: MatDrawer;
   searchText: string;

   constructor(private router: Router,
               private snackBar: MatSnackBar,
               private translate: TranslateService,
               private sidenavService: SideNavService)
   {
      super();
   }

   ngOnInit(): void {
      this.subscriptions.add(this.sidenavService.onSidenavToggle.subscribe(() => {
         this.splitePane.toggle();
      }));
   }

   search(): void {
      if(!!!this.searchText) {
         this.snackBar.open(this.translate.instant("searchEmptyError"));
         return;
      }

      const extras = {
         queryParams: { search: this.searchText }
      };
      this.router.navigate(["em/setting/search"], extras);
      this.searchText = "";
   }

}
