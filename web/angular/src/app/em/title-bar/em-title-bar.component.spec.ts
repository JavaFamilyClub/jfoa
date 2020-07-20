/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

import { TestBed, async } from "@angular/core/testing";
import { BrowserModule } from "@angular/platform-browser";
import { RouterTestingModule } from "@angular/router/testing";
import { ModelService } from "../../widget/services/model.service";
import { EmTab, EmTitleBarService } from "../service/em-title-bar.service";
import { EmTitleBarComponent } from "./em-title-bar.component";

describe("EmTitleBarComponent", () => {

   let modelService: any;
   let emTitleBarService: any;

   beforeEach(async(() => {
      modelService = { getModel: jest.fn() };
      emTitleBarService = { changeTab: jest.fn() };
      emTitleBarService.currentTab = EmTab.MONITOR;

      TestBed.configureTestingModule({
         imports: [
            BrowserModule,
            RouterTestingModule
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
