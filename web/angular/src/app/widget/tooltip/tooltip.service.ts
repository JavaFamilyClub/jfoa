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

import { DOCUMENT } from "@angular/common";
import {
   ApplicationRef,
   ComponentFactory,
   ComponentFactoryResolver,
   ComponentRef,
   Inject,
   Injectable,
   Injector
} from "@angular/core";
import { TooltipComponent } from "./tooltip.component";

@Injectable()
export class TooltipService {
   private tooltipFactory: ComponentFactory<TooltipComponent>;
   private currentTooltip: ComponentRef<TooltipComponent>;

   constructor(private applicationRef: ApplicationRef,
               private injector: Injector,
               private componentFactoryResolver: ComponentFactoryResolver,
               @Inject(DOCUMENT) private document: any)
   {
      this.tooltipFactory = componentFactoryResolver.resolveComponentFactory(TooltipComponent);
   }

   createTooltip(): ComponentRef<TooltipComponent> {
      if(this.currentTooltip) {
         this.currentTooltip.destroy();
         this.currentTooltip = null;
      }

      const tooltipRef = this.tooltipFactory.create(this.injector);
      this.applicationRef.attachView(tooltipRef.hostView);
      this.document.body.appendChild(tooltipRef.location.nativeElement);
      return this.currentTooltip = tooltipRef;
   }

   removeTooltip(tooltipRef: ComponentRef<TooltipComponent>) {
      const tooltipElement = tooltipRef.location.nativeElement as HTMLElement;

      if(!!tooltipElement.parentNode) {
         tooltipElement.parentNode.removeChild(tooltipElement);
      }

      tooltipRef.destroy();
   }
}