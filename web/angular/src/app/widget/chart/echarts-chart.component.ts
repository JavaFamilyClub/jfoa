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

import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { Size } from "../../common/data/size";
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
   @Input() autoSize = true;
   @Input() hPadding = 20;
   @Input() vPadding = 10;

   _loading = true;
   eChartsInstance: any;

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

   chartInit(eChartsInstance: any): void {
      this.eChartsInstance = eChartsInstance;
   }

   /**
    * 1. auto apply bounds
    * 2. trim null properties
    */
   private fixChartModel(chartModel: EChartModel): void {
      this.fixChartSize(chartModel);
      Tool.trimObjectByNull(chartModel);
   }

   fixChartSize(chartModel: EChartModel): void {
      if(!this.autoAdaptSize || !!!chartModel || !!chartModel.initOpts?.height
         || !!!this.hostRef?.nativeElement)
      {
         return;
      }

      this.doResizeChart(chartModel);
   }

   getBox(): Size {
      const bounds = this.hostRef.nativeElement.getBoundingClientRect();

      if(!!!bounds) {
         return;
      }

      const height = bounds.height - this.vPadding;
      const width = bounds.width - this.hPadding;

      return {
         width,
         height
      };
   }

   private doResizeChart(chartModel: EChartModel) {
      const size = this.getBox();

      if(!!!size || !!!chartModel) {
         return;
      }

      const clientHeight = size.height - this.vPadding;
      const clientWidth = size.width - this.hPadding;

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
      if(!!!this.chartModel) {
         return true;
      }

      return this._loading;
   }

   autoResize(): void {
      if(!this.autoSize) {
         return;
      }

      const size = this.getBox();

      if(!!this.eChartsInstance && !!size) {
         this.eChartsInstance.resize({
            width: size.width,
            height: size.height
         });
      }
   }

   chartLoaded(): void {
      this._loading = false;
   }
}
