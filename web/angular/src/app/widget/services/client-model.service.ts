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
    HttpClient,
    HttpErrorResponse,
    HttpHeaders,
    HttpParams,
    HttpResponse
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { InstallerClientUrlConstants } from "../../common/constants/url/installer-client-url-constants";
import { ComponentTool } from "../../common/util/component-tool";
import { Tool } from "../../common/util/tool";

@Injectable({
    providedIn: "root"
})
export class ClientModelService {
    private readonly headers: HttpHeaders;
    private formHeaders: HttpHeaders;
    private _errorHandler: (error: any) => boolean;

    constructor(private http: HttpClient, private modalService: NgbModal) {
        this.headers = new HttpHeaders({
            "Content-Type": "application/json"
        });
        this.formHeaders = new HttpHeaders({
            "Content-Type": "application/x-www-form-urlencoded"
        });
    }

    get errorHandler(): (error: any) => boolean {
        return this._errorHandler;
    }

    set errorHandler(handler: (error: any) => boolean) {
        this._errorHandler = handler;
    }

    getModel<T>(controller: string, params?: HttpParams): Observable<T> {
        const options = {
            headers: this.headers,
            params: params
        };
        return this.http.get<T>(this.baseHref + controller, options).pipe(
            catchError((error) => this.handleError<T>(error))
        );
    }

        sendModel<T>(controller: string, model: any, params?: HttpParams): Observable<HttpResponse<T>> {
        return this.http.post<T>(this.baseHref + controller, model, {
            headers: this.headers,
            observe: "response",
            params: params }
        ).pipe(
            catchError((error) => this.handleError<HttpResponse<T>>(error))
        );
    }

    sendModelByForm<T>(controller: string, formValue: HttpParams, params?: HttpParams): Observable<HttpResponse<T>> {
        return this.http.post<T>(this.baseHref + controller, formValue.toString(), {
            headers: this.formHeaders,
            observe: "response",
            params: params }
        ).pipe(
            catchError((error) => this.handleError<HttpResponse<T>>(error))
        );
    }

    /**
     * Use put method to send model.
     */
    putModel<T>(controller: string, model: any, params?: HttpParams): Observable<HttpResponse<T>> {
        return this.http.put<T>(this.baseHref + controller, model, {
            headers: this.headers,
            observe: "response",
            params: params }
        ).pipe(
            catchError((error) => this.handleError<HttpResponse<T>>(error))
        );
    }

    private handleError<T>(res: HttpErrorResponse): Observable<T> {
        let error;

        try {
            error = res.error;
        }
        catch(ignore) {
        }

        let errMsg = (!!error && !!error.message) ? error.message :
            res.status == 403 ? "_#(js:server.error.connectionForbidden)" :
                error && !(error === "" || error instanceof ProgressEvent) ? error :
                    res.status ? `${res.status} - ${res.statusText}` : "_#(js:server.error.connectionLost)";

        if(!error || !this.errorHandler || !this.errorHandler(error)) {
            ComponentTool.showMessageDialog(this.modalService, "Error", errMsg).then(() => {});
        }

        return throwError(errMsg);
    }

    get baseHref(): string {
        return Tool.isInstaller()
           ? InstallerClientUrlConstants.CLIENT_BASE_HREF
           : Tool.API_VERSION;
    }
}
