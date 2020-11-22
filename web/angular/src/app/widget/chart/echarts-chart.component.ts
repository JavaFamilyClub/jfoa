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
import { EChartModel } from "./model/echart-model";

@Component({
  selector: "echarts-chart",
  templateUrl: "./echarts-chart.component.html",
  styleUrls: ["./echarts-chart.component.scss"]
})
export class EchartsChartComponent implements OnInit {
  @Input() chartModel: EChartModel;

  ngOnInit(): void {
  }

  get initOpts(): any {
    return {
      width: "500",
      height: "500",
      renderer: "svg"
    };
  }

  get options(): EChartOption {
    return {
      color: ['red', "green"],
      legend: {},
      tooltip: {},
      dataset: {
        source: [
          ['product', '2012', '2013', '2014', '2015'],
          ['Support', 41, 30, 65, 53],
          ['Like', 86, 92, 85, 83]
        ]
      },
      xAxis: [
        {
          type: 'category',
          axisTick: null,
          data: null
        }
      ],
      yAxis: [
        {
          type: 'value',
          axisTick: null,
          data: null
        }
      ],
      series: [
        // 这几个系列会在第一个直角坐标系中，每个系列对应到 dataset 的每一行。
        {
          type: 'bar',
          seriesLayoutBy: 'row',
          name: null,
          data: null
        },
        {
          type: 'bar',
          seriesLayoutBy: 'row',
          name: null,
          data: null
        }
      ]
    };
  }

}
