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

import { AbstractControl } from "@angular/forms";
import { Observable, of as observableOf, Subject } from "rxjs";
import { isNumber as isNumeric } from "util";
import { environment } from "../../../environments/environment";
import { InstallerClientUrlConstants } from "../constants/url/installer-client-url-constants";
import { CommonsKVModel } from "../data/commons-kv-model";
import { FileData } from "../data/file-data";
import { Platform } from "../enum/platform";

declare var require: any;

/**
 * common util
 */
export namespace Tool {

    export const INSTALLER_URI = "https://javafamily.club/";
    // export const INSTALLER_URI = "http://localhost/"; // dev
    export const INSTALLER_CLIENT_API_VERSION = "client/api/1.0";
    export const BASE_API_VERSION = "api/1.0";
    export const INSTALLER_API_VERSION = INSTALLER_URI + BASE_API_VERSION;
    export const API_VERSION = "../" + BASE_API_VERSION;

    export const ADMIN = "admin";

    export const DOC_URL = "https://javafamilyclub.github.io/jfoa";
    export const DEV_DOC_URL = "https://javafamily.club/swagger-ui.html";

    export const isEquals: (obj0, obj1) => boolean = require("lodash/isEqual");
    export const clone: <T>(v: T) => T = require("lodash/cloneDeep");
    export const isEmpty: (any) => boolean = require("lodash/isEmpty");
    export const unescapeHTML: (string) => string = require("lodash/unescape");
    export const union: <T>(...arr: T[]) => T[] = require("lodash/union");
    export const orderBy = require("lodash/orderBy");

    // correct typing of first parameter - varargs isn"t possible in typescript
    export const intersectionWith: <T>(obj: T[], vals: T[], comparator: (v1: T, v2: T) => boolean) => T[] = require("lodash/intersectionWith");
    export const uniq: <T>(arr: T[]) => T[] = require("lodash/uniq");

    export const platforms: CommonsKVModel<string, Platform>[] = [
        {
            key: "Mac",
            value: Platform.Mac
        },
        {
            key: "Linux",
            value: Platform.Linux
        },
        {
            key: "Win_x64",
            value: Platform.Win_x64
        }
    ];

    /**
     * Encode a single non-ascii character to unicode enclosed in "[]"
     * @param ch single-character string
     * @param encodeDot whether to encode "."
     * @returns encoded string
     */
    function byteEncodeChar(ch: string, encodeDot = true): string {
        if(!ch) {
            return "";
        }

        let charCode: number = ch.charCodeAt(0);

        if(charCode < 128 && ch !== "[" && ch !== "]" && ch !== "/" &&
            ch !== "=" && ch !== "%" && ch !== "&" && ch !== "?" &&
            ch !== "#" && ch !== "'" && ch !== "<" && ch !== ">" &&
            ch !== "," && ch !== "\\" && ch !== "+" && ch !== ";" &&
            ch !== "(" && ch !== ")" && ch !== "{" && ch !== "}" && ch !== "`" &&
            (ch !== "." || !encodeDot) && ch !== "|") {
            return ch;
        }
        else {
            return "~_" + charCode.toString(16) + "_~";
        }
    }

    /**
     * Encode non-ascii characters to unicode enclosed in "[]".
     * @param source string
     * @param encodeDot whether to encode "."
     * @returns encoded string.
     */
    export function byteEncode(source: string, encodeDot = true): string {
        if(!source) {
            return "";
        }

        let ret: string = "";

        for(let ch of source) {
            ret += byteEncodeChar(ch, encodeDot);
        }

        return ret;
    }

    export function byteDecode(source: string): string {
        if((source == null) || (source == "")) {
            return "";
        }

        let arr = [];

        for(let i = 0; i < source.length; i++) {
            let ch = source.charAt(i);

            if(ch == "~" && i < source.length - 1 && source.charAt(i + 1) == "_") {
                let idx = source.indexOf("_~", i + 2);

                if(idx > i + 2) {
                    ch = String.fromCharCode(parseInt(source.substring(i + 2, idx), 16));
                    i = idx + 1;
                }
            }

            arr[i] = ch;
        }

        return arr.join("").replace(/%20/g, " ");
    }

    export function setFormControlDisabled(control: AbstractControl, val: boolean) {
        if(val) {
            control.disable({emitEvent: false});
        }
        else {
            control.enable({emitEvent: false});
        }
    }

    export function replaceStr(val: string, ostr: string, nstr: string): string {
        // Replace all ostr to nstr.
        let reg: RegExp = new RegExp(ostr, "g");

        return val.replace(reg, nstr);
    }

