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

package club.javafamily.runner.web.widget.echarts.service.visualmap;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.info.VisualMapSupport;
import club.javafamily.runner.web.widget.echarts.model.EChartVisualMap;
import club.javafamily.runner.web.widget.echarts.info.BindingInfo;
import club.javafamily.runner.web.widget.echarts.info.cpie.CustomPieBindingInfo;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultVisualMapFactory extends ChartVisualMapFactory {

   @Override
   public EChartVisualMap build(TableLens lens,
                                BindingInfo bindingInfo,
                                ChartHelper chartHelper,
                                ChartType type,
                                Map<String, Object> params)
   {
      if(!(bindingInfo instanceof VisualMapSupport)) {
         return null;
      }

      EChartVisualMap visualMap = new EChartVisualMap();
      VisualMapSupport info = (VisualMapSupport) bindingInfo;

      visualMap.setMax(info.getMax());
      visualMap.setMin(info.getMin());
      visualMap.setShow(info.isShowVisualMap());
      visualMap.setInRange(info.getInRange());

      return visualMap;
   }

}
