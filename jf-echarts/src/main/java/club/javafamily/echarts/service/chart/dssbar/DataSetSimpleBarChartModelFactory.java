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

package club.javafamily.echarts.service.chart.dssbar;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.model.EChartOption;
import club.javafamily.echarts.service.ChartHelper;
import club.javafamily.echarts.service.ChartModelFactory;
import club.javafamily.echarts.service.axis.DefaultAxisFactory;
import club.javafamily.echarts.service.dataset.DefaultDataSetFactory;
import club.javafamily.echarts.service.legend.DefaultLegendFactory;
import club.javafamily.echarts.service.series.SimpleSeriesFactory;
import club.javafamily.echarts.service.tooltip.DefaultTooltipFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSetSimpleBarChartModelFactory implements ChartModelFactory {

   @Autowired
   public DataSetSimpleBarChartModelFactory(DefaultTooltipFactory tooltipFactory,
                                            DefaultAxisFactory axisFactory,
                                            SimpleSeriesFactory seriesFactory,
                                            DefaultDataSetFactory dataSetFactory,
                                            DefaultLegendFactory legendFactory)
   {
      this.tooltipFactory = tooltipFactory;
      this.axisFactory = axisFactory;
      this.seriesFactory = seriesFactory;
      this.dataSetFactory = dataSetFactory;
      this.legendFactory = legendFactory;
   }

   @Override
   public EChartOption buildOptions(TableLens lens, ObjectInfo bindingInfo,
                                    ChartHelper chartHelper, ChartType type,
                                    Map<String, Object> params)
   {
      EChartOption options = new EChartOption();

      options.setTooltip(tooltipFactory.build(lens, bindingInfo, chartHelper, type, params));
      options.setLegend(legendFactory.build(lens, bindingInfo, chartHelper, type, params));

      // TODO ChartOptionColorFactory
//      options.setColor(color);
      options.setxAxis(axisFactory.build(lens, bindingInfo, chartHelper, type, params));
      options.setyAxis(axisFactory.build(lens, bindingInfo, chartHelper, type, params));
      options.setSeries(seriesFactory.build(lens, bindingInfo, chartHelper, type, params));
      options.setDataset(dataSetFactory.build(lens, bindingInfo, chartHelper, type, params));

      return options;
   }

   private final DefaultTooltipFactory tooltipFactory;
   private final DefaultAxisFactory axisFactory;
   private final SimpleSeriesFactory seriesFactory;
   private final DefaultDataSetFactory dataSetFactory;
   private final DefaultLegendFactory legendFactory;

}
