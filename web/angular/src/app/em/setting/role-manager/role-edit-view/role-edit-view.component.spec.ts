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
import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { TranslateModule } from "@ngx-translate/core";
import { TestUtils } from "../../../../common/test/test-utils";
import { ModelService } from "../../../../widget/services/model.service";

import { RoleEditViewComponent } from "./role-edit-view.component";

describe("RoleEditViewComponent", () => {
  let component: RoleEditViewComponent;
  let fixture: ComponentFixture<RoleEditViewComponent>;
  let modelService: any;

  beforeEach(waitForAsync(() => {
    modelService = TestUtils.createModelService();

    TestBed.configureTestingModule({
      declarations: [ RoleEditViewComponent ],
      providers: [
        {
          provide: ModelService,
          useValue: modelService
        }
      ],
      imports: [
         NoopAnimationsModule,
         MatCardModule,
         MatButtonModule,
         FormsModule,
         ReactiveFormsModule,
         TranslateModule
      ],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleEditViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
