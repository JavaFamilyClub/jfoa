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

package club.javafamily.echarts.chart.cpie;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.echarts.model.ChartFormatInfo;
import club.javafamily.echarts.model.EChartVisualInRange;
import club.javafamily.echarts.info.*;
import club.javafamily.echarts.info.cpie.CustomPieBindingInfo;
import club.javafamily.echarts.chart.ChartHelper;
import org.springframework.stereotype.Component;

@Component
public class CustomizedPieChartHelper implements ChartHelper {

   @Override
   public boolean isAccept(ChartType type, ObjectInfo bindingInfo) {
      return type == ChartType.pie &&
         (bindingInfo == null || bindingInfo instanceof CustomPieBindingInfo);
   }

   @Override
   public ObjectInfo buildDefaultBindingInfo(String title) {
      CustomPieBindingInfo bindingInfo = new CustomPieBindingInfo();

      bindingInfo.setShowVisualMap(false);
      bindingInfo.setMax(0);
      bindingInfo.setMin(0);
      bindingInfo.setSeriesItemStyleColor("#c23531");
      bindingInfo.setAnimationType("scale");
      bindingInfo.setAnimationEasing("animationEasing");
      bindingInfo.setAnimationDelay(100D);
      bindingInfo.setInRange(defaultCustomizedPieVisualMapInRange());
      bindingInfo.setTooltip(defaultTooltipInfo());
      bindingInfo.setTitleInfo(defaultTitleInfo(title));
      bindingInfo.setFormat(defaultChartFormat());

      return bindingInfo;
   }

   private ChartFormatInfo defaultChartFormat() {
      ChartFormatInfo formatInfo = new ChartFormatInfo();

      formatInfo.setBackgroundColor("#2c343c");

      return formatInfo;
   }

   private TitleInfo defaultTitleInfo(String title) {
      TitleInfo titleInfo = new TitleInfo();

      TitleFormat format = new TitleFormat();
      format.setLeft("center");
      format.setTop(10);
      format.setColor("#ccc");

      titleInfo.setTitle(title);
      titleInfo.setFormat(format);

      return titleInfo;
   }

   public TooltipInfo defaultTooltipInfo() {
      TooltipInfo tooltip = new TooltipInfo();

      tooltip.setTrigger("item");
      tooltip.setFormatter("{a} <br/>{b} : {c} ({d}%)");

      return tooltip;
   }

   public EChartVisualInRange defaultCustomizedPieVisualMapInRange() {
      EChartVisualInRange inRange = new EChartVisualInRange();

      inRange.setColorLightness(new Double[] {0D, 1D});

      return inRange;
   }
}
