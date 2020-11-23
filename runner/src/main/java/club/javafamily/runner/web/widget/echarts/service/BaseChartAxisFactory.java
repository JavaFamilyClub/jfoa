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

package club.javafamily.runner.web.widget.echarts.service;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.EChartAxis;
import club.javafamily.runner.web.widget.echarts.info.AxisInfo;
import club.javafamily.runner.web.widget.echarts.info.BindingInfo;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseChartAxisFactory implements ChartAxisFactory {

   @Override
   public List<EChartAxis> build(TableLens lens,
                                 BindingInfo bindingInfo,
                                 ChartType type,
                                 boolean isX)
   {
      List<AxisInfo> list;

      if(isX) {
         list = bindingInfo.getXAxis();
      }
      else {
         list = bindingInfo.getYAxis();
      }

      if(list == null) {
         return null;
      }

      return list.stream()
         .map(axis -> new EChartAxis(axis.getType()))
         .collect(Collectors.toList());
   }
}
