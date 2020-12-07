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

import { Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { BaseSubscription } from "../../widget/base/BaseSubscription";

@Component({
   selector: "article-view",
   templateUrl: "./article-view.component.html",
   styleUrls: ["./article-view.component.scss"]
})
export class ArticleViewComponent extends BaseSubscription implements OnInit, OnDestroy {
   private url: string;
   loaded: boolean = false;
   @ViewChild("articleContent") articleContent: ElementRef;

   constructor(private route: ActivatedRoute,
               private renderer: Renderer2)
   {
      super();
   }

   ngOnInit(): void {
      this.subscriptions.add(this.route.paramMap.subscribe(params => {
         const articleUri = params.get("articleUri");

         if(articleUri !== this.url) {
            this.hiddenContent();
            this.url = articleUri;
            this.articleContent.nativeElement.setAttribute("src", this.url);
         }
      }));
   }

   ngOnDestroy() {
      super.ngOnDestroy();
   }

   showContent(): void {
      this.loaded = true;
      this.renderer.setStyle(this.articleContent, "display", "block");
   }

   hiddenContent(): void {
      this.renderer.setStyle(this.articleContent, "display", "none");
      this.loaded = false;
   }
}
