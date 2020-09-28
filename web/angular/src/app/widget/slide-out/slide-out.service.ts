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

import {
   Injectable, ComponentFactoryResolver, Injector, ApplicationRef, ComponentFactory,
   ComponentRef, TemplateRef, EmbeddedViewRef, ViewRef
} from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { isString } from "util";
import { UIContextService } from "../../common/services/ui-context.service";
import { GuiTool } from "../../common/util/gui-tool";
import { SlideOutBackdropComponent } from "./slide-out-backdrop.component";
import { SlideOutOptions } from "./slide-out-options";
import { SlideOutRef } from "./slide-out-ref";
import { SlideOutComponent } from "./slide-out.component";

/*
 * Copied from ng-bootstrap, no longer exported.
 */
class ContentRef {
   constructor(public nodes: any[], public viewRef?: ViewRef, public componentRef?: ComponentRef<any>) {}
}

/**
 * The NgbModal service equivalent for the slide out component.
 */
@Injectable()
export class SlideOutService {
   private slideContainer: ComponentFactory<SlideOutComponent>;

   constructor(private factoryResolver: ComponentFactoryResolver,
               private applicationRef: ApplicationRef,
               private uiContext: UIContextService)
   {
      this.slideContainer = factoryResolver.resolveComponentFactory(SlideOutComponent);
   }

   /**
    * Method equivalent to the modal open method.
    * Note: Even though it takes the same options as modals, not all options make sense
    * for slide outs and will therefore be ignored.
    */
   public open(content: any, options?: SlideOutOptions, injector?: Injector): SlideOutRef {
      let containerEl: Element = options.container instanceof Element ? options.container : null;
      let containerSelector: string = null;

      if(containerEl == null) {
         containerSelector = options.container as string || "body";
         const containerCandidates = document.querySelectorAll(containerSelector);

         // Don"t check the layout box if only one element is found.
         if(containerCandidates.length === 1) {
            containerEl = containerCandidates.item(0);
         }
         else {
            for(let i = 0; i < containerCandidates.length; i++) {
               const containerCandidate = containerCandidates.item(i);

               if(GuiTool.hasLayoutBox(containerCandidate)) {
                  containerEl = containerCandidate;
                  break;
               }
            }
         }
      }

      if(containerEl == null) {
         throw new Error(`No valid container found for selector ${containerSelector}`);
      }

      const activeModal: NgbActiveModal = new NgbActiveModal();
      const contentRef: ContentRef = this.getContentRef(options.injector || injector,
         content, activeModal);

      const slideCon: ComponentRef<SlideOutComponent> =
         this.slideContainer.create(injector, contentRef.nodes);
      this.applicationRef.attachView(slideCon.hostView);

      const backdropFactory = this.factoryResolver.resolveComponentFactory(SlideOutBackdropComponent);
      const backdropCmptRef: ComponentRef<any> =
         options.backdrop == "static" ? backdropFactory.create(injector) : null;

      if(!!backdropCmptRef) {
         this.applicationRef.attachView(backdropCmptRef.hostView);
         containerEl.appendChild(backdropCmptRef.location.nativeElement);
      }

      containerEl.appendChild(slideCon.location.nativeElement);

      setTimeout(() => {
         let title: string = options.title;

         if(!title) {
            contentRef.nodes.forEach(nodes => nodes.forEach(node => {
               if(!title && node && node.querySelector) {
                  const titleNode: Element = node.querySelector(".modal-title");

                  if(titleNode) {
                     title = titleNode.innerHTML;

                     if(options.objectId) {
                        title = options.objectId.substring(options.objectId.lastIndexOf("/")) +
                           " " + title;
                     }
                  }
               }
            }));
         }

         if(title) {
            slideCon.instance.title = title;
         }
      }, 200);

      const slideRef: SlideOutRef = new SlideOutRef(slideCon, contentRef, backdropCmptRef,
                                                    options.objectId,
                                                    this.uiContext.getCurrentSheetId());

      activeModal.close = (result: any) => { slideRef.close(result); };
      activeModal.dismiss = (reason: any) => { slideRef.dismiss(reason); };

      this.applyOptions(slideCon.instance, options);

      return slideRef;
   }

   private applyOptions(tabs: SlideOutComponent, options: Object): void {
      ["keyboard", "size", "windowClass", "side" ].forEach((optionName: string) => {
         if(options[optionName]) {
            tabs[optionName] = options[optionName];
         }
      });
   }

   private getContentRef(contentInjector: Injector, content: any, context: NgbActiveModal)
      : ContentRef
   {
      if(!content) {
         return new ContentRef([]);
      }
      else if(content instanceof TemplateRef) {
         const viewRef: EmbeddedViewRef<any> = content.createEmbeddedView(context);
         this.applicationRef.attachView(viewRef);
         return new ContentRef([viewRef.rootNodes], viewRef);
      }
      else if(isString(content)) {
         return new ContentRef([[document.createTextNode(`${content}`)]]);
      }
      else {
         const contentCmptFactory: ComponentFactory<any> =
            this.factoryResolver.resolveComponentFactory(content);
         const modalContentInjector = Injector.create(
            {providers: [{provide: NgbActiveModal, useValue: context}], parent: contentInjector});
         const componentRef: ComponentRef<any> =
            contentCmptFactory.create(modalContentInjector);
         this.applicationRef.attachView(componentRef.hostView);
         return new ContentRef([[componentRef.location.nativeElement]],
            componentRef.hostView, componentRef);
      }
   }
}
