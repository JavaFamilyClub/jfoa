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

package club.javafamily.echarts.chart.dssbar;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.echarts.info.*;
import club.javafamily.echarts.info.dssbar.DataSetSimpleBarBindingInfo;
import club.javafamily.echarts.info.dssbar.DataSetSimpleBarAxisInfo;
import club.javafamily.echarts.chart.ChartHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

@Component
public class DataSetSimpleBarChartHelper implements ChartHelper {
   @Override
   public boolean isAccept(ChartType type, ObjectInfo bindingInfo) {
      if(type != ChartType.bar) {
         return false;
      }

      if(bindingInfo == null) {
         return true;
      }

      return bindingInfo instanceof DataSetSimpleBarBindingInfo
         && !CollectionUtils.isEmpty(bindingInfo.dataSet());
   }

   @Override
   public ObjectInfo buildDefaultBindingInfo(String title) {
      DataSetSimpleBarBindingInfo bindingInfo = new DataSetSimpleBarBindingInfo();

      bindingInfo.setxAxisInfo(
         Collections.singletonList(
            new DataSetSimpleBarAxisInfo(AxisInfo.DEFAULT_X_AXIS_TYPE)));
      bindingInfo.setyAxisInfo(
         Collections.singletonList(
            new DataSetSimpleBarAxisInfo(AxisInfo.DEFAULT_Y_AXIS_TYPE)));

      bindingInfo.setLegend(defaultLegendInfo());

      return bindingInfo;
   }

   public LegendInfo defaultLegendInfo() {
      LegendInfo legendInfo = new LegendInfo();
      legendInfo.setSeriesLayoutBy(LegendInfo.SERIES_LAYOUT_BY_ROW);

      return legendInfo;
   }
}
