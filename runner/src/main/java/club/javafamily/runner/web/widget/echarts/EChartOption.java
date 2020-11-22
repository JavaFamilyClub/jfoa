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

package club.javafamily.runner.web.widget.echarts;

import java.util.List;

public class EChartOption {

   private String backgroundColor;
   private EChartTitle title;
   private EChartVisualMap visualMap;
   private List<String> color;
   // TODO
   private Object tooltip;
   private Object legend;

   private List<EChartAxis> xAxis;
   private List<EChartAxis> yAxis;
   private EChartDataSet dataset;

   private List<EChartSeries> series;

   public List<String> getColor() {
      return color;
   }

   public void setColor(List<String> color) {
      this.color = color;
   }

   public Object getTooltip() {
      return tooltip;
   }

   public void setTooltip(Object tooltip) {
      this.tooltip = tooltip;
   }

   public List<EChartAxis> getxAxis() {
      return xAxis;
   }

   public List<EChartAxis> getyAxis() {
      return yAxis;
   }

   public void setxAxis(List<EChartAxis> xAxis) {
      this.xAxis = xAxis;
   }

   public void setyAxis(List<EChartAxis> yAxis) {
      this.yAxis = yAxis;
   }

   public EChartDataSet getDataset() {
      return dataset;
   }

   public void setDataset(EChartDataSet dataset) {
      this.dataset = dataset;
   }

   public List<EChartSeries> getSeries() {
      return series;
   }

   public void setSeries(List<EChartSeries> series) {
      this.series = series;
   }

   public Object getLegend() {
      return legend;
   }

   public void setLegend(Object legend) {
      this.legend = legend;
   }

   public String getBackgroundColor() {
      return backgroundColor;
   }

   public void setBackgroundColor(String backgroundColor) {
      this.backgroundColor = backgroundColor;
   }

   public EChartTitle getTitle() {
      return title;
   }

   public void setTitle(EChartTitle title) {
      this.title = title;
   }

   public EChartVisualMap getVisualMap() {
      return visualMap;
   }

   public void setVisualMap(EChartVisualMap visualMap) {
      this.visualMap = visualMap;
   }
}
