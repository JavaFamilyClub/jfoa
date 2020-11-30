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

public class EChartVisualMap {
   private boolean show;
   private Integer min;
   private Integer max;
   private EChartVisualInRange inRange;

   public boolean isShow() {
      return show;
   }

   public void setShow(boolean show) {
      this.show = show;
   }

   public Integer getMin() {
      return min;
   }

   public void setMin(Integer min) {
      this.min = min;
   }

   public Integer getMax() {
      return max;
   }

   public void setMax(Integer max) {
      this.max = max;
   }

   public EChartVisualInRange getInRange() {
      return inRange;
   }

   public void setInRange(EChartVisualInRange inRange) {
      this.inRange = inRange;
   }
}
