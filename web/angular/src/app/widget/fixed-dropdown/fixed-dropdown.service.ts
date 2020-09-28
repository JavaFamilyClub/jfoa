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

import { DOCUMENT } from "@angular/common";
import {
   ApplicationRef,
   ComponentFactory,
   ComponentFactoryResolver,
   ComponentRef,
   Inject,
   Injectable,
   Injector,
   TemplateRef,
   Type,
   ViewRef
} from "@angular/core";
import { Rectangle } from "../../common/data/rectangle";
import { DropdownOptions } from "./dropdown-options";
import { DropdownStackService } from "./dropdown-stack.service";
import { FixedDropdownContextmenuComponent } from "./fixed-dropdown-contextmenu.component";
import { DropdownRef } from "./fixed-dropdown-ref";
import { FixedDropdownComponent } from "./fixed-dropdown.component";

export class ContentRef {
   constructor(public nodes: any[],
               public viewRef?: ViewRef,
               public componentRef?: ComponentRef<any>)
   {
   }
}

/**
 * Class which handles the instantiation of dropdowns.
 * Inspired by https://github.com/ng-bootstrap/ng-bootstrap/blob/master/src/modal/modal-stack.ts
 */
@Injectable()
export class FixedDropdownService {
   private dropdownFactory: ComponentFactory<FixedDropdownComponent>;
   private dropdownContextmenuFactory: ComponentFactory<FixedDropdownContextmenuComponent>;

   constructor(private applicationRef: ApplicationRef,
               private injector: Injector,
               private componentFactoryResolver: ComponentFactoryResolver,
               @Inject(DOCUMENT) private document: Document,
               private stackService: DropdownStackService)
   {
      this.dropdownFactory = componentFactoryResolver
         .resolveComponentFactory(FixedDropdownComponent);
      this.dropdownContextmenuFactory = componentFactoryResolver
         .resolveComponentFactory(FixedDropdownContextmenuComponent);
   }

   open(content: any, options: DropdownOptions): DropdownRef {
      return this._open(this.componentFactoryResolver, this.injector, content, options);
   }

   private _open(moduleCFR: ComponentFactoryResolver, contentInjector: Injector, content: any,
                 options: DropdownOptions): DropdownRef
   {
      const containerEl = options.container || this.document.body;

      if(!containerEl) {
         throw new Error(
            `The specified dropdown container "${containerEl}" was not found in the DOM.`);
      }

      const contentRef = this.getContentRef(moduleCFR, contentInjector, content);
      const dropdownCmptRef = this.getDropdownRef(options, contentRef);
      this.applicationRef.attachView(dropdownCmptRef.hostView);
      containerEl.appendChild(dropdownCmptRef.location.nativeElement);
      dropdownCmptRef.instance.container = containerEl;
      this.applyOptions(dropdownCmptRef.instance, options);
      return new DropdownRef(dropdownCmptRef, this.stackService, contentRef);
   }

   private getDropdownRef(options: DropdownOptions,
                          contentRef: ContentRef): ComponentRef<FixedDropdownComponent>
   {
      return options.contextmenu ?
         this.dropdownContextmenuFactory.create(this.injector, contentRef.nodes) :
         this.dropdownFactory.create(this.injector, contentRef.nodes);
   }

   private getContentRef(moduleCFR: ComponentFactoryResolver, contentInjector: Injector,
                         content: TemplateRef<any> | Type<any>): ContentRef
   {
      if(content instanceof TemplateRef) {
         const viewRef = content.createEmbeddedView(null);
         this.applicationRef.attachView(viewRef);
         return new ContentRef([viewRef.rootNodes], viewRef);
      }
      else {
         const contentCmptFactory = moduleCFR.resolveComponentFactory(content);
         const componentRef = contentCmptFactory.create(contentInjector);
         this.applicationRef.attachView(componentRef.hostView);
         return new ContentRef([[componentRef.location.nativeElement]], componentRef.hostView,
                               componentRef);
      }
   }

   private applyOptions(dropdown: FixedDropdownComponent, options: DropdownOptions) {
      dropdown.dropdownBounds = new Rectangle(options.position.x, options.position.y, 0, 0);

      if(options.autoClose != null) {
         dropdown.autoClose = options.autoClose;
      }

      if(options.closeOnOutsideClick != null) {
         dropdown.closeOnOutsideClick = options.closeOnOutsideClick;
      }

      if(options.closeOnWindowResize != null) {
         dropdown.closeOnWindowResize = options.closeOnWindowResize;
      }

      if(options.zIndex != null) {
         dropdown.zIndex = options.zIndex;
      }
   }
}
