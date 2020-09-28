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

import {
   HttpEvent,
   HttpHandler,
   HttpInterceptor,
   HttpRequest,
   HttpResponse
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { NEVER } from "rxjs/internal/observable/never";
import { tap } from "rxjs/operators";

@Injectable()
export class HttpDebounceInterceptor implements HttpInterceptor {
   private requests = new Map<string, boolean>();

   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      if(req.method !== "GET") {
         return next.handle(req);
      }

      const key = req.urlWithParams;

      if(this.requests.has(key)) {
         return NEVER;
      }

      const result = next.handle(req).pipe(
         tap(
            (event) => {
               if(event instanceof HttpResponse) {
                  this.requests.delete(key);
               }
            },
            () => this.requests.delete(key)
         )
      );

      this.requests.set(key, true);
      return result;
   }
}
