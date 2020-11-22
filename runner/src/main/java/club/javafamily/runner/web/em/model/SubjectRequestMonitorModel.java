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

package club.javafamily.runner.web.em.model;

import club.javafamily.runner.web.widget.echarts.EChartModel;

import java.io.Serializable;

public class SubjectRequestMonitorModel implements Serializable {
   private EChartModel summaryChartModel;
   private EChartModel supportChartModel;
   private EChartModel opposeChartModel;

   public EChartModel getSummaryChartModel() {
      return summaryChartModel;
   }

   public void setSummaryChartModel(EChartModel summaryChartModel) {
      this.summaryChartModel = summaryChartModel;
   }

   public EChartModel getSupportChartModel() {
      return supportChartModel;
   }

   public void setSupportChartModel(EChartModel supportChartModel) {
      this.supportChartModel = supportChartModel;
   }

   public EChartModel getOpposeChartModel() {
      return opposeChartModel;
   }

   public void setOpposeChartModel(EChartModel opposeChartModel) {
      this.opposeChartModel = opposeChartModel;
   }
}
