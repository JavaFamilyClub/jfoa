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
   ChangeDetectionStrategy,
   ChangeDetectorRef,
   Component,
   ElementRef,
   EventEmitter,
   forwardRef,
   Inject,
   Input,
   OnDestroy,
   Optional,
   Output
} from "@angular/core";
import { Subscription } from "rxjs";
import { DropdownOptions } from "./dropdown-options";
import { DropdownRef } from "./fixed-dropdown-ref";
import { FixedDropdownService } from "./fixed-dropdown.service";
import {AssemblyActionGroup} from "../../common/action/assembly-action-group";

@Component({
   selector: "actions-contextmenu",
   templateUrl: "actions-contextmenu.component.html",
   styleUrls: ["actions-contextmenu.component.scss"],
   changeDetection: ChangeDetectionStrategy.OnPush
})
export class ActionsContextmenuComponent implements OnDestroy {
   @Input() sourceEvent: MouseEvent | TouchEvent;
   @Input() forceTab: boolean = false;
   @Input() set focused(value: any) {
      // When this component is created dynamically,
      // need to manually mark for change detection.
      this.changeDetectorRef.markForCheck();
      this.focusedGroup = value.group;
      this.focusedAction = value.action;
   }
   @Output() onClose = new EventEmitter<void>();
   visibleActions: AssemblyActionGroup[] = [];
   instance: ActionsContextmenuComponent;
   dropdownRef: DropdownRef;
   private _actions: AssemblyActionGroup[];
   private focusedGroup: number = -1;
   private focusedAction: number = -1;
   private childSubscription: Subscription;

   @Input() set actions(actions: AssemblyActionGroup[]) {
      this._actions = actions;

      if(actions != null) {
         this.visibleActions = actions.filter((group) => group && group.visible);
      }
      else {
         this.visibleActions = [];
      }
   }

   get actions(): AssemblyActionGroup[] {
      return this._actions;
   }

   constructor(public changeDetectorRef: ChangeDetectorRef,
      @Optional() @Inject(forwardRef(() => FixedDropdownService)) private dropdownService: FixedDropdownService)
   {
   }

   ngOnDestroy(): void {
      if(this.childSubscription) {
         this.childSubscription.unsubscribe();
         this.childSubscription = null;
      }
   }

   onClick(action: any) {
     action.action(this.sourceEvent);
     this.onClose.emit();
   }

   /**
    * Check if the action should be focused on.
    */
   isFocused(group: number, action: number): boolean {
      return group == this.focusedGroup && action == this.focusedAction;
   }

   openChild(e: MouseEvent, action: any) {
      e.preventDefault();
      e.stopPropagation();
      let hasChild = action.childAction;

      if(this.dropdownRef) {
         this.closeDescendants(<ActionsContextmenuComponent>this.instance);
      }

      if(hasChild) {
         let elem: ElementRef = new ElementRef( e.target);
         let top = this.getElementAbsoluteTop(elem);
         let left = this.getElementAbsoluteLeft(elem);

         let options: DropdownOptions = {
            position: {x: left, y: top},
            contextmenu: true,
            closeOnWindowResize: true,
         };

         if(!this.dropdownRef && !this.instance) {
            this.dropdownRef  = this.dropdownService.open(ActionsContextmenuComponent, options);
            this.instance = this.dropdownRef.componentInstance;
            this.instance.sourceEvent = e;
            this.instance.actions = action.childAction();
            this.childSubscription = this.instance.onClose.subscribe(
              (event) => this.closeAll());
        }
      }
   }

   closeSelf() {
      this.onClose.emit();
   }

   getElementAbsoluteTop(elem: ElementRef) {
      let nativeElement = elem.nativeElement;
      let nativeElementC = nativeElement;
      let top = 0;

      while(nativeElementC) {
         top += nativeElementC.offsetTop;
         nativeElementC = nativeElementC.offsetParent;
      }

      return top;
   }

   getElementAbsoluteLeft(elem: ElementRef) {
      let nativeElement = elem.nativeElement;
      let nativeElementC = nativeElement;
      let width = nativeElement.offsetWidth;
      let left = 0;

      while(nativeElementC) {
        left += nativeElementC.offsetLeft;
        nativeElementC = nativeElementC.offsetParent;
      }

      return left += width;
   }

   public closeChild() {
      if(this.dropdownRef) {
         this.dropdownRef.close();
         this.dropdownRef = null;
         this.instance = null;
      }
   }

   closeAll(){
      this.closeChild();
      this.onClose.emit();
   }

   closeDescendants(context: ActionsContextmenuComponent) {
      while(context) {
        context.closeChild();
        context = context.instance;
      }

      this.closeChild();
   }
}
