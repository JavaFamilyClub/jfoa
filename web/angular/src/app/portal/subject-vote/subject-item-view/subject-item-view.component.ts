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
import { SubjectRequest } from "../../../domain/subject-request";
import { ModelService } from "../../../widget/services/model.service";

@Component({
   selector: "subject-item-view",
   templateUrl: "subject-item-view.component.html",
   styleUrls: ["subject-item-view.component.scss"]
})
export class SubjectItemViewComponent implements OnInit {
   @Input() model: SubjectRequest;

   constructor(private modelService: ModelService) {
   }

   ngOnInit(): void {
   }

}
