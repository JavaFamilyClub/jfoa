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

import { Component, Inject } from "@angular/core";
import { FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { TranslateService } from "@ngx-translate/core";

@Component({
  selector: "input-name-dialog",
  templateUrl: "./input-name-dialog.component.html",
  styleUrls: ["./input-name-dialog.component.scss"]
})
export class InputNameDialogComponent {
  title: string = this.translate.instant("dialog.inputName.title");
  placeholder: string = this.translate.instant("dialog.inputName.namePh");
  hint: string;
  name: string;
  validator: ValidatorFn | ValidatorFn[] = [Validators.required];
  validatorErrors: ValidationErrors = {
    required: this.translate.instant("user.profile.warn.nameRequired")
  };

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private translate: TranslateService,
              @Inject(MAT_DIALOG_DATA) private data: any)
  {
    this.title = data?.title ?? this.title;
    this.placeholder = data?.placeholder ?? this.placeholder;
    this.hint = data?.hint;
    this.name = data?.name;
    this.validator = data?.validator ?? this.validator;
    this.validatorErrors = data?.validatorErrors ?? this.validatorErrors;
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      "name": this.fb.control(this.name, this.validator)
    });

    this.form.get("name").valueChanges.subscribe(value => {
      this.name = value;
    });
  }

  get errorKeys(): string[] {
    return Object.keys(this.validatorErrors);
  }
}
