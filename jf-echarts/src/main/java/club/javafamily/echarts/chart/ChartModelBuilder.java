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

package club.javafamily.echarts.chart;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.factory.ChartObjectFactory;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.model.*;

import java.util.Map;

public interface ChartModelBuilder extends ChartObjectFactory<EChartModel> {

   default boolean isAccept(ChartType type, ObjectInfo bindingInfo, ChartHelper chartHelper) {
      return isMatch(type, bindingInfo);
   }

   boolean isMatch(ChartType type, ObjectInfo bindingInfo);

   default EChartModel build(TableLens lens, ObjectInfo bindingInfo,
                             ChartHelper chartHelper,
                             ChartType type, Map<String, Object> params)
   {
      EChartModel model = new EChartModel();

      EChartInitOptions initOpts = buildInitOptions(lens, bindingInfo, chartHelper, type, params);
      EChartOption options = buildOptions(lens, bindingInfo, chartHelper, type, params);

      model.setInitOpts(initOpts);
      model.setOptions(options);

      return model;
   }

   default EChartInitOptions buildInitOptions(TableLens lens, ObjectInfo bindingInfo,
                                              ChartHelper chartHelper,
                                              ChartType type, Map<String, Object> params)
   {
      ChartFormatInfo format = bindingInfo.getFormat();

      if(format == null || format.getBounds() == null) {
         return new EChartInitOptions(600D, 400D);
      }

      double width = format.getBounds().width;
      double height = format.getBounds().height;

      return new EChartInitOptions(width, height);
   }

   EChartOption buildOptions(TableLens lens, ObjectInfo bindingInfo,
                             ChartHelper chartHelper,
                             ChartType type, Map<String, Object> params);

}
