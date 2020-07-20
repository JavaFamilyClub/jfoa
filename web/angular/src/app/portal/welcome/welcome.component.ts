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

import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";

let echarts = require("echarts");

@Component({
   selector: "portal-welcome",
   templateUrl: "welcome.component.html",
   styleUrls: ["welcome.component.scss"]
})
export class WelcomeComponent implements OnInit {
   @ViewChild("chartRegion", {static: true}) chartRegion: ElementRef;

   ngOnInit(): void {
      const mockDataOption = {
         title: { text: "ECharts Example" },
         tooltip: {},
         xAxis: {
            data: ["Xi'An", "Bei'Jing", "Shang'Hai", "Nan'Jing", "Shang'Luo", "Bao'Ji"]
         },
         yAxis: {},
         series: [{
            name: "City",
            type: "bar",
            data: [5, 20, 36, 10, 10, 20]
         }]
      };

      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(this.chartRegion.nativeElement);

      myChart.setOption(mockDataOption);
   }

}
