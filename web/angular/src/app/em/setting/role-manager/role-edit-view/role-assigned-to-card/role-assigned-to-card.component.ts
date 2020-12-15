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

import { Component, Input, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { MatColumnIno } from "../../../../../widget/mat-table-view/mat-column-ino";
import { RoleAssignedToModel } from "./role-assigned-to-model";

@Component({
   selector: "role-assigned-to-card",
   templateUrl: "./role-assigned-to-card.component.html",
   styleUrls: ["./role-assigned-to-card.component.scss"]
})
export class RoleAssignedToCardComponent implements OnInit {
   @Input() model: RoleAssignedToModel;

   constructor(private translate: TranslateService)
   {
   }

   ngOnInit(): void {
   }

   get cols(): MatColumnIno[] {
      return [
         {
            headerCheckbox: true,
            cellCheckbox: true,
            name: "headerCheckbox",
            headerCheckboxHandle: (value: boolean) => {
               if(value) {
                  // this.selectedItems = this.permission.items;
               }
               else {
                  // this.resetSelectedItems();
               }
            }
            // headerCheckboxChecked: () => {
            //    return !Tool.isEmpty(this.selectedItems)
            //       && this.selectedItems === this.permission.items;
            // }
         },
         {
            label: this.translate.instant("Type"),
            name: "type"
         },
         {
            label: this.translate.instant("Name"),
            name: "name"
         }
      ];
   }

}
