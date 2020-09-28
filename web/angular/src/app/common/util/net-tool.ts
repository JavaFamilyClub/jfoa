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

/**
 * Common network utility methods
 */
export namespace NetTool {
   export const COOKIE_NAME = "XSRF-TOKEN";
   export const PARAMETER_NAME = "_csrf";

   export function parseCookieValue(cookies: string, name: string): string {
      return cookies.split(";")
         .map(v => v.trim())
         .map(v => {
            const eq = v.indexOf("=");
            return [v.substring(0, eq), v.substring(eq + 1)];
         })
         .filter(pair => pair[0] == name)
         .map(pair => pair[1])[0];
   }

   export function tokenValue(): string {
      const cookies = document && document.cookie || "";

      return NetTool.parseCookieValue(cookies, COOKIE_NAME);
   }

   export function xsrfToken(): string {
      const token = tokenValue();
      if(!!token) {
         return `${PARAMETER_NAME}=${tokenValue()}`;
      }

      return null;
   }
}
