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

package club.javafamily.echarts;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.chart.ChartHelper;
import club.javafamily.echarts.chart.ChartModelBuilder;
import club.javafamily.echarts.exception.EChartsException;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.model.EChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChartModelBuilderService {

   @Autowired
   public ChartModelBuilderService(List<ChartModelBuilder> chartBuilders) {
      this.chartBuilders = chartBuilders;
   }

   public EChartModel build(ChartType type, TableLens lens,
                            ObjectInfo bindingInfo,
                            ChartHelper chartHelper,
                            Map<String, Object> params)
   {
      List<ChartModelBuilder> matchBuilders = chartBuilders.stream()
         .filter(builder -> builder.isAccept(type, bindingInfo, chartHelper))
         .collect(Collectors.toList());

      if(matchBuilders.size() < 1) {
         throw new EChartsException("No matched chart model builder.");
      }
      else if(matchBuilders.size() > 1) {
         LOGGER.warn("Multiple chart model builder are matched.");
      }

      ChartModelBuilder chartModelBuilder = matchBuilders.get(0);

      return chartModelBuilder.build(lens, bindingInfo, chartHelper, type, params);
   }

   private final List<ChartModelBuilder> chartBuilders;

   private static final Logger LOGGER = LoggerFactory.getLogger(ChartModelBuilderService.class);
}
