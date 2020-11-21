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

public class EChartModel {
   private EChartInitOptions initOpts;
   private EChartOption options;

   public EChartModel() {
   }

   public EChartModel(EChartInitOptions initOpts, EChartOption options) {
      this.initOpts = initOpts;
      this.options = options;
   }

   public EChartInitOptions getInitOpts() {
      return initOpts;
   }

   public void setInitOpts(EChartInitOptions initOpts) {
      this.initOpts = initOpts;
   }

   public EChartOption getOptions() {
      return options;
   }

   public void setOptions(EChartOption options) {
      this.options = options;
   }
}
