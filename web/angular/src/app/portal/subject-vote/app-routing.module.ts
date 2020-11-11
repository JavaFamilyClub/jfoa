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

import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SubjectCreateComponent } from "./subject-create/subject-create.component";
import { SubjectListComponent } from "./subject-list/subject-list.component";
import { SubjectVoteComponent } from "./subject-vote.component";

const appRoutes: Routes = [
   {
      path: "",
      component: SubjectVoteComponent,
      children: [
         {
            path: "subject-list",
            component: SubjectListComponent
         },
         {
            path: "subject-create",
            component: SubjectCreateComponent
         },
         {
            path: "**",
            redirectTo: "subject-list"
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
export class AppRoutingModule {
}