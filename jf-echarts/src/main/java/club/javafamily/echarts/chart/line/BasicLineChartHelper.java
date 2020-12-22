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

package club.javafamily.echarts.chart.line;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.echarts.chart.ChartHelper;
import club.javafamily.echarts.info.*;
import club.javafamily.echarts.info.line.BasicLineAxisInfo;
import club.javafamily.echarts.info.line.BasicLineBindingInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BasicLineChartHelper implements ChartHelper {
   @Override
   public boolean isAccept(ChartType type, ObjectInfo bindingInfo) {
      return type == ChartType.line && bindingInfo != null;
   }

   @Override
   public ObjectInfo buildDefaultBindingInfo(String title) {
      BasicLineBindingInfo info = new BasicLineBindingInfo();

      info.setTooltip(new TooltipInfo(TooltipInfo.AXIS_TRIGGER));
      info.setTitleInfo(new TitleInfo(title));
      DefaultAxisInfo xAxisInfo = new DefaultAxisInfo(AxisInfo.DEFAULT_X_AXIS_TYPE);
      xAxisInfo.setAxisBindingEnabled(true);
      xAxisInfo.setBindingColIndex(0);
      info.setxAxisInfo(Collections.singletonList(xAxisInfo));

      BasicLineAxisInfo yAxisInfo = new BasicLineAxisInfo(AxisInfo.DEFAULT_Y_AXIS_TYPE,
         new Object[]{0, "100%"});
      yAxisInfo.setBindingColIndex(1);
      info.setyAxisInfo(Collections.singletonList(yAxisInfo));

      return info;
   }
}
