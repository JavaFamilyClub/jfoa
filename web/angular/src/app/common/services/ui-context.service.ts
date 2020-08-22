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

import { Injectable } from "@angular/core";
import { Observable ,  Subject } from "rxjs";

export interface SheetMessage {
   action: "show" | "hide" | "close";
   sheetId: string;
}

export interface ObjectMessage {
   action: "property" | "add" | "delete" | "rename";
   objectId: string;
   sheetId: string;
   title: string;
}

@Injectable()
export class UIContextService {
   private lastTabs: Map<string, string> = new Map();
   private sheetChange: Subject<SheetMessage> = new Subject<SheetMessage>();
   private objectChange: Subject<ObjectMessage> = new Subject<ObjectMessage>();
   private currentSheetId: string = null;

   isVS(): boolean {
      return true;
   }

   isAdhoc(): boolean {
      return false;
   }

   getDefaultTab(key: string, defaultTab: string): string {
      return this.lastTabs[key] || defaultTab;
   }

   setDefaultTab(key: string, defaultTab: string): void {
      this.lastTabs[key] = defaultTab;
   }

   getSheetChange(): Observable<SheetMessage> {
      return this.sheetChange.asObservable();
   }

   sheetShow(id: string) {
      this.sheetChange.next({action: "show", sheetId: id});
      this.currentSheetId = id;
   }

   sheetHide(id: string) {
      this.sheetChange.next({action: "hide", sheetId: id});

      if(this.currentSheetId == id) {
         this.currentSheetId = null;
      }
   }

   sheetClose(id: string) {
      this.sheetChange.next({action: "close", sheetId: id});

      if(this.currentSheetId == id) {
         this.currentSheetId = null;
      }
   }

   getCurrentSheetId(): string {
      return this.currentSheetId;
   }

   getObjectChange(): Observable<ObjectMessage> {
      return this.objectChange.asObservable();
   }

   objectPropertyChanged(objectId: string, title: string) {
      this.objectChange.next({action: "property", title: title,
                              objectId: objectId, sheetId: this.currentSheetId });
   }

   objectAdded(dragName: string) {
      this.objectChange.next({action: "add", title: null,
                              objectId: dragName, sheetId: this.currentSheetId });
   }

   objectDeleted(dragName: string) {
      this.objectChange.next({action: "delete", title: null,
                              objectId: dragName, sheetId: this.currentSheetId });
   }

   objectRenamed(objectId: string) {
      this.objectChange.next({action: "rename", title: null,
                              objectId: objectId, sheetId: this.currentSheetId });
   }
}
