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

package club.javafamily.runner.tasks.internal;

import club.javafamily.runner.service.ServerDumpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * For em monitor. dump server info to redis every seconds.
 * and only store recent 60 items default, config by {@code jfoa.server.dump-interval}
 */
@Component
public class DumpServerJob {

   @Autowired
   public DumpServerJob(ServerDumpService serverMonitorService) {
      this.serverMonitorService = serverMonitorService;

      LOGGER.info("System monitor job initialized.");
   }

   @Scheduled(initialDelay = 30000L, fixedRateString = "${jfoa.server.dump-interval}")
   public void systemMonitor() {
      serverMonitorService.dumpServerProperties();
   }

   private final ServerDumpService serverMonitorService;
   private static final Logger LOGGER = LoggerFactory.getLogger(DumpServerJob.class);
}
