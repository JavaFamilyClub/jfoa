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

package club.javafamily.echarts.factory.axis;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.commons.utils.Tool;
import club.javafamily.echarts.model.EChartAxis;
import club.javafamily.echarts.info.AxisInfo;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.chart.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * xAxis: {type: 'category', [data: [...]]},
 * yAxis: {type: 'value'}
 */
@Component
public class DefaultAxisFactory extends ChartAxisFactory {

   @Override
   public List<EChartAxis> build(TableLens lens,
                                 ObjectInfo bindingInfo,
                                 ChartHelper chartHelper,
                                 ChartType type,
                                 Map<String, Object> params)
   {
      List<AxisInfo> list = getAxisInfos(bindingInfo, params);

      if(list == null) {
         return null;
      }

      return list.stream()
         .map(axis -> {
            EChartAxis chartAxis = new EChartAxis(axis.getType());

            if(!axis.isAxisBindingEnabled()) {
               return chartAxis;
            }

            int colIndex = axis.getBindingColIndex(lens);

            chartAxis.setData(lens.getColData(colIndex));

            return chartAxis;
         })
         .collect(Collectors.toList());
   }

   protected List<AxisInfo> getAxisInfos(ObjectInfo bindingInfo, Map<String, Object> params) {
      List<AxisInfo> list;
      boolean isY = Boolean.TRUE.equals(params.get("isY"));

      if(isY) {
         list = bindingInfo.getYAxis();
      }
      else {
         list = bindingInfo.getXAxis();
      }

      return list;
   }

}
