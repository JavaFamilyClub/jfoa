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

package club.javafamily.echarts.factory.series;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.model.EChartSeries;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.info.LegendInfo;
import club.javafamily.echarts.chart.ChartHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SimpleSeriesFactory extends ChartSeriesFactory {

   @Override
   public List<EChartSeries> build(TableLens lens,
                                   ObjectInfo bindingInfo,
                                   ChartHelper chartHelper,
                                   ChartType type,
                                   Map<String, Object> params)
   {
      List<String> dataCols = bindingInfo.dataSet();
      LegendInfo legend = bindingInfo.getLegend();
      List<EChartSeries> series = new ArrayList<>();

      if(lens.isEmptyData()) {
         LOGGER.info("Empty lens data.");
         return series;
      }

      int seriesCount;
      String seriesLayoutBy = null;

      if(legend != null && LegendInfo.SERIES_LAYOUT_BY_ROW.equals(legend.getSeriesLayoutBy())) {
         seriesCount = dataCols.size();
         seriesLayoutBy = LegendInfo.SERIES_LAYOUT_BY_ROW;
      }
      else {
         seriesCount = lens.getRowCount() - lens.getHeaderRowCount();
      }

      for(int i = 0; i < seriesCount; i++) {
         series.add(new EChartSeries(type, seriesLayoutBy));
      }

      return series;
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSeriesFactory.class);
}
