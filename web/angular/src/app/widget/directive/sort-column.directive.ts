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
   Directive,
   ElementRef, EventEmitter,
   Input, OnChanges,
   OnInit, Output,
   Renderer2, SimpleChanges
} from "@angular/core";

@Directive({
   selector: "[sortColumn]"
})
export class SortColumnDirective implements OnInit, OnChanges {
   @Input() data: any[];
   @Input() sortKey: any;
   @Input() sortType: any;
   @Output() sortTypeChanged: EventEmitter<any> = new EventEmitter();
   private toggleSort: boolean = true;

   constructor(private el: ElementRef, private renderer: Renderer2) {
   }

   ngOnChanges(changes: SimpleChanges) {
      if(changes.hasOwnProperty("sortType") && this.sortType != this.sortKey) {
         this.hideSortIcon();
      }
      else if(changes["data"]) {
         if(this.sortType == this.sortKey) {
            this.sortArray();
         }
      }
   }

   ngOnInit(): void {
      this.renderer.listen(this.el.nativeElement, "click", () => {
         if(this.data && this.sortKey) {
            this.toggleSort = !this.toggleSort;
            this.sortArray();
         }
      });
   }

   sortArray(): Array<any> {
      let tempArray: Array<any> = this.data;
      tempArray.sort((a, b) => {
         let str1: string = a[this.sortKey];
         let str2: string = b[this.sortKey];

         str1 = str1 ? (str1 + "").toLowerCase() : "";
         str2 = str2 ? (str2 + "").toLowerCase() : "";

         if(this.toggleSort) {
            if(str1 < str2) {
               return -1;
            }

            if(str1 > str2) {
               return 1;
            }
         }
         else {
            if(str1 > str2) {
               return -1;
            }

            if(str1 < str2) {
               return 1;
            }
         }

         return this.toggleSort ?
            a[this.sortKey] > b[this.sortKey] ? 1 : -1 :
            a[this.sortKey] > b[this.sortKey] ? -1 : 1;
      });

      this.updateSortIcon();
      this.sortTypeChanged.emit(this.sortKey);
      return tempArray;
   }

   private updateSortIcon() {
      const element = this.el.nativeElement;
      let iconElement = element.querySelector(".sort-column-sort-icon");

      if(iconElement) {
         this.renderer.removeClass(iconElement,
            this.toggleSort ? "sort-descending-icon" : "sort-ascending-icon");
         this.renderer.addClass(iconElement,
            this.toggleSort ? "sort-ascending-icon" : "sort-descending-icon");
         this.renderer.setStyle(iconElement, "display", "inline-block");
      }
      else {
         iconElement = this.renderer.createElement("i");
         this.renderer.addClass(iconElement, "sort-column-sort-icon");
         this.renderer.addClass(iconElement,
            this.toggleSort ? "sort-ascending-icon" : "sort-descending-icon");
         this.renderer.setStyle(iconElement, "display", "inline-block");
         this.renderer.setStyle(iconElement, "vertical-align", "middle");
         this.renderer.appendChild(element, iconElement);
      }
   }

   private hideSortIcon() {
      const element = this.el.nativeElement;
      let iconElement = element.querySelector(".sort-column-sort-icon");

      if(iconElement) {
         this.renderer.setStyle(iconElement, "display", "none");
      }
   }
}
