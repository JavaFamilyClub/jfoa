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

import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.monitor.model.SystemMonitorSummaryModel;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SystemSummaryController {

   @GetMapping("/em/monitor/system/summary")
   public SystemMonitorSummaryModel getSummaryModel() {
      SystemMonitorSummaryModel model = new SystemMonitorSummaryModel();

      model.setServerTime(new Date());

      RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
      long uptime = runtimeMXBean.getUptime();
      model.setServerStartUpTime(I18nUtil.parseTime(uptime));

      return model;
   }

}
