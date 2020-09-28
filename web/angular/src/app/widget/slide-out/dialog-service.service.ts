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

import { Injectable, Injector, OnDestroy } from "@angular/core";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { Subject ,  Subscription } from "rxjs";
import { UIContextService } from "../../common/services/ui-context.service";
import { SlideOutOptions } from "./slide-out-options";
import { SlideOutRef } from "./slide-out-ref";
import { SlideOutService } from "./slide-out.service";
import { Tool } from "../../common/util/tool";

/**
 * The service that delegates whether to open content in a modal or slide out panel.
 */
@Injectable()
export class DialogService implements OnDestroy {
   constructor(private modalService: NgbModal,
               private slideOutService: SlideOutService,
               private injector: Injector,
               private uiContext: UIContextService)
   {
      this.injector = Injector.create(
         {providers: [{provide: DialogService, useValue: this}], parent: injector});
      this.uiSub = uiContext.getObjectChange().subscribe(msg => {
         if(msg.action == "property") {
            this.panes.forEach(p => {
               if(p.ref.objectId == msg.objectId && p.ref.sheetId == msg.sheetId &&
                  (!msg.title || msg.title == p.ref.title))
               {
                  p.ref.changedByOthers = true;
               }
            });
         }
      });
   }

   ngOnDestroy() {
      if(this.uiSub) {
         this.uiSub.unsubscribe();
         this.uiSub = null;
      }
   }

   public container: string = null;
   private sheetId: string;
   private objectDeleted = new Subject<string>();
   private panes: {ref: SlideOutRef, options: SlideOutOptions, content: any}[] = [];
   private currentSlideouts: SlideOutRef[] = [];
   private uiSub: Subscription;
   private popupCount: number = 0;

   getCurrentSlideOuts(): SlideOutRef[] {
      return this.currentSlideouts;
   }

   hasSlideout(objectId: string): boolean {
      return this.currentSlideouts.some(s => s.objectId == objectId);
   }

   public setSheetId(id: string) {
      this.sheetId = id;
   }

   // called when object is deleted
   objectDelete(objectId: string) {
      this.objectDeleted.next(objectId);
   }

   // called when object name is changed
   objectRename(oname: string, nname: string) {
      this.getCurrentSlideOuts()
         .filter(s => s.objectId == oname)
         .forEach(s => s.objectId = nname);
      const panes = this.panes.filter(s => s.options.objectId == oname);
      panes.forEach(s => s.options.objectId = nname);
      panes.forEach(s => s.options.title = s.ref.title);
   }

   // called when object properties changed (commit/apply)
   objectChange(objectId: string, title: string = null) {
      this.uiContext.objectPropertyChanged(objectId, title);
   }

   // find SlideOutRef with same content and options
   private findExisting(content0: any, options0: SlideOutOptions): SlideOutRef {
      const entry = this.panes.find(
         p => this.compareSlideoutOptions(p.options, options0) && content0 == p.content);
      return entry ? entry.ref : null;
   }

   public dismissAllSlideouts() {
      this.currentSlideouts.forEach(s => s.dismiss());
      this.currentSlideouts = [];
   }

   public showSlideout(idx: number) {
      for(let i = 0; i < this.currentSlideouts.length; i++) {
         this.currentSlideouts[i].setOnTop(i == idx);
         this.currentSlideouts[i].setExpanded(i == idx);
      }
   }

   public showSlideoutFor(objectId: string) {
      this.currentSlideouts.forEach(s => {
         s.setOnTop(s.objectId == objectId);
         s.setExpanded(s.objectId == objectId);
      });
   }

   // check if slideout is on top of all others. a slide out is on top if it"s z-index
   // is set on top (1049), or if it"s the last slideout of all visible slideouts.
   isSlideoutOnTop(idx: number): boolean {
      if(this.currentSlideouts[idx].isOnTop()) {
         return true;
      }

      if(this.currentSlideouts.find(s => s.isOnTop())) {
         return false;
      }

      return idx == this.currentSlideouts.length - 1;
   }

