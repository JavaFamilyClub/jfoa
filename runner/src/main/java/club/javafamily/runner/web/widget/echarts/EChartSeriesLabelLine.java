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

public class EChartSeriesLabelLine {
   private ColorObject lineStyle;
   private Double smooth;
   private Integer length;
   private Integer length2;

   public EChartSeriesLabelLine() {
   }

   public EChartSeriesLabelLine(ColorObject lineStyle,
                                Double smooth,
                                Integer length,
                                Integer length2)
   {
      this.lineStyle = lineStyle;
      this.smooth = smooth;
      this.length = length;
      this.length2 = length2;
   }

   public ColorObject getLineStyle() {
      return lineStyle;
   }

   public void setLineStyle(ColorObject lineStyle) {
      this.lineStyle = lineStyle;
   }

   public Double getSmooth() {
      return smooth;
   }

   public void setSmooth(Double smooth) {
      this.smooth = smooth;
   }

   public Integer getLength() {
      return length;
   }

   public void setLength(Integer length) {
      this.length = length;
   }

   public Integer getLength2() {
      return length2;
   }

   public void setLength2(Integer length2) {
      this.length2 = length2;
   }
}
