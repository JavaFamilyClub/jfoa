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

import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { InstallerClientUrlConstants } from "../common/constants/url/installer-client-url-constants";
import { ModelService } from "../widget/services/model.service";
import { PrincipalService } from "../widget/services/principal-service";

export interface UserInfo {
   userName: string;
   password: string;
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

   constructor(private modelService: ModelService,
               private principalService: PrincipalService,
               private router: Router)
   {
   }

   ngOnInit(): void {
      document.body.className += " app-loaded";
   }

   login(): void {
      const params = new HttpParams()
         .set("userName", this.model.userName)
         .set("password", this.model.password);

      this.modelService.sendModel<string>(InstallerClientUrlConstants.LOGIN_URI, params)
         .subscribe((res) =>
      {
         console.log("login response:", res);

         if(!!!res?.body) {
            this.principalService.refresh();
            this.router.navigateByUrl("/portal");
         }
      }, (error) => {
         console.log("error:", error);
      });
   }
}
