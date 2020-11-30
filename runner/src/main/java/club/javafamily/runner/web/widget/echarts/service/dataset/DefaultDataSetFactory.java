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

package club.javafamily.runner.web.widget.echarts.service.dataset;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.model.EChartDataSet;
import club.javafamily.runner.web.widget.echarts.info.*;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DefaultDataSetFactory extends ChartDataSetFactory {

   @Override
   public EChartDataSet build(TableLens lens, BindingInfo bindingInfo,
                              ChartHelper chartHelper,
                              ChartType type, Map<String, Object> params)
   {
      List<List<Object>> source = new ArrayList<>();
      EChartDataSet dataSet = new EChartDataSet(source);

      // TODO build dataSet
      LegendInfo legend = bindingInfo.getLegend();
      List<AxisInfo> xAxis = bindingInfo.getXAxis();

      return dataSet;
   }

}
