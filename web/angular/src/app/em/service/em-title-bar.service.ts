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

import { Injectable, OnDestroy } from "@angular/core";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { Subject } from "rxjs";

@Injectable()
export class EmTitleBarService implements OnDestroy {
   private tab: EmTab = EmTab.MONITOR;
   changeTabSubject = new Subject<EmTab>();

   constructor(private router: Router, private translate: TranslateService) {
      if(router.url.startsWith("/em/setting"))  {
         this.tab = EmTab.SETTING;
      }
   }

   ngOnDestroy(): void {
      if(!!this.changeTabSubject) {
         this.changeTabSubject.unsubscribe();
         this.changeTabSubject = null;
      }
   }

   get currentTab(): EmTab {
      return this.tab;
   }

   get tabDesc(): string {
      return this.tab == EmTab.MONITOR
         ? this.translate.instant("em.Monitoring")
         : this.translate.instant("em.Setting");
   }

   changeTab(tab: EmTab): void {
      this.tab = tab;
      this.changeTabSubject.next(tab);
   }
}

export enum EmTab {
   MONITOR = 1,
   SETTING = 2
}