   get openPopups(): number {
      return this.popupCount;
   }

   /*
    * Called when an NgbModal is closed
    */
   onModalClose() {
      // Decrement the pop up counter after keyboard events go through
      setTimeout(() => this.popupCount -= 1);
   }

   /**
    * Delegates open method to appropriate service based on boolean.
    */
   public open(content: any, options?: SlideOutOptions): any {
      if(!options || !options.popup) {
         if(this.container) {
            if(!options) {
               options = <SlideOutOptions> {};
            }

            if(!options.container) {
               options.container = this.container;
            }
         }

         let ref: SlideOutRef = this.findExisting(content, options);

         if(ref) {
            // dismiss the old slideout ref, we only want to handle onCommit of the most
            // recent ref
            ref.dismiss();
         }

         ref = this.slideOutService.open(content, options, this.injector);
         this.panes.push({ref: ref, options: options, content: content});
         let sheetSubscription: Subscription;
         let assemblySubscription: Subscription;
         this.currentSlideouts.push(ref);
         this.showSlideout(this.currentSlideouts.length - 1);

         ref.result.then(() => {
            this.currentSlideouts = this.currentSlideouts.filter(s => s != ref);
         }, () => {
            this.currentSlideouts = this.currentSlideouts.filter(s => s != ref);
         });

         if(this.sheetId) {
            sheetSubscription = this.uiContext.getSheetChange().subscribe(msg => {
               if(msg.sheetId == this.sheetId) {
                  switch(msg.action) {
                  case "show":
                     this.currentSlideouts.push(ref);
                     ref.setVisible(true);
                     break;
                  case "hide":
                     this.currentSlideouts = this.currentSlideouts.filter(s => s != ref);
                     ref.setVisible(false);
                     break;
                  case "close":
                     this.currentSlideouts = this.currentSlideouts.filter(s => s != ref);
                     ref.close();
                     break;
                  }
               }
            });

            assemblySubscription = this.objectDeleted.subscribe(msg => {
               if(msg == ref.objectId && ref.objectId) {
                  this.currentSlideouts = this.currentSlideouts.filter(s => s != ref);
                  ref.dismiss();
               }
            });
         }

         const cleanup = () => {
            if(sheetSubscription) {
               sheetSubscription.unsubscribe();
               assemblySubscription.unsubscribe();
            }

            this.panes = this.panes.filter(p => p.ref != ref);
         };

         ref.result.then(cleanup, cleanup);

         return ref;
      }
      else {
         let ref: NgbModalRef = this.modalService.open(content, options);

         if(ref) {
            this.popupCount += 1;
            ref.result.then(() => this.onModalClose(), () => this.onModalClose());
         }

         return ref;
      }
   }

   private compareSlideoutOptions(opt1: SlideOutOptions, opt2: SlideOutOptions): boolean {
      const noInj1 = Tool.clone(opt1);
      const noInj2 = Tool.clone(opt2);
      noInj1.injector = null;
      noInj2.injector = null;
      return Tool.isEquals(noInj1, noInj2);
   }
}

export function ViewerDialogServiceFactory(modalService: NgbModal,
                                           slideOutService: SlideOutService,
                                           injector: Injector,
                                           uiContext: UIContextService): DialogService
{
   const service = new DialogService(modalService, slideOutService, injector, uiContext);
   service.container = ".viewer-container";
   return service;
}

export function ComposerDialogServiceFactory(modalService: NgbModal,
                                             slideOutService: SlideOutService,
                                             injector: Injector,
                                             uiContext: UIContextService): DialogService
{
   const service = new DialogService(modalService, slideOutService, injector, uiContext);
   service.container = ".composer-body";
   return service;
}

export function BindingDialogServiceFactory(modalService: NgbModal,
                                            slideOutService: SlideOutService,
                                            injector: Injector,
                                            uiContext: UIContextService): DialogService
{
   const service = new DialogService(modalService, slideOutService, injector, uiContext);
   service.container = "binding-editor .split-content";
   return service;
}
