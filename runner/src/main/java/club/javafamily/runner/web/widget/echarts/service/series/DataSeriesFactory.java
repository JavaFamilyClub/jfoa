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

package club.javafamily.runner.web.widget.echarts.service.series;

import club.javafamily.commons.cell.Cell;
import club.javafamily.commons.cell.CellValueType;
import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.commons.utils.CellValueTypeUtils;
import club.javafamily.runner.web.widget.echarts.info.*;
import club.javafamily.runner.web.widget.echarts.model.*;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class DataSeriesFactory extends ChartSeriesFactory {

   @Override
   public List<EChartSeries> build(TableLens lens, ObjectInfo bindingInfo,
                                   ChartHelper chartHelper,
                                   ChartType type, Map<String, Object> params)
   {
      if(!(bindingInfo instanceof SeriesSupport)) {
         return null;
      }

      SeriesSupport info = (SeriesSupport) bindingInfo;
      EChartSeries series = new EChartSeries();
      ColorObject labelAndLineColor = new ColorObject("rgba(255, 255, 255, 0.4)");

      TitleInfo titleInfo = bindingInfo.getTitleInfo();

      series.setName(titleInfo != null ? titleInfo.getTitle() : type.name());
      series.setType(type);
      series.setRadius("65%");
      series.setCenter(new String[] {"50%", "55%"});
      series.setRoseType("radius");
      series.setLabel(labelAndLineColor);
      series.setLabelLine(new EChartSeriesLabelLine(
         labelAndLineColor, 0D, 10, 20));

      series.setData(buildCustomizedPieSeriesData(lens, bindingInfo));

      EChartSeriesItemStyle itemStyle
         = new EChartSeriesItemStyle(info.getSeriesItemStyleColor());
      itemStyle.setShadowBlur(200);
      itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
      series.setItemStyle(itemStyle);

      series.setAnimationType(info.getAnimationType());
      series.setAnimationEasing(info.getAnimationEasing());
      series.setAnimationDelay(info.getAnimationDelay());

      return Collections.singletonList(series);
   }

   private List<Object> buildCustomizedPieSeriesData(TableLens lens, ObjectInfo bindingInfo) {
      List<AxisInfo> xAxis = bindingInfo.getXAxis();
      List<AxisInfo> yAxis = bindingInfo.getYAxis();

      if(CollectionUtils.isEmpty(xAxis) || CollectionUtils.isEmpty(yAxis)) {
         return null;
      }

      String xBindingColumn = xAxis.get(0).getBindingColumn();
      String yBindingColumn = yAxis.get(0).getBindingColumn();
      Integer xIndex = lens.getColumnIndex(xBindingColumn);
      Integer yIndex = lens.getColumnIndex(yBindingColumn);

      if(xIndex == null || yIndex == null) {
         return null;
      }

      List<Object> data = new ArrayList<>();

      for(int row = lens.getHeaderRowCount(); row < lens.getRowCount(); row++) {
         Cell xLabel = lens.getObject(row, xIndex);
         Cell yValue = lens.getObject(row, yIndex);

         if(!CellValueType.STRING.equals(xLabel.getType()) ||
            !CellValueTypeUtils.isNumber(yValue.getType()))
         {
            LOGGER.error("Type is not match. x: {}, y: {}", xLabel, yValue);
            continue;
         }

         data.add(new NameValueData(
            Objects.toString(lens.getObject(row, xIndex).getValue(), ""),
            Double.parseDouble(lens.getObject(row, xIndex).toString())));
      }

      data.sort(Comparator.comparingDouble(
         item -> ((NameValueData) item).getValue()));

      return data;
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(DataSeriesFactory.class);
}
