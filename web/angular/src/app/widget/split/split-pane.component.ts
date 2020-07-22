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

declare var require: any;
import {
   Component,
   ElementRef,
   EventEmitter,
   Input,
   OnInit,
   Output,
   ViewEncapsulation
} from "@angular/core";

let Split = require("split.js/dist/split.js");

@Component({
   selector: "split-pane",
   templateUrl: "./split-pane.component.html",
   styleUrls: ["./split-pane.component.css"],
   encapsulation: ViewEncapsulation.None
})

export class SplitPaneComponent implements OnInit {
   @Input() public sizes: number[];
   @Input() public minSize = 100;
   @Input() public gutterSize = 6;
   @Input() public snapOffset = 30;
   @Input() public direction = "horizontal";
   @Input() public cursor = "grabbing";
   @Output() public onDrag: EventEmitter<any> = new EventEmitter<any>();
   @Output() public onDragStart: EventEmitter<any> = new EventEmitter<any>();
   @Output() public onDragEnd: EventEmitter<any> = new EventEmitter<any>();
   splitInstance: any;

   constructor(private element: ElementRef) {
   }

   public ngOnInit(): void {
      const children: Element[] = [];
      let parent: Element = this.element.nativeElement;

      if (parent.querySelector(".split-pane-container")) {
         parent = <Element> parent.querySelector(".split-pane-container");

         if (this.direction === "vertical") {
            parent.classList.add("vertical");
         } else {
            parent.classList.add("horizontal");
         }
      }

      for (let i = 0; i < parent.childNodes.length; i++) {
         const childNode: Node = parent.childNodes.item(i);

         if(childNode.nodeType === 1) { // element
            const childElement: Element = <Element> childNode;
            childElement.classList.add("split");
            children.push(childElement);
         }
      }

      if (!this.sizes) {
         const size: number = Math.floor(100 / children.length);
         this.sizes = [];

         for(let i = 0; i < children.length; i++) {
            this.sizes.push(size);
         }
      }

      const config = {
         sizes: this.sizes,
         minSize: this.minSize,
         gutterSize: this.gutterSize,
         snapOffset: this.snapOffset,
         direction: this.direction,
         cursor: this.cursor,
         onDrag: (event: any) => {
            this.onDrag.emit(event);
         },
         onDragStart: (event: any) => {
            this.onDragStart.emit(event);
         },
         onDragEnd: (event: any) => {
            this.onDragEnd.emit(event);
         }
      };

      this.splitInstance = Split(children, config);
   }

   public setSizes(newSizes: number[]): void {
      this.splitInstance.setSizes(newSizes);
   }

   public getSizes(): number[] {
      return this.splitInstance.getSizes();
   }

   public collapse(index: number): void {
      return this.splitInstance.collapse(index);
   }
}
