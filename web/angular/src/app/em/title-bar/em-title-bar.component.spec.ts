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
import { TestBed, async } from "@angular/core/testing";
import { MatButtonModule } from "@angular/material/button";
import { MatDialog, MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatToolbarModule } from "@angular/material/toolbar";
import { BrowserModule } from "@angular/platform-browser";
import { RouterTestingModule } from "@angular/router/testing";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { of } from "rxjs";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { EmTab, EmTitleBarService } from "../service/em-title-bar.service";
import { EmTitleBarComponent } from "./em-title-bar.component";

describe("EmTitleBarComponent", () => {

   let modelService: any;
   let emTitleBarService: any;
   let principalService: any;
   let translate: any;

   beforeEach(async(() => {
      principalService = { refresh: jest.fn() };
      modelService = { getModel: jest.fn() };
      emTitleBarService = { changeTab: jest.fn() };
      emTitleBarService.currentTab = EmTab.MONITOR;

      translate = {
         get: jest.fn(() => of({})),
         getBrowserLang: jest.fn(() => of("en")),
         use: jest.fn(() => of({}))
      };

      TestBed.configureTestingModule({
         imports: [
            BrowserModule,
            RouterTestingModule,
            MatToolbarModule,
            MatButtonModule,
            MatMenuModule,
            MatDividerModule,
            MatDialogModule,
            MatIconModule,
            TranslateModule
         ],
         declarations: [
            EmTitleBarComponent
         ],
         providers: [
            {
               provide: ModelService,
               useValue: modelService
            },
            {
               provide: EmTitleBarService,
               useValue: emTitleBarService
            },
            {
               provide: PrincipalService,
               useValue: principalService
            },
            {
               provide: TranslateService,
               useValue: translate
            }
         ]
      }).compileComponents();
   }));

   it("should create the app", async(() => {
      const fixture = TestBed.createComponent(EmTitleBarComponent);
      const app = fixture.debugElement.componentInstance;
      expect(app).toBeTruthy();
   }));
});
