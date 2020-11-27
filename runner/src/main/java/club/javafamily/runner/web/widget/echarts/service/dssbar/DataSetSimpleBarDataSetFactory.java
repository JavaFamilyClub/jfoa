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

package club.javafamily.runner.web.widget.echarts.service.dssbar;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.EChartDataSet;
import club.javafamily.runner.web.widget.echarts.info.*;
import club.javafamily.runner.web.widget.echarts.service.BaseChartDataSetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataSetSimpleBarDataSetFactory extends BaseChartDataSetFactory {

   @Autowired
   public DataSetSimpleBarDataSetFactory(DataSetSimpleBarChartHelper chartHelper) {
      super(chartHelper);
      this.chartHelper = chartHelper;
   }

   @Override
   public boolean isAccept(ChartType type, BindingInfo bindingInfo) {
      return chartHelper.isAccept(type, bindingInfo);
   }

   @Override
   public EChartDataSet build(TableLens lens, BindingInfo bindingInfo,
                              ChartType type, Map<String, Object> params)
   {
      List<List<Object>> source = new ArrayList<>();
      EChartDataSet dataSet = new EChartDataSet(source);

      // TODO build dataSet
      LegendInfo legend = bindingInfo.getLegend();
      List<AxisInfo> xAxis = bindingInfo.getXAxis();

      return dataSet;
   }

   private final DataSetSimpleBarChartHelper chartHelper;
}
