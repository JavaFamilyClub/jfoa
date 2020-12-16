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
import { PortalAppComponent } from "./app.component";
import { ArticleViewComponent } from "./article-view/article-view.component";
import { WelcomeComponent } from "./welcome/welcome.component";

const appRoutes: Routes = [
   {
      path: "",
      component: PortalAppComponent,
      children: [
         {
            path: "welcome",
            component: WelcomeComponent
         },
         {
            path: "article/:articleId",
            component: ArticleViewComponent
         },
         {
            path: "subjectVote",
            loadChildren: () => import("./subject-vote/subject-vote-app.module").then(m => m.SubjectVoteAppModule)
         },
         {
            path: "mailAuthor",
            loadChildren: () => import("./mail-author/mail-author-app.module").then(m => m.MailAuthorAppModule)
         },
         {
            path: "clientDownload",
            loadChildren: () => import("./client-download/client-download-app.module").then(m => m.ClientDownloadAppModule)
         },
         {
            path: "**",
            redirectTo: "welcome"
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
export class PortalAppRoutingModule {
}
