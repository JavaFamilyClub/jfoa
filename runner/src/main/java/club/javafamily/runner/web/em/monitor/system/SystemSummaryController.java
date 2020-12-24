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

package club.javafamily.runner.web.em.monitor.system;

import club.javafamily.commons.utils.ExportUtil;
import club.javafamily.commons.utils.Tool;
import club.javafamily.echarts.model.EChartModel;
import club.javafamily.runner.service.ServerDumpService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.WebMvcUtil;
import club.javafamily.runner.web.em.monitor.model.SystemMonitorSummaryModel;
import club.javafamily.runner.web.widget.echarts.EChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SystemSummaryController {

   @Autowired
   public SystemSummaryController(ServerDumpService serverDumpService,
                                  EChartsService chartModelService)
   {
      this.serverDumpService = serverDumpService;
      this.chartModelService = chartModelService;
   }

   @GetMapping("/em/monitor/system/summary")
   public SystemMonitorSummaryModel getSummaryModel() {
      SystemMonitorSummaryModel model = new SystemMonitorSummaryModel();

      model.setServerTime(new Date());
      model.setServerStartUpTime(serverDumpService.serverUptime());
      model.setCpuLoadPercent(serverDumpService.cpuUsagePercent());
      model.setMemoryPercent(serverDumpService.memoryPercent());
      model.setHeapPercent(serverDumpService.heapPercent());

      return model;
   }

   @GetMapping("/em/monitor/system/summary/chart/heap")
   public EChartModel getHeapChart() {
      return chartModelService.buildLineChart(serverDumpService.heapUsageMBTableLens());
   }

   @GetMapping("/em/monitor/system/summary/chart/thread")
   public EChartModel getThreadChart() {
      return chartModelService.buildLineChart(serverDumpService.liveThreadTableLens());
   }

   @GetMapping("/em/monitor/system/summary/chart/memory")
   public EChartModel getMemoryChart() {
      return chartModelService.buildLineChart(serverDumpService.memoryUsageMBTableLens());
   }

   @GetMapping("/em/monitor/system/summary/chart/cpu")
   public EChartModel getCpuChart() {
      return chartModelService.buildLineChart(serverDumpService.cpuUsageTableLens());
   }

   @GetMapping("/em/monitor/system/thread-dump")
   public void downLoadThreadDump(HttpServletResponse response) throws Exception {
      String fileName = Tool.PROJECT_MAIN + "-thread";

      try {
         fileName = fileName + "-" + WebMvcUtil.getIP().replaceAll("\\.", "-");
      }
      catch(Exception ignore) {
      }

      fileName += ".txt";

      ExportUtil.writeDownloadHeader(response, fileName);

      serverDumpService.writeThreadDump(response.getOutputStream());
   }

   @GetMapping("/em/monitor/system/gc")
   public void gc() {
      System.gc();
   }

   private final ServerDumpService serverDumpService;
   private final EChartsService chartModelService;
}
