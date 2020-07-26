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

import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { EMAppComponent } from "./app.component";

const appRoutes: Routes = [
   {
      path: "",
      component: EMAppComponent,
      children: [
         {
            path: "monitor",
            loadChildren: () => import("./monitor/em-monitor.module").then(m => m.EmMonitorModule)
         },
         {
            path: "setting",
            loadChildren: () => import("./setting/em-setting.module").then(m => m.EmSettingModule)
         },
         {
            path: "setting",
            loadChildren: () => import("./setting/em-setting.module").then(m => m.EmSettingModule)
         },
         {
            path: "**",
            redirectTo: "monitor"
         }
      ]
   }
];

@NgModule({
   imports: [
      RouterModule.forChild(appRoutes)
   ],
   exports: [
      RouterModule
   ]
})
export class EMAppRoutingModule {
}
