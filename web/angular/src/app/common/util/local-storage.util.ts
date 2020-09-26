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

declare var window: any;

export class LocalStorage {
   static prefix = "__jfoa__";

   public static getItem(key: string): string | null {
      return window.localStorage.getItem(LocalStorage.prefix + key);
   }

   public static setItem(key: string, data: string): void {
      window.localStorage.setItem(LocalStorage.prefix + key, data);
   }

   /**
    * keys
    */
   static USER_DEFINE_LANG = "user-define-lang";
}
