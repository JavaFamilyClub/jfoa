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

import { ComponentFixture, TestBed, waitForAsync } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { TestUtils } from "../../common/test/test-utils";
import { ModelService } from "../../widget/services/model.service";

import { ArticleViewComponent } from "./article-view.component";

describe("ArticleViewComponent", () => {
   let component: ArticleViewComponent;
   let fixture: ComponentFixture<ArticleViewComponent>;

   beforeEach(waitForAsync(() => {
      let translate = TestUtils.createTranslateService();
      let modelService = TestUtils.createModelService();

      TestBed.configureTestingModule({
         imports: [
            TranslateModule,
            RouterTestingModule
         ],
         providers: [
            {
               provide: TranslateService,
               useValue: translate
            },
            {
               provide: ModelService,
               useValue: modelService
            }
         ],
         declarations: [ ArticleViewComponent ]
      })
         .compileComponents();
   }));

   beforeEach(() => {
      fixture = TestBed.createComponent(ArticleViewComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it("should create", () => {
      expect(component).toBeTruthy();
   });
});
