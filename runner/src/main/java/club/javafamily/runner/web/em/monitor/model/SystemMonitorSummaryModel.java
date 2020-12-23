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

package club.javafamily.runner.web.em.monitor.model;

import club.javafamily.commons.utils.Tool;
import club.javafamily.echarts.model.EChartModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SystemMonitorSummaryModel {
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = Tool.DEFAULT_TIME_ZONE_STR)
   private Date serverTime;
   private String serverStartUpTime;
   private int cpuLoadPercent;
   private int memoryPercent;
   private EChartModel heapMemoryChart;

   public int getMemoryPercent() {
      return memoryPercent;
   }

   public void setMemoryPercent(int memoryPercent) {
      this.memoryPercent = memoryPercent;
   }

   public int getCpuLoadPercent() {
      return cpuLoadPercent;
   }

   public void setCpuLoadPercent(int cpuLoadPercent) {
      this.cpuLoadPercent = cpuLoadPercent;
   }

   public Date getServerTime() {
      return serverTime;
   }

   public void setServerTime(Date serverTime) {
      this.serverTime = serverTime;
   }

   public String getServerStartUpTime() {
      return serverStartUpTime;
   }

   public void setServerStartUpTime(String serverStartUpTime) {
      this.serverStartUpTime = serverStartUpTime;
   }
}
