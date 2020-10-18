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

import { ChangeDetectorRef, Component, ElementRef, NgZone, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from "@ngx-translate/core";
import { Observable } from "rxjs";
import { Searchable } from "../../../common/annotation/searchable";
import { EmUrlConstants } from "../../../common/constants/url/em-url-constants";
import { CommonsKVModel } from "../../../common/data/commons-kv-model";
import { FileData } from "../../../common/data/file-data";
import { Platform } from "../../../common/enum/platform";
import { Tool } from "../../../common/util/tool";
import { MatColumnIno } from "../../../widget/mat-table-view/mat-column-ino";
import { ModelService } from "../../../widget/services/model.service";
import { ClientUploadModel } from "./model/client-upload.model";
import { InstallerModel } from "./model/installer.model";

export enum ClientTabs {
  Manager,
  Upload
}

@Searchable({
  title: "Client Manager",
  route: "/em/setting/client-manager",
  keywords: [
    "client manager", "client upload"
  ]
})
@Component({
  selector: "client-manager",
  templateUrl: "./client-manager.component.html",
  styleUrls: ["./client-manager.component.scss"]
})
export class ClientManagerComponent implements OnInit {
  installer: InstallerModel;

  installers: InstallerModel[];
  selectedItems: InstallerModel[] = [];

  form: FormGroup;
  loading: boolean;
  tabIndex: number;

  platforms: CommonsKVModel<string, Platform>[] = Tool.platforms;

  constructor(private zone: NgZone,
              private fb: FormBuilder,
              private snackBar: MatSnackBar,
              private modelService: ModelService,
              private translate: TranslateService,
              private changeDetectorRef: ChangeDetectorRef)
  {
  }

  ngOnInit(): void {
    this.refresh();
    this.reset();
  }

  private refresh(): void {
    this.modelService.getModel<InstallerModel[]>(
       EmUrlConstants.INSTALLERS).subscribe(data =>
    {
      this.installers = data;
    });
  }

  private initForm(): void {
    this.form = this.fb.group({
      platform: this.fb.control(this.installer.platform, [Validators.required]),
      version: this.fb.control(this.installer.version, [Validators.required]),
      link: this.fb.control(this.installer.link, [Validators.required])
    });

    this.form.get("platform").valueChanges.subscribe((value) => {
      this.installer.platform = value;
    });

    this.form.get("version").valueChanges.subscribe((value) => {
      this.installer.version = value;
    });

    this.form.get("link").valueChanges.subscribe((value) => {
      this.installer.link = value;
    });
  }

  reset(): void {
    this.installer = {
      platform: Platform.Mac,
      version: "latest",
      link: ""
    };

    this.initForm();
  }

  get cols(): MatColumnIno[] {
    return [
      {
        label: this.translate.instant("ID"),
        name: "id"
      },
      {
        label: this.translate.instant("Platform"),
        name: "platform"
      },
      {
        label: this.translate.instant("Version"),
        name: "version"
      },
      {
        label: this.translate.instant("em.client.downloadLink"),
        name: "link"
      }
    ];
  }

  upload(): void {
    if(!!!this.installer?.link || !!!this.installer.version) {
      this.snackBar.open("File info and data missing.");
      return;
    }

    this.loading = true;

    this.modelService.sendModel(EmUrlConstants.UPLOAD_INSTALLER, this.installer)
       .subscribe(() => {
         this.loading = false;
         this.reset();
         this.changeDetectorRef.detectChanges();
         this.onChangeTab(ClientTabs.Manager.valueOf());
         this.snackBar.open("Installer upload success.");
       });
  }

  onChangeTab(index: number): void {
    this.tabIndex = index;

    if(index == ClientTabs.Manager.valueOf()) {
      this.refresh();
    }
  }

  selectItem(item: InstallerModel): void {
    // TODO multi select
    this.selectedItems = [ item ];
  }

  deleteSelected(): void {
    if(this.selectedItems?.length < 1) {
      return;
    }
  }
}
