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

package club.javafamily.echarts.service.visualmap;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.echarts.info.VisualMapSupport;
import club.javafamily.echarts.model.EChartVisualMap;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.service.ChartHelper;
import club.javafamily.echarts.service.ChartObjectFactory;

public abstract class ChartVisualMapFactory implements ChartObjectFactory<EChartVisualMap> {

   @Override
   public boolean isAccept(ChartType type, ObjectInfo bindingInfo, ChartHelper chartHelper) {
      return chartHelper.isAccept(type, bindingInfo) && bindingInfo instanceof VisualMapSupport;
   }

}
