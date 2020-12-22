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

package club.javafamily.echarts.factory.series;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.chart.ChartHelper;
import club.javafamily.echarts.info.AxisInfo;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.model.EChartSeries;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Col 0 is x, col 1 is y
 */
@Component
public class SimpleDataSeriesFactory extends ChartSeriesFactory {
   @Override
   public List<EChartSeries> build(TableLens lens, ObjectInfo bindingInfo,
                                   ChartHelper chartHelper, ChartType type,
                                   Map<String, Object> params)
   {
      List<AxisInfo> yAxis = bindingInfo.getYAxis();

      if(yAxis == null) {
         return null;
      }

      return yAxis.stream()
         .map(axis -> {
            EChartSeries series = new EChartSeries(type);

            if(lens.isEmpty()) {
               return series;
            }

            int colIndex = axis.getBindingColIndex(lens);

            series.setName(lens.getObject(0, colIndex).getValue().toString());

            series.setData(lens.getColData(colIndex));

            return series;
         })
         .collect(Collectors.toList());
   }

}
