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

package club.javafamily.runner.web.widget.echarts.info.binding;

public class CustomPieBindingInfo extends BaseBindingInfo {

   public CustomPieBindingInfo(String subject) {
      super(subject);
   }

   public boolean isShowVisualMap() {
      return showVisualMap;
   }

   public void setShowVisualMap(boolean showVisualMap) {
      this.showVisualMap = showVisualMap;
   }

   public int getMax() {
      return max;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public int getMin() {
      return min;
   }

   public void setMin(int min) {
      this.min = min;
   }

   public String getSeriesItemStyleColor() {
      return seriesItemStyleColor;
   }

   public void setSeriesItemStyleColor(String seriesItemStyleColor) {
      this.seriesItemStyleColor = seriesItemStyleColor;
   }

   public String getAnimationType() {
      return animationType;
   }

   public void setAnimationType(String animationType) {
      this.animationType = animationType;
   }

   public String getAnimationEasing() {
      return animationEasing;
   }

   public void setAnimationEasing(String animationEasing) {
      this.animationEasing = animationEasing;
   }

   public Double getAnimationDelay() {
      return animationDelay;
   }

   public void setAnimationDelay(Double animationDelay) {
      this.animationDelay = animationDelay;
   }

   private int max;
   private int min;
   private boolean showVisualMap;
   private String seriesItemStyleColor;
   private String animationType;
   private String animationEasing;
   private Double animationDelay;
}
