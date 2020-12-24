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

import { Component, ElementRef, Input, OnInit } from "@angular/core";
import { Tool } from "../../common/util/tool";
import { ModelService } from "../services/model.service";
import { EChartModel } from "./model/echart-model";

@Component({
   selector: "echarts-chart",
   templateUrl: "./echarts-chart.component.html",
   styleUrls: ["./echarts-chart.component.scss"]
})
export class EchartsChartComponent implements OnInit {
   _chartModel: EChartModel;
   @Input() url: string;
   @Input() autoAdaptSize = true;
   @Input() hPadding = 20;
   @Input() vPadding = 10;

   constructor(private modelService: ModelService,
               private readonly hostRef: ElementRef)
   {
   }

   @Input() set chartModel(chartModel: EChartModel) {
      this.fixChartModel(chartModel);
      this._chartModel = chartModel;
   }

   get chartModel(): EChartModel {
      return this._chartModel;
   }

   /**
    * 1. auto apply bounds
    * 2. trim null properties
    */
   private fixChartModel(chartModel: EChartModel): void {
      this.fixChartSize(chartModel);
      Tool.trimObjectByNull(chartModel);
   }

   private fixChartSize(chartModel: EChartModel): void {
      if(!this.autoAdaptSize || !!!chartModel || !!chartModel.initOpts?.height
         || !!!this.hostRef?.nativeElement)
      {
         return;
      }

      // const clientHeight = this.hostRef.nativeElement.clientHeight - this.vPadding;
      // const clientWidth = this.hostRef.nativeElement.clientWidth - this.hPadding;

      const bounds = this.hostRef.nativeElement.getBoundingClientRect();

      if(!!!bounds) {
         return;
      }

      const clientHeight = bounds.height - this.vPadding;
      const clientWidth = bounds.width - this.hPadding;

      chartModel.initOpts.height = clientHeight;
      chartModel.initOpts.width = clientWidth;
   }

   ngOnInit(): void {
      this.refresh();
   }

   refresh(): void {
      if(!this.url) {
         return;
      }

      this.modelService.getModel<EChartModel>(this.url).subscribe(model => {
         this.fixChartModel(model);
         this.chartModel = model;
      });
   }

   get loading(): boolean {
      return !!!this.chartModel;
   }
}
