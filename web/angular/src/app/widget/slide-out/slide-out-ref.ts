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

import { SlideOutComponent } from "./slide-out.component";
import { ComponentRef } from "@angular/core";
import { ContentRef } from "@ng-bootstrap/ng-bootstrap/util/popup";

/**
 * A reference to a newly opened slide out component. (Equivalent to NgbModalRef)
 */
export class SlideOutRef {
   private resolve: (result?: any) => void;
   private reject: (reason?: any) => void;
   private _changedByOthers: boolean = false;

   get objectId(): string {
      return this._objectId;
   }

   set objectId(id: string) {
      this.slideOutRef.instance.renameTitle(this._objectId, id);
      this._objectId = id;
   }

   get changedByOthers(): boolean {
      return this._changedByOthers;
   }

   set changedByOthers(changedByOthers: boolean) {
      this._changedByOthers = changedByOthers;
      this.slideOutRef.instance.changedByOthers = changedByOthers;
   }

   /**
    * The instance of component used as slide out"s content.
    * Undefined when a TemplateRef is used.
    */
   get componentInstance(): any {
      if(this.contentRef.componentRef) {
         return this.contentRef.componentRef.instance;
      }
   }

   set componentInstance(instance: any) {}

   /**
    * A promise that is resolved when the component is closed
    * and rejected when the component is dismissed.
    */
   result: Promise<any>;

   constructor(private slideOutRef: ComponentRef<SlideOutComponent>,
               private contentRef: ContentRef,
               private backdropCmptRef: ComponentRef<any>,
               private _objectId: string,
               public sheetId: string,
               private beforeDismiss?: Function)
   {
      this.slideOutRef.instance.dismiss
         .subscribe((reason: any) => {
            this.dismiss(reason);
      });

      this.result = new Promise((resolve, reject) => {
         this.resolve = resolve;
         this.reject = reject;
      });
      this.result.then(null, () => {});
   }

   get title(): string {
      return this.slideOutRef.instance.title;
   }

   /**
    * Can be used to close slide, passing an optional result.
    */
   close(result?: any): void {
      if(this.slideOutRef) {
         this.resolve(result);
         this.removeSlideOut();
      }
   }

   /**
    * Can be used to dismiss slide, passing an optional reason.
    */
   dismiss(reason?: any): void {
      if(this.slideOutRef) {
         if(!this.beforeDismiss || this.beforeDismiss() !== false) {
            this.reject(reason);
            this.removeSlideOut();
         }
      }
   }

   setExpanded(expanded: boolean): void {
      if(this.slideOutRef) {
         this.slideOutRef.instance.setExpanded(expanded);
      }
   }

   setVisible(vis: boolean) {
      if(this.slideOutRef) {
         this.slideOutRef.instance.visible = vis;
      }
   }

   // set z-index to place slide out on top of others.
   setOnTop(onTop: boolean) {
      if(this.slideOutRef) {
         this.slideOutRef.instance.setOnTop(onTop);
      }
   }

   isOnTop(): boolean {
      return this.slideOutRef && this.slideOutRef.instance.isOnTop();
   }

   isExpanded(): boolean {
      return this.slideOutRef && this.slideOutRef.instance.isExpanded();
   }

   private removeSlideOut(): void {
      const slideContainer = this.slideOutRef.location.nativeElement;

      if(this.backdropCmptRef) {
         slideContainer.parentNode.removeChild(this.backdropCmptRef.location.nativeElement);
         this.backdropCmptRef.destroy();
      }

      slideContainer.parentNode.removeChild(slideContainer);
      this.slideOutRef.destroy();

      if (this.contentRef && this.contentRef.viewRef) {
         this.contentRef.viewRef.destroy();
      }

      this.backdropCmptRef = null;
      this.slideOutRef = null;
      this.contentRef = null;
   }
}
