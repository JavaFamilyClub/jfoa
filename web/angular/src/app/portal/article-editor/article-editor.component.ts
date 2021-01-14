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

import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateService } from "@ngx-translate/core";
import { PortalUrlConstants } from "../../common/constants/url/portal-url-constants";
import { ArticleType } from "../../common/enum/article-type";
import { ComponentTool } from "../../common/util/component-tool";
import { ModelService } from "../../widget/services/model.service";
import { EditArticleModel } from "../article-model/edit-article-model";

@Component({
   selector: "article-editor",
   templateUrl: "./article-editor.component.html",
   styleUrls: ["./article-editor.component.scss"]
})
export class ArticleEditorComponent implements OnInit {
   model: EditArticleModel;
   placeholder: string = this.translate.instant("portal.toolbar.writeArticle");

   constructor(private router: Router,
               private activatedRoute: ActivatedRoute,
               private modalService: NgbModal,
               private translate: TranslateService,
               private modelService: ModelService)
   {
   }

   ngOnInit(): void {
      this.model = {
         type: ArticleType.Rich,
         title: "",
         description: "",
         tags: [],
         content: ""
      };
   }

   apply(): void {
      this.modelService.sendModel(PortalUrlConstants.ARTICLE, this.model)
         .subscribe(response => {
            const articleId = response.body;

            ComponentTool.showConfirmDialog(this.modalService,
               this.translate.instant("Success"),
               this.translate.instant("article.submitSuccessMsg")).then(result =>
            {
               if(result == "ok") {
                  this.router.navigate(["/portal/article/" + articleId], {
                     relativeTo: this.activatedRoute
                  }).then();
               }
            });
         });
   }
}
