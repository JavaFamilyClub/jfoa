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

package club.javafamily.runner.web.widget.echarts.service.axis;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.model.EChartAxis;
import club.javafamily.runner.web.widget.echarts.info.AxisInfo;
import club.javafamily.runner.web.widget.echarts.info.ObjectInfo;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * xAxis: {type: 'category'},
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
      List<AxisInfo> list;
      boolean isY = Boolean.TRUE.equals(params.get("isY"));

      if(isY) {
         list = bindingInfo.getYAxis();
      }
      else {
         list = bindingInfo.getXAxis();
      }

      if(list == null) {
         return null;
      }

      return list.stream()
         .map(axis -> new EChartAxis(axis.getType()))
         .collect(Collectors.toList());
   }

}
