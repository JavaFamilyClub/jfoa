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

import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { FileData } from "../../../common/data/file-data";
import { Platform } from "../../../common/enum/platform";
import { Tool } from "../../../common/util/tool";
import { ModelService } from "../../../widget/services/model.service";
import { ClientUploadModel } from "./model/client-upload.model";

@Searchable({
  title: "Client Manager",
  route: "/em/setting/client-manager",
  keywords: [
    "client manager"
  ]
})
@Component({
  selector: "client-manager",
  templateUrl: "./client-manager.component.html",
  styleUrls: ["./client-manager.component.scss"]
})
export class ClientManagerComponent implements OnInit {
  model: ClientUploadModel;

  @ViewChild("fileChooser") fileChooser: ElementRef<HTMLInputElement>;

  form: FormGroup;
  loading: boolean;

  platforms: {label: string, value: Platform}[] = [
    {
      label: "Mac",
      value: Platform.Mac
    },
    {
      label: "Linux",
      value: Platform.Linux
    },
    {
      label: "Win_x64",
      value: Platform.Win_x64
    }
  ];

  constructor(private fb: FormBuilder,
              private snackBar: MatSnackBar,
              private modelService: ModelService)
  {
  }

  ngOnInit(): void {
    this.reset();
  }

  private initForm(): void {
    this.form = this.fb.group({
      platform: this.fb.control(this.model.platform, [Validators.required]),
      version: this.fb.control(this.model.version, [Validators.required])
    });

    this.form.get("platform").valueChanges.subscribe((value) => {
      this.model.platform = value;
    });

    this.form.get("version").valueChanges.subscribe((value) => {
      this.model.version = value;
    });
  }

  reset(): void {
    this.model = {
      platform: Platform.Mac,
      version: ""
    };

    this.initForm();
  }

  browser(): void {
    if(!!this.fileChooser?.nativeElement) {
      this.fileChooser?.nativeElement.click();
    }
  }

  onFileChange(event: any): void {
    Tool.readFileData(event).subscribe(files => {
      if(files.length < 1 && !!!this.fileChooser.nativeElement.value) {
        return;
      }

      const value: FileData = files?.[0];

      if(!!!value) {
        return;
      }

      this.model.fileName = this.model.fileName || value.name;
      this.model.fileData = value;

      this.fileChooser.nativeElement.value = null;
    });
  }

  upload(): void {
    if(!!!this.model.fileData || !!!this.model.fileName || !!!this.model.version) {
      this.snackBar.open("File info and data missing.");
      return;
    }

    this.loading = true;

    this.modelService.sendModel(EmUrlConstants.UPLOAD_INSTALLER, this.model)
       .subscribe(() => {
         this.loading = false;
         this.model.fileData = null;
         this.snackBar.open("Installer upload success.");
       });
  }
}
