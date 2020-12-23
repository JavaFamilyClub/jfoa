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
import club.javafamily.commons.lens.TableLens;
import club.javafamily.commons.utils.Tool;
import club.javafamily.echarts.chart.ChartHelper;
import club.javafamily.echarts.chart.ChartModelBuilder;
import club.javafamily.echarts.factory.axis.DefaultAxisFactory;
import club.javafamily.echarts.factory.series.SimpleDataSeriesFactory;
import club.javafamily.echarts.factory.tooltip.DefaultTooltipFactory;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.model.EChartOption;
import club.javafamily.echarts.model.EChartTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
public class BasicLineChartModelBuilder implements ChartModelBuilder {

   @Autowired
   public BasicLineChartModelBuilder(DefaultAxisFactory axisFactory,
                                     DefaultTooltipFactory tooltipFactory, SimpleDataSeriesFactory seriesFactory)
   {
      this.axisFactory = axisFactory;
      this.tooltipFactory = tooltipFactory;
      this.seriesFactory = seriesFactory;
   }

   @Override
   public boolean isMatch(ChartType type, ObjectInfo bindingInfo) {
      return type == ChartType.line && bindingInfo != null;
   }

   @Override
   public EChartOption buildOptions(TableLens lens, ObjectInfo bindingInfo,
                                    ChartHelper chartHelper, ChartType type,
                                    Map<String, Object> params)
   {
      EChartOption options = new EChartOption();

      if(bindingInfo.getTitleInfo() != null
         && StringUtils.hasText(bindingInfo.getTitleInfo().getTitle()))
      {
         options.setTitle(new EChartTitle(bindingInfo.getTitleInfo().getTitle()));
      }

      options.setTooltip(tooltipFactory.build(lens, bindingInfo, chartHelper, type, params));
      options.setxAxis(axisFactory.build(lens, bindingInfo, chartHelper, type, params));

      Map<String, Object> yParams = Tool.deepCloneMap(params);
      yParams.put("isY", true);

      options.setyAxis(axisFactory.build(lens, bindingInfo, chartHelper, type, yParams));

      options.setSeries(seriesFactory.build(lens, bindingInfo, chartHelper, type, params));

      return options;
   }

   private final DefaultTooltipFactory tooltipFactory;
   private final SimpleDataSeriesFactory seriesFactory;
   private final DefaultAxisFactory axisFactory;
}
