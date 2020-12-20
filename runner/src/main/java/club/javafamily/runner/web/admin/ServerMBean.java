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

package club.javafamily.runner.web.admin;

import club.javafamily.commons.utils.Tool;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

/**
 * System MBean
 */
@Component
@ManagedResource
public class ServerMBean {

   private final OperatingSystemMXBean operatingSystemMXBean
      = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

   @ManagedAttribute(description = "Server Memory Usage Percent")
   public int memoryPercent() {
      long totalMemory = operatingSystemMXBean.getTotalPhysicalMemorySize();
      long freeMemory = operatingSystemMXBean.getFreePhysicalMemorySize();
      double memoryPercent = Tool.getPercent(totalMemory, totalMemory - freeMemory);

      return (int) memoryPercent;
   }

   @ManagedAttribute(description = "CPU Memory Usage Percent")
   public int cpuUsagePercent() {
      return (int) (100 * operatingSystemMXBean.getSystemCpuLoad());
   }

}
