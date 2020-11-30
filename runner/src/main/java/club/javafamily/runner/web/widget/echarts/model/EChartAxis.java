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

package club.javafamily.runner.web.widget.echarts.model;

import java.util.List;

public class EChartAxis {

   private String type;
   private List<String> data;
   private EChartAxisTick axisTick;

   public EChartAxis() {
   }

   public EChartAxis(String type) {
      this.type = type;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public List<String> getData() {
      return data;
   }

   public void setData(List<String> data) {
      this.data = data;
   }

   public EChartAxisTick getAxisTick() {
      return axisTick;
   }

   public void setAxisTick(EChartAxisTick axisTick) {
      this.axisTick = axisTick;
   }
}
