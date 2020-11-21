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

import club.javafamily.runner.enums.ChartType;

import java.util.List;

public class EChartSeries {

   private String name;
   private ChartType type;
   private String seriesLayoutBy;
   private List<Integer> data;

   public EChartSeries() {
   }

   public EChartSeries(ChartType type, String seriesLayoutBy) {
      this.type = type;
      this.seriesLayoutBy = seriesLayoutBy;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ChartType getType() {
      return type;
   }

   public void setType(ChartType type) {
      this.type = type;
   }

   public String isSeriesLayoutBy() {
      return seriesLayoutBy;
   }

   public void setSeriesLayoutBy(String seriesLayoutBy) {
      this.seriesLayoutBy = seriesLayoutBy;
   }

   public List<Integer> getData() {
      return data;
   }

   public void setData(List<Integer> data) {
      this.data = data;
   }

   public String getSeriesLayoutBy() {
      return seriesLayoutBy;
   }
}
