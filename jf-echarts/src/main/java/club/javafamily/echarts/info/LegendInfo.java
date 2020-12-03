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

package club.javafamily.echarts.info;

public class LegendInfo {

   public static final String SERIES_LAYOUT_BY_ROW = "row";

   private String columnName;
   private String seriesLayoutBy;

   public String getColumnName() {
      return columnName;
   }

   public void setColumnName(String columnName) {
      this.columnName = columnName;
   }

   public String getSeriesLayoutBy() {
      return seriesLayoutBy;
   }

   public void setSeriesLayoutBy(String seriesLayoutBy) {
      this.seriesLayoutBy = seriesLayoutBy;
   }
}
