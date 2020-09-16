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

import { Component, OnInit } from "@angular/core";
import { Tool } from "../common/util/tool";
import { ModelService } from "../widget/services/model.service";

export interface UserInfo {
   userName: string,
   password: string
}

@Component({
  selector: "app-login",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class LoginAppComponent implements OnInit {
   model: UserInfo = {
      userName: "",
      password: ""
   };

   constructor(private modelService: ModelService) {
   }

   ngOnInit(): void {
      document.body.className += " app-loaded";
   }

   get loginUrl(): string {
      return Tool.INSTALLER_URI + Tool.API_VERSION + "/login";
   }

   login(): void {
      this.modelService.sendModel(this.loginUrl, this.model).subscribe((res) => {
         console.log(res);
      }, (error) => {
         console.log(error);
      });
   }
}
