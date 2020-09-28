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

import { Injectable, NgZone } from "@angular/core";

export interface DebounceCallback {
   callback: DebounceFn;
   args: any[];
}

export type DebounceFn = (...args: any[]) => any;
export type DebounceReducer =
   (previous: DebounceCallback, next: DebounceCallback) => DebounceCallback;

const defaultReducer = (previous, next) => next;

@Injectable()
export class DebounceService {
   private callbacks = new Map<string, {callback: DebounceCallback, timeoutId: any}>();

   constructor(private zone: NgZone) {
   }

   debounce(key: string, fn: DebounceFn, delay: number, args: any[],
            reducer: DebounceReducer = defaultReducer): void
   {
      let next = {callback: fn, args};

      if(this.callbacks.has(key)) {
         const previous = this.callbacks.get(key);
         next = reducer(previous.callback, next);
         clearTimeout(previous.timeoutId);
         this.callbacks.delete(key);
      }

      // debounce should not cause an extra change detection
      this.zone.runOutsideAngular(() => {
         const timeoutId = setTimeout(() => {
            if(this.callbacks.has(key)) {
               const {callback} = this.callbacks.get(key);
               callback.callback.apply(null, callback.args);
               this.callbacks.delete(key);
            }
         }, delay);

         this.callbacks.set(key, {callback: next, timeoutId: timeoutId});
      });
   }

   cancel(key: string): void {
      if(this.callbacks.has(key)) {
         clearTimeout(this.callbacks.get(key).timeoutId);
         this.callbacks.delete(key);
      }
   }
}
