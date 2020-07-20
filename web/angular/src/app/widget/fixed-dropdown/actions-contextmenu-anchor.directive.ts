/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
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
   Directive,
   ElementRef,
   EventEmitter,
   Input,
   OnDestroy,
   OnInit,
   Output,
   Renderer2
} from "@angular/core";
import { Subscription } from "rxjs";
import { AssemblyActionGroup } from "../../common/action/assembly-action-group";
import { Point } from "../../common/data/point";
import { ActionsContextmenuComponent } from "./actions-contextmenu.component";
import { DropdownOptions } from "./dropdown-options";
import { DropdownRef } from "./fixed-dropdown-ref";
import { FixedDropdownService } from "./fixed-dropdown.service";

@Directive({
   selector: "[actionsContextmenuAnchor]"
})
export class ActionsContextmenuAnchorDirective implements OnInit, OnDestroy {
   @Input() position: Point = new Point();
   @Output() onContextmenuOpened = new EventEmitter<any>();
   @Output() onContextmenuClosed = new EventEmitter<any>();
   private _actions: AssemblyActionGroup[];
   private dropdownRef: DropdownRef | null;
   private dropdownSubscription: Subscription = new Subscription();
   private childClickedSubscription: Subscription;
   private _enabled = true;
   private listener: () => void = null;

   @Input()
   set actions(actions: AssemblyActionGroup[]) {
      this._actions = actions;

      if(this.dropdownRef != null && !this.dropdownRef.closed) {
         const instance: ActionsContextmenuComponent = this.dropdownRef.componentInstance;
         instance.actions = actions;
         instance.changeDetectorRef.markForCheck();
      }
   }

   get actions(): AssemblyActionGroup[] {
      return this._actions;
   }

   @Input()
   set contextmenuEnabled(value: boolean) {
      if(this._enabled && !value) {
         this.removeListener();
      }
      else if(!this._enabled && value) {
         this.addListener();
      }

      this._enabled = value;
   }

   get contextmenuEnabled(): boolean {
      return this._enabled;
   }

   constructor(private dropdownService: FixedDropdownService,
               private renderer: Renderer2,
               private element: ElementRef)
   {
   }

   ngOnInit(): void {
      if(this._enabled) {
         this.addListener();
      }
      else {
         this.removeListener();
      }
   }

   ngOnDestroy(): void {
      this.close();
      this.removeListener();

      if(this.dropdownSubscription) {
         this.dropdownSubscription.unsubscribe();
         this.dropdownSubscription = null;
      }

      if(this.childClickedSubscription) {
         this.childClickedSubscription.unsubscribe();
         this.childClickedSubscription = null;
      }
   }

   private onContextmenu(e: MouseEvent) {
      e.preventDefault();
      e.stopPropagation();

      let options: DropdownOptions = {
         position: {x: e.clientX, y: e.clientY},
         contextmenu: true,
         closeOnWindowResize: true,
      };

      this.position.x = options.position.x;
      this.position.y = options.position.y;
      this.dropdownRef = this.dropdownService.open(ActionsContextmenuComponent, options);
      let instance: ActionsContextmenuComponent = this.dropdownRef.componentInstance;
      instance.sourceEvent = e;
      instance.actions = this.actions;
      this.dropdownSubscription.add(this.dropdownRef.closeEvent.subscribe(
         evt => this.onContextmenuClosed.emit(evt)));
      // event on clicking menu item
      this.dropdownSubscription.add(this.dropdownRef.componentInstance.onClose.subscribe(
         evt => this.onContextmenuClosed.emit(evt)));
      this.childClickedSubscription = instance.onClose.subscribe(
         (event) => this.close());
      this.onContextmenuOpened.emit(true);
   }

   close() {
      if(!!this.dropdownRef) {
         this.dropdownRef.close();
         this.dropdownRef = null;
      }
   }

   private addListener(): void {
      if(!this.listener && this.element.nativeElement) {
         this.listener = this.renderer.listen(
            this.element.nativeElement, "contextmenu", (event) => this.onContextmenu(event));
      }
   }

   private removeListener(): void {
      if(this.listener) {
         this.listener();
         this.listener = null;
      }
   }
}
