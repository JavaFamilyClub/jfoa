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

import { NO_ERRORS_SCHEMA } from "@angular/core";
import { ComponentFixture, TestBed, waitForAsync } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { MatTreeModule } from "@angular/material/tree";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { TestUtils } from "../../../common/test/test-utils";
import { ModelService } from "../../../widget/services/model.service";
import { PrincipalService } from "../../../widget/services/principal-service";
import { WidgetModule } from "../../../widget/widget.module";
import { RoleManagerComponent } from "./role-manager.component";

describe("RoleManagerComponent", () => {
  let component: RoleManagerComponent;
  let fixture: ComponentFixture<RoleManagerComponent>;
  let modelService: any;
  let principalService: any;
  let translate: any;

  beforeEach(waitForAsync(() => {
    modelService = TestUtils.createModelService();
    principalService = TestUtils.createPrincipalService();
    translate = TestUtils.createTranslateService();

    TestBed.configureTestingModule({
      providers: [
        {
          provide: ModelService,
          useValue: modelService
        },
        {
          provide: PrincipalService,
          useValue: principalService
        },
         MatSnackBar,
        {
          provide: TranslateService,
          useValue: translate
        }
      ],
      imports: [
        NoopAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatSnackBarModule,
        TranslateModule,
        MatSidenavModule,
        WidgetModule,
        MatTreeModule,
        MatIconModule
      ],
      declarations: [
         RoleManagerComponent
      ],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