    export function hasKey(obj: Object, key: string): boolean {
        return obj ? Object.prototype.hasOwnProperty.call(obj, key) : false;
    }

    /**
     * Flattens a multidimensional array into a 1d array
     *
     * @param arr the array to flatten
     * @returns the flattened array
     */
    export function flatten(arr: any[]): any[] {
        return arr.reduce((a, b) => a.concat(Array.isArray(b) ? flatten(b) : b), []);
    }

    /**
     * Returns the result of n modulus m.
     */
    export function mod(n: number, m: number): number {
        return ((n % m) + m) % m;
    }

    /**
     * Checks whether two strings are equal, ignoring case.
     *
     * @param s1 the first string to check
     * @param s2 the second string to check
     * @returns true if the strings are equal when ignoring case, false otherwise
     */
    export function equalsIgnoreCase(s1: string, s2: string): boolean {
        if(s1 == null || s2 == null) {
            return s1 === s2;
        }

        return s1.toUpperCase() === s2.toUpperCase();
    }

    export function getMarginSize(border: string): number {
        // Parse the border width from the format string
        return border ? parseInt(border.substring(0, (border.indexOf("px"))), 10) : 0;
    }

    /**
     * Returns the browser-independent keycode of the keyboard event.
     *
     * @param event the keyboard event
     * @returns the keycode of the keyboard event
     */
    export function getKeyCode(event: KeyboardEvent): number {
        return event.which || event.keyCode || event.charCode;
    }

    // Encode url path by separating the /
    export function encodeURIPath(path: string): string {
        return path.split("/").map(p => encodeURIComponent(p)).join("/");
    }

    export function isArray(val: any) {
        return Object.prototype.toString.call(val) === "[object Array]";
    }

    // Checks if list of emails delimited by semicolon (;) is valid by checking against regex
    // Empty strings are treated as valid
    export function isValidEmail(val: string) {
        if(!val || val.length == 0) {
            return true;
        }

        const validEmailRegex = /^[\w\d!#$%&"*+\-/=?^_`{|}~]+(\.[\w\d!#$%&"*+\-/=?^_`{|}~]+)*@[\w\d\-_]+(\.[\w\d\-_]+)*$/;
        const addresses: string[] = val.split(";");

        // Return false if any of the emails are invalid
        return !addresses.map(str => str.trim()).some(str => str != "" && !validEmailRegex.test(str));
    }

    /**
     * Detect if an object is a numeric type.
     *    This type of detection is generally only string and number.
     * @param obj object
     */
    export function isNumber(obj: any) {
        return (+obj + "" === obj || isNumeric(obj)) && !isNaN(+obj);
    }

    export function readFileData(event: any): Observable<FileData[]> {
        if(!event || !event.target || !event.target.files || !event.target.files.length) {
            return observableOf([]);
        }

        const subject = new Subject<FileData[]>();
        const files = [];
        let count = 0;

        for(let i = 0; i < event.target.files.length; i++) {
            const file = event.target.files[i];
            const data = {
                name: file.name,
                content: ""
            };
            files.push(data);
            const reader = new FileReader();
            reader.readAsDataURL(file);

            reader.onload = () => {
                let result = <string> reader.result;

                if(!!result) {
                    if(result === "data:") {
                        // empty file
                        data.content = "";
                    }
                    else  if(result.startsWith("data:")) {
                        const index = result.indexOf(";base64,");

                        if(index < 0) {
                            data.content = "";
                        }
                        else {
                            data.content = result.substring(index + 8); // add length of ';base64,'
                        }
                    }
                    else {
                        data.content = result;
                    }
                }

                count += 1;

                if(count == event.target.files.length) {
                    subject.next(files);
                    subject.complete();
                }
            };
        }

        return subject.asObservable();
    }

    export function isInstaller(): boolean {
        // let base: HTMLBaseElement = <HTMLBaseElement> window.document.querySelector("base");
        //
        // return base.href.startsWith("file://");

        return environment.installer;
    }

    export function isMac(): boolean {
        const platform =  navigator.platform;

        return platform == "Mac68K" || platform == "MacPPC"
           || platform == "Macintosh" || platform == "MacIntel";
    }

    export function isLinux(): boolean {
        return navigator.platform.indexOf("Linux") > -1;
    }

    export function userPlatform(): Platform {
        if(Tool.isMac()) {
            return Platform.Mac;
        }
        else if(Tool.isLinux()) {
            return Platform.Linux;
        }

        return Platform.Win_x64;
    }

    export function requestPrefix(): string {
        return Tool.isInstaller() ? INSTALLER_API_VERSION : API_VERSION;
    }
}
