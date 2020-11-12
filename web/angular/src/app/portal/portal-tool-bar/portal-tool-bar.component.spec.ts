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

import { HttpClient } from "@angular/common/http";
import { TestBed, waitForAsync } from "@angular/core/testing";
import { MatButtonModule } from "@angular/material/button";
import { MatChipsModule } from "@angular/material/chips";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatToolbarModule } from "@angular/material/toolbar";
import { BrowserModule } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { of } from "rxjs";
import { TestUtils } from "../../common/test/test-utils";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { PortalToolBarComponent } from "./portal-tool-bar.component";

describe("PortalToolBarComponent", () => {

   let router: any;
   let modelService: any;
   let http: any;
   let modalService: any;
   let translate: any;

   beforeEach(waitForAsync(() => {
      modelService = TestUtils.createModelService();

      modalService = {
         open: jest.fn()
      };

      http = {
         get: jest.fn(() => of({}))
      };

      translate = TestUtils.createTranslateService();
      router = TestUtils.createRouter();

      TestBed.configureTestingModule({
         imports: [
            BrowserModule,
            MatToolbarModule,
            MatButtonModule,
            MatIconModule,
            MatMenuModule,
            MatDividerModule,
            MatChipsModule,
            TranslateModule
         ],
         declarations: [
            PortalToolBarComponent
         ],
         providers: [
            {
               provide: Router,
               useValue: router
            },
            {
               provide: NgbModal,
               useValue: modalService
            },
            {
               provide: HttpClient,
               useValue: http
            },
            {
               provide: ModelService,
               useValue: modelService
            },
            {
               provide: TranslateService,
               useValue: translate
            },
            PrincipalService
         ]
      }).compileComponents();
   }));

   it("should create the app", waitForAsync(() => {
      const fixture = TestBed.createComponent(PortalToolBarComponent);
      const app = fixture.debugElement.componentInstance;
      expect(app).toBeTruthy();
   }));
});
