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

package club.javafamily.runner.web.widget.echarts.service.dataset;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.model.EChartDataSet;
import club.javafamily.runner.web.widget.echarts.info.*;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class DefaultDataSetFactory extends ChartDataSetFactory {

   @Override
   public EChartDataSet build(TableLens lens, ObjectInfo bindingInfo,
                              ChartHelper chartHelper,
                              ChartType type, Map<String, Object> params)
   {
      List<List<Object>> source = new ArrayList<>();
      EChartDataSet dataSet = new EChartDataSet(source);

      List<AxisInfo> xAxis = bindingInfo.getXAxis();
      List<AxisInfo> yAxis = bindingInfo.getYAxis();

      if(!CollectionUtils.isEmpty(xAxis)) {
         String xBinding = xAxis.get(0).getBindingColumn();
         List<Object> xRow = fillDataSet(lens, xBinding);

         if(!CollectionUtils.isEmpty(xRow)) {
            source.add(xRow);
         }
      }

      if(!CollectionUtils.isEmpty(yAxis)) {
         for(AxisInfo ya : yAxis) {
            List<Object> yRow = fillDataSet(lens, ya.getBindingColumn());

            if(!CollectionUtils.isEmpty(yRow)) {
               source.add(yRow);
            }
         }
      }

      return dataSet;
   }

   private List<Object> fillDataSet(TableLens lens, String bindingColumn) {
      List<Object> data;
      Integer index = lens.getColumnIndex(bindingColumn);

      if(index != null && index >= 0) {
         data = new ArrayList<>();
         int rowCount = lens.getDataRowCount();

         for(int row = 0; row < rowCount; row++) {
            data.add(lens.getObject(row, index).getValue());
         }

         return data;
      }

      return null;
   }

}
