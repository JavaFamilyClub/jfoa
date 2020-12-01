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

package club.javafamily.runner.web.widget.echarts.service.chart.cpie;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.info.ObjectInfo;
import club.javafamily.runner.web.widget.echarts.model.ChartFormatInfo;
import club.javafamily.runner.web.widget.echarts.model.EChartOption;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import club.javafamily.runner.web.widget.echarts.service.ChartModelFactory;
import club.javafamily.runner.web.widget.echarts.service.series.DataSeriesFactory;
import club.javafamily.runner.web.widget.echarts.service.title.DefaultTitleFactory;
import club.javafamily.runner.web.widget.echarts.service.tooltip.DefaultTooltipFactory;
import club.javafamily.runner.web.widget.echarts.service.visualmap.DefaultVisualMapFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomizedPieChartModelFactory implements ChartModelFactory {

   @Autowired
   public CustomizedPieChartModelFactory(DefaultTitleFactory titleFactory,
                                         DefaultTooltipFactory tooltipFactory,
                                         DefaultVisualMapFactory visualMapFactory,
                                         DataSeriesFactory seriesFactory)
   {
      this.titleFactory = titleFactory;
      this.tooltipFactory = tooltipFactory;
      this.visualMapFactory = visualMapFactory;
      this.seriesFactory = seriesFactory;
   }

   @Override
   public EChartOption buildOptions(TableLens lens, ObjectInfo bindingInfo,
                                    ChartHelper chartHelper, ChartType type,
                                    Map<String, Object> params)
   {
      EChartOption options = new EChartOption();

      ChartFormatInfo format = bindingInfo.getFormat();
      String bg = format != null ? format.getBackgroundColor() : null;
      options.setBackgroundColor(bg);

      options.setTitle(titleFactory.build(lens, bindingInfo, chartHelper, type, params));

      options.setTooltip(tooltipFactory.build(lens, bindingInfo, chartHelper, type, params));

      options.setVisualMap(visualMapFactory.build(lens, bindingInfo, chartHelper, type, params));

      options.setSeries(seriesFactory.build(lens, bindingInfo, chartHelper, type, params));

      return options;
   }

   private final DefaultTitleFactory titleFactory;
   private final DefaultTooltipFactory tooltipFactory;
   private final DefaultVisualMapFactory visualMapFactory;
   private final DataSeriesFactory seriesFactory;
}
