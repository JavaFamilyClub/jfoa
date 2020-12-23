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

package club.javafamily.runner.web.widget.echarts;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.commons.utils.Tool;
import club.javafamily.echarts.ChartModelBuilderService;
import club.javafamily.echarts.chart.line.BasicLineChartHelper;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.model.EChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EChartsService {

   public EChartModel buildLineChart(TableLens lens) {
      String title = Tool.toString(lens.getObject(0, 1).getValue());
      ObjectInfo bindingInfo = lineChartHelper.buildDefaultBindingInfo(title);

      return chartModelBuilder.build(ChartType.line, lens,
         bindingInfo, lineChartHelper);
   }

   @Autowired
   public EChartsService(ChartModelBuilderService chartModelBuilder,
                         BasicLineChartHelper lineChartHelper)
   {
      this.chartModelBuilder = chartModelBuilder;
      this.lineChartHelper = lineChartHelper;
   }

   private final ChartModelBuilderService chartModelBuilder;
   private final BasicLineChartHelper lineChartHelper;
}
