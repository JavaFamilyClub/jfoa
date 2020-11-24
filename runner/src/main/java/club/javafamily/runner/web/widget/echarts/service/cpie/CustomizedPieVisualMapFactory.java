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

package club.javafamily.runner.web.widget.echarts.service.cpie;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.EChartVisualMap;
import club.javafamily.runner.web.widget.echarts.info.BindingInfo;
import club.javafamily.runner.web.widget.echarts.info.binding.CustomPieBindingInfo;
import club.javafamily.runner.web.widget.echarts.service.ChartObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomizedPieVisualMapFactory implements ChartObjectFactory<EChartVisualMap> {

   @Autowired
   public CustomizedPieVisualMapFactory(CustomizedPieChartHelper chartHelper) {
      this.chartHelper = chartHelper;
   }

   @Override
   public boolean isAccept(ChartType type, BindingInfo bindingInfo) {
      return chartHelper.isAccept(type, bindingInfo);
   }

   @Override
   public EChartVisualMap build(TableLens lens,
                                BindingInfo bindingInfo,
                                ChartType type,
                                Map<String, Object> params)
   {
      EChartVisualMap visualMap = new EChartVisualMap();
      CustomPieBindingInfo info = (CustomPieBindingInfo) bindingInfo;

      visualMap.setMax(info.getMax());
      visualMap.setMin(info.getMin());
      visualMap.setShow(info.isShowVisualMap());
      visualMap.setInRange(chartHelper.defaultCustomizedPieVisualMapInRange());

      return visualMap;
   }

   private final CustomizedPieChartHelper chartHelper;
}
