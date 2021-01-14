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

import { Component, HostListener, Input } from "@angular/core";
import { Router } from "@angular/router";
import { BaseSubscription } from "../../../../widget/base/BaseSubscription";
import { ArticleItemModel } from "../../article-model/article-item-model";

@Component({
   selector: "article-item",
   templateUrl: "./article-item.component.html",
   styleUrls: ["./article-item.component.scss"]
})
export class ArticleItemComponent {
   @Input() item: ArticleItemModel;

   constructor(private router: Router) {
   }

   @HostListener("click")
   public click(): void {
      this.router.navigateByUrl("/portal/blog/article/" + this.item.id).then();
   }
}
