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
import club.javafamily.runner.util.*;
import club.javafamily.runner.web.admin.ServerMBean;
import club.javafamily.runner.web.em.monitor.model.SystemMonitorSummaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.lang.management.*;
import java.util.Date;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SystemSummaryController {

   @Autowired
   public SystemSummaryController(ServerMBean serverMBean) {
      this.serverMBean = serverMBean;
   }

   @GetMapping("/em/monitor/system/summary")
   public SystemMonitorSummaryModel getSummaryModel() {
      SystemMonitorSummaryModel model = new SystemMonitorSummaryModel();

      model.setServerTime(new Date());

      RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
      long uptime = runtimeMXBean.getUptime();
      model.setServerStartUpTime(I18nUtil.parseTime(uptime));

      model.setCpuLoadPercent(serverMBean.cpuUsagePercent());
      model.setMemoryPercent(serverMBean.memoryPercent());

      return model;
   }

   @GetMapping("/em/monitor/system/thread-dump")
   public void downLoadThreadDump(HttpServletResponse response) {
      String fileName = Tool.PROJECT_MAIN + "-thread";

      try {
         fileName = fileName + "-" + WebMvcUtil.getIP().replaceAll("\\.", "-");
      }
      catch(Exception ignore) {
      }

      fileName += ".txt";
      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
      ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);

      ExportUtil.writeDownloadHeader(response, fileName);

      try(OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream())) {
         for(ThreadInfo threadInfo : threadInfos) {
            out.write("---------------------------------------------------------------\n");
            out.write(threadInfo.toString());
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   @GetMapping("/em/monitor/system/gc")
   public void gc() {
      System.gc();
   }

   private final ServerMBean serverMBean;
}
