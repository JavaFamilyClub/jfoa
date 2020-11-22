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

public class EChartTitle {
   private String text;
   private String left;
   private Integer top;
   private EChartTextStyle textStyle;

   public EChartTitle() {
   }

   public EChartTitle(String text) {
      this.text = text;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getLeft() {
      return left;
   }

   public void setLeft(String left) {
      this.left = left;
   }

   public Integer getTop() {
      return top;
   }

   public void setTop(Integer top) {
      this.top = top;
   }

   public EChartTextStyle getTextStyle() {
      return textStyle;
   }

   public void setTextStyle(EChartTextStyle textStyle) {
      this.textStyle = textStyle;
   }
}
