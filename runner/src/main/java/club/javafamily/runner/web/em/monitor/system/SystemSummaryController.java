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
import club.javafamily.runner.service.ServerDumpService;
import club.javafamily.runner.util.*;
import club.javafamily.runner.web.em.monitor.model.SystemMonitorSummaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.management.*;
import java.util.Date;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SystemSummaryController {

   @Autowired
   public SystemSummaryController(ServerDumpService serverDumpService) {
      this.serverDumpService = serverDumpService;
   }

   @GetMapping("/em/monitor/system/summary")
   public SystemMonitorSummaryModel getSummaryModel() {
      SystemMonitorSummaryModel model = new SystemMonitorSummaryModel();

      model.setServerTime(new Date());
      model.setServerStartUpTime(serverDumpService.serverUptime());
      model.setCpuLoadPercent(serverDumpService.cpuUsagePercent());
      model.setMemoryPercent(serverDumpService.memoryPercent());

      return model;
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
}
