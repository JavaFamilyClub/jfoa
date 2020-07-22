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

import {
   HttpEvent,
   HttpHandler,
   HttpInterceptor, HttpParams,
   HttpRequest
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UriCodec } from "../util/uri-codec";

@Injectable()
export class HttpParamsCodecInterceptor implements HttpInterceptor {
   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      if(req.params) {
         const params = req.params.keys().reduce<HttpParams>((keyParams, key) => {
            return req.params.getAll(key).reduce<HttpParams>((valueParams, value) => {
               return valueParams.append(key, value);
            }, keyParams);
         }, new HttpParams({encoder: new UriCodec()}));
         req = req.clone({
            params: params
         });
      }

      return next.handle(req);
   }
}