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

package club.javafamily.echarts.model;

public class EChartInitOptions {

   private String renderer = "svg";
   private Double width;
   private Double height;

   public EChartInitOptions() {
   }

   public EChartInitOptions(Double width, Double height) {
      this.width = width;
      this.height = height;
   }

   public String getRenderer() {
      return renderer;
   }

   public void setRenderer(String renderer) {
      this.renderer = renderer;
   }

   public Double getWidth() {
      return width;
   }

   public void setWidth(Double width) {
      this.width = width;
   }

   public Double getHeight() {
      return height;
   }

   public void setHeight(Double height) {
      this.height = height;
   }
}
