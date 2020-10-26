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
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { TestUtils } from "../../../common/test/test-utils";

import { InputNameDialogComponent } from "./input-name-dialog.component";

describe("InputNameDialogComponent", () => {
  let component: InputNameDialogComponent;
  let fixture: ComponentFixture<InputNameDialogComponent>;
  let translate: any;
  let dialogValue: any;

  beforeEach(waitForAsync(() => {
    translate = TestUtils.createTranslateService();
    dialogValue = {};

    TestBed.configureTestingModule({
      providers: [
        {
          provide: TranslateService,
          useValue: translate
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: dialogValue
        }
      ],
      imports: [
         MatDialogModule,
         NoopAnimationsModule,
         FormsModule,
         ReactiveFormsModule,
         TranslateModule,
         MatButtonModule
      ],
      declarations: [ InputNameDialogComponent ],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InputNameDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
