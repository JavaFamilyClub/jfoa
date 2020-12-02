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

package club.javafamily.runner.web.widget.echarts.service;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.runner.web.widget.echarts.info.ObjectInfo;

import java.util.Map;

public interface ChartObjectFactory <T> {

   default boolean isAccept(ChartType type, ObjectInfo bindingInfo, ChartHelper chartHelper) {
      return chartHelper == null || chartHelper.isAccept(type, bindingInfo);
   }

   default T build(ChartType type, TableLens lens,
                   ObjectInfo bindingInfo,
                   ChartHelper chartHelper,
                   Map<String, Object> params)
   {
      if(isAccept(type, bindingInfo, chartHelper) && bindingInfo != null) {
         return build(lens, bindingInfo, chartHelper, type, params);
      }

      return null;
   }

   T build(TableLens lens, ObjectInfo bindingInfo,
           ChartHelper chartHelper,
           ChartType type, Map<String, Object> params);

}
