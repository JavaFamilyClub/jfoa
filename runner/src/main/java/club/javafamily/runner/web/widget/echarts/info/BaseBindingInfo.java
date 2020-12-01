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

package club.javafamily.runner.web.widget.echarts.info;

import club.javafamily.runner.web.widget.echarts.model.ChartFormatInfo;

import java.util.List;

public abstract class BaseBindingInfo implements ObjectInfo {

   protected BaseBindingInfo() {
   }

   @Override
   public List<AxisInfo> getXAxis() {
      return xAxisInfo;
   }

   @Override
   public List<AxisInfo> getYAxis() {
      return yAxisInfo;
   }

   public void setxAxisInfo(List<AxisInfo> xAxisInfo) {
      this.xAxisInfo = xAxisInfo;
   }

   public void setyAxisInfo(List<AxisInfo> yAxisInfo) {
      this.yAxisInfo = yAxisInfo;
   }

   @Override
   public LegendInfo getLegend() {
      return legend;
   }

   public void setLegend(LegendInfo legend) {
      this.legend = legend;
   }

   @Override
   public TooltipInfo getTooltip() {
      return tooltip;
   }

   public void setTooltip(TooltipInfo tooltip) {
      this.tooltip = tooltip;
   }

   @Override
   public TitleInfo getTitleInfo() {
      return titleInfo;
   }

   public void setTitleInfo(TitleInfo titleInfo) {
      this.titleInfo = titleInfo;
   }

   @Override
   public ChartFormatInfo getFormat() {
      return format;
   }

   public void setFormat(ChartFormatInfo format) {
      this.format = format;
   }

   private List<AxisInfo> xAxisInfo;
   private List<AxisInfo> yAxisInfo;
   protected LegendInfo legend;
   protected TooltipInfo tooltip;
   protected TitleInfo titleInfo;
   protected ChartFormatInfo format;
}
