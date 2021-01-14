/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";
import { WidgetModule } from "../../widget/widget.module";
import { ArticleEditorComponent } from "./article-editor/article-editor.component";
import { ArticleItemComponent } from "./article-list/article-item/article-item.component";
import { ArticleListComponent } from "./article-list/article-list.component";
import { ArticleViewComponent } from "./article-view/article-view.component";
import { BlogAppRouting } from "./blog-app-routing.module";
import { BlogRootComponent } from "./blog-root.component";

@NgModule({
   declarations: [
      BlogRootComponent,
      ArticleListComponent,
      ArticleViewComponent,
      ArticleEditorComponent,
      ArticleItemComponent
   ],
   imports: [
      CommonModule,
      FormsModule,
      WidgetModule,
      BlogAppRouting,
      ReactiveFormsModule,
      TranslateModule
   ],
   bootstrap: [BlogRootComponent],
   entryComponents: [
   ],
   providers: [
   ]
})
export class BlogAppModule {
}

