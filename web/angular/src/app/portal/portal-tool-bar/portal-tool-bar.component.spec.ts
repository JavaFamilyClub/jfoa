/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

import { HttpClient, HttpHandler } from "@angular/common/http";
import { TestBed, async } from "@angular/core/testing";
import { MatButtonModule } from "@angular/material/button";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatToolbarModule } from "@angular/material/toolbar";
import { BrowserModule } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { RouterTestingModule } from "@angular/router/testing";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { observable, of } from "rxjs";
import { ModelService } from "../../widget/services/model.service";
import { PrincipalService } from "../../widget/services/principal-service";
import { PortalToolBarComponent } from "./portal-tool-bar.component";

describe("PortalToolBarComponent", () => {

   let router: any;
   let modelService: any;
   let http: any;
   let modalService: any;

   beforeEach(async(() => {
      modelService = {
         getModel: jest.fn(() => of({})),
         sendModel: jest.fn(),
         putModel: jest.fn()
      };

      modalService = {
         open: jest.fn()
      };

      http = {
         get: jest.fn(() => of({}))
      };

      TestBed.configureTestingModule({
         imports: [
            BrowserModule,
            RouterTestingModule,
            MatToolbarModule,
            MatButtonModule,
            MatIconModule,
            MatMenuModule,
            MatDividerModule
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
            PrincipalService
         ]
      }).compileComponents();
   }));

   it("should create the app", async(() => {
      const fixture = TestBed.createComponent(PortalToolBarComponent);
      const app = fixture.debugElement.componentInstance;
      expect(app).toBeTruthy();
   }));
});
