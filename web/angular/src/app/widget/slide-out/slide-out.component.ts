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

import { Component, Output, EventEmitter, HostListener, Input, HostBinding,
         ViewChild, ElementRef, Renderer2 } from "@angular/core";
import { DialogService } from "./dialog-service.service";
import { ModalDismissReasons } from "@ng-bootstrap/ng-bootstrap";
import { SlideOutRef } from "./slide-out-ref";

@Component({
   selector: "slide-out",
   templateUrl: "slide-out.component.html",
   styleUrls: ["slide-out.component.scss"]
})
export class SlideOutComponent {
   @Input() title: string = "";
   @Input() keyboard: boolean = true;
   @Input() size: "sm" | "lg" = null;
   @HostBinding("class") @Input() side: "left" | "right" = "left";
   @Input() windowClass: string = null;
   @Input() changedByOthers: boolean = false;
   @Output() dismiss: EventEmitter<any> = new EventEmitter<any>();
   public visible: boolean = true;
   open: boolean = true;
   @ViewChild("contentContainer") contentContainer: ElementRef;
   private resizeListener: () => void;
   private resizeEndListener: () => void;
   explicitWidth: number = null;
   resizeX: number = null;
   resizeW: number = null;

   constructor(private dialogService: DialogService,
               private renderer: Renderer2)
   {
   }

   // z-index, under modal 1050, and top slideout 10490
   @HostBinding("style.z-index") zIndex = 10480;

   setOnTop(onTop: boolean) {
      this.zIndex = onTop ? 10490 : 10480;
   }

   isOnTop(): boolean {
      return this.zIndex > 10480;
   }

   isSlideoutOnTop(idx: number): boolean {
      return this.dialogService.isSlideoutOnTop(idx);
   }

   setExpanded(expanded: boolean): void {
      this.open = expanded;
   }

   isExpanded(): boolean {
      return this.open;
   }

   toggle(): void {
      this.open = !this.open;
   }

   get sizeClass(): string {
      return this.size ? "modal-" + this.size : null;
   }

   @HostListener("document:keyup.esc", ["$event"])
   escKey(event): void {
      if(this.keyboard && !event.defaultPrevented && this.dialogService.openPopups == 0) {
         this.dismissComponent(ModalDismissReasons.ESC);
      }
   }

   dismissComponent(reason: any): void {
      this.dismiss.emit(reason);
   }

   getCurrentSlideouts(): SlideOutRef[] {
      return this.dialogService.getCurrentSlideOuts();
   }

   dismissAll() {
      this.dialogService.dismissAllSlideouts();
   }

   showSlideout(idx: number) {
      this.dialogService.showSlideout(idx);
   }

   startResize(event: MouseEvent) {
      this.resizeX = event.pageX;
      this.explicitWidth = this.contentContainer.nativeElement.getBoundingClientRect().width;
      this.resizeW = this.explicitWidth;

      this.resizeListener = this.renderer.listen("document", "mousemove", (evt: MouseEvent) => {
         this.explicitWidth = this.resizeW + evt.pageX - this.resizeX;
         evt.preventDefault();
      });
      this.resizeEndListener = this.renderer.listen("document", "mouseup", () => {
         this.resizeListener();
         this.resizeEndListener();
      });
   }

   renameTitle(oldObjectId: string, newObjectId: string): void {
      if(!!newObjectId && !!this.title) {
         this.title = this.title.replace(oldObjectId, newObjectId);
      }
   }
}
