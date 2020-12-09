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

import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { TabItem } from "../model/tab-item";

@Component({
  selector: "security-manager",
  templateUrl: "./security-manager.component.html",
  styleUrls: ["./security-manager.component.scss"]
})
export class SecurityManagerComponent implements OnInit {

  constructor(private translateService: TranslateService) { }

  ngOnInit(): void {
  }

  tabItems: TabItem[] = [
    {
      label: this.translateService.instant("em.security.tabs.resources"),
      routerPath: "resources"
    },
    {
      label: this.translateService.instant("em.user.manager"),
      routerPath: null
    }
  ];

}
