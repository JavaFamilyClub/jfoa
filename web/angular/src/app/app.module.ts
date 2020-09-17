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

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { MAT_DATE_LOCALE } from "@angular/material/core";
import { MAT_DIALOG_DEFAULT_OPTIONS } from "@angular/material/dialog";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { CsrfInterceptor } from "./common/services/csrf-interceptor";
import { HttpDebounceInterceptor } from "./common/services/http-debounce-interceptor";
import { HttpParamsCodecInterceptor } from "./common/services/http-params-codec-interceptor";

export const httpInterceptorProviders = [
   {provide: HTTP_INTERCEPTORS, useClass: HttpDebounceInterceptor, multi: true},
   {provide: HTTP_INTERCEPTORS, useClass: HttpParamsCodecInterceptor, multi: true},
   // For POST request throws 403 error in SpringBoot + SpringSecurity
   {provide: HTTP_INTERCEPTORS, useClass: CsrfInterceptor, multi: true}
];

@NgModule({
   declarations: [
      AppComponent
   ],
   imports: [
      CommonModule,
      BrowserAnimationsModule,
      HttpClientModule,
      NgbModalModule,
      AppRoutingModule
   ],
   providers: [
      httpInterceptorProviders,
      {
         provide: MAT_DATE_LOCALE,
         useValue: "zh-CN"
      },
      {
         provide: MAT_DIALOG_DEFAULT_OPTIONS,
         useValue: {
            minWidth: "30%"
         }
      }
   ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
