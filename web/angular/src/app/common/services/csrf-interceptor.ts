/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
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

import { DOCUMENT, ɵparseCookieValue as parseCookieValue } from "@angular/common";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";

const COOKIE_NAME = "XSRF-TOKEN";
const HEADER_NAME = "X-XSRF-TOKEN";
// const HEADER_NAME = "_csrf_heard";

/**
 * Handles setting the CSRF token in the HTTP request headers. We're not using Angular's provided
 * module because it ignores non-mutating requests (e.g. GET, HEAD).
 */
@Injectable()
export class CsrfInterceptor implements HttpInterceptor {
   private _token: string = null;
   private parsedCookies: string = null;

   constructor(@Inject(DOCUMENT) private document: any) {
   }

   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const token = this.token;

      if(token !== null && !req.headers.has(HEADER_NAME)) {
         req = req.clone({headers: req.headers.set(HEADER_NAME, token)});
      }

      return next.handle(req);
   }

   private get token(): string {
      const cookies = this.document.cookie || "";

      if(this.parsedCookies !== cookies) {
         this._token = parseCookieValue(cookies, COOKIE_NAME);
         this.parsedCookies = cookies;
      }

      return this._token;
   }
}