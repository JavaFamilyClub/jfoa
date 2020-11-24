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

import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.EChartVisualInRange;
import club.javafamily.runner.web.widget.echarts.info.BindingInfo;
import club.javafamily.runner.web.widget.echarts.info.binding.CustomPieBindingInfo;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;

@Component
public class CustomizedPieChartHelper implements ChartHelper {

   @Override
   public boolean isAccept(ChartType type, BindingInfo bindingInfo) {
      return type == ChartType.pie &&
         (bindingInfo == null || bindingInfo instanceof CustomPieBindingInfo);
   }

   @Override
   public BindingInfo buildDefaultBindingInfo(String subject) {
      CustomPieBindingInfo bindingInfo = new CustomPieBindingInfo(subject);

      bindingInfo.setShowVisualMap(false);
      bindingInfo.setMax(0);
      bindingInfo.setMin(0);
      bindingInfo.setSeriesItemStyleColor("#c23531");
      bindingInfo.setAnimationType("scale");
      bindingInfo.setAnimationEasing("animationEasing");
      bindingInfo.setAnimationDelay(100D);

      return bindingInfo;
   }

   public EChartVisualInRange defaultCustomizedPieVisualMapInRange() {
      EChartVisualInRange inRange = new EChartVisualInRange();

      inRange.setColorLightness(new Double[] {0D, 1D});

      return inRange;
   }
}
