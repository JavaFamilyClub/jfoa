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
import { PortalUrlConstants } from "../../../common/constants/url/portal-url-constants";
import { TextEditorModel } from "../../../widget/model/text-editor-model";
import { TextEditorState } from "../../../widget/rich-text-editor/text-editor-state";
import { BaseSubscription } from "../../../widget/base/BaseSubscription";
import { ModelService } from "../../../widget/services/model.service";
import { ArticleDtoModel } from "../article-model/article-dto-model";
import { EditArticleModel } from "../article-model/edit-article-model";

@Component({
   selector: "article-list",
   templateUrl: "./article-list.component.html",
   styleUrls: ["./article-list.component.scss"]
})
export class ArticleListComponent extends BaseSubscription {

}
