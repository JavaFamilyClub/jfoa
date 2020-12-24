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
import { EChartOption } from "echarts";
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

  constructor(private modelService: ModelService) {
  }

  @Input() set chartModel(chartModel: EChartModel) {
     Tool.trimObjectByNull(chartModel?.options);
     this._chartModel = chartModel;
  }

  get chartModel(): EChartModel {
     return this._chartModel;
  }

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    if(!this.url) {
      return;
    }

    this.modelService.getModel<EChartModel>(this.url).subscribe(model => {
       Tool.trimObjectByNull(model?.options);
      this.chartModel = model;
    });
  }

  get loading(): boolean {
     return !!!this.chartModel;
  }
}
