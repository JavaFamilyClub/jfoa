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

package club.javafamily.runner.admin;

import club.javafamily.commons.utils.Tool;
import club.javafamily.runner.util.I18nUtil;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.management.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * System MBean
 */
@Component
@ManagedResource
public class ServerMBean {

   private final Runtime runtime = Runtime.getRuntime();
   private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
   private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
   private final ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
   private final OperatingSystemMXBean operatingSystemMXBean
      = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
   private final List<GarbageCollectorMXBean> gcMXBeans
      = ManagementFactory.getGarbageCollectorMXBeans();

   private static final int BYTE_TO_MB = 1024 * 1024;
   private static final String SPLIT = " -- ";

   private long usedMemory() {
      long totalMemory = operatingSystemMXBean.getTotalPhysicalMemorySize();
      long freeMemory = operatingSystemMXBean.getFreePhysicalMemorySize();

      return totalMemory - freeMemory;
   }

   @ManagedAttribute(description = "Server Memory Usage Percent")
   public int getMemoryPercent() {
      long totalMemory = operatingSystemMXBean.getTotalPhysicalMemorySize();
      long freeMemory = operatingSystemMXBean.getFreePhysicalMemorySize();
      double memoryPercent = Tool.getPercent(totalMemory, totalMemory - freeMemory);

      return (int) memoryPercent;
   }

   @ManagedAttribute(description = "Used memory, in MB")
   public long getMemoryUsageMB() {
      long used = usedMemory();

      return Math.round(1.0D * used / BYTE_TO_MB);
   }

   @ManagedAttribute(description = "Server Heap Usage Percent")
   public int getHeapPercent() {
      long totalMemory = runtime.totalMemory();
      long freeMemory = runtime.freeMemory();
      double memoryPercent = Tool.getPercent(totalMemory, totalMemory - freeMemory);

      return (int) memoryPercent;
   }

   @ManagedAttribute(description = "Used heap memory, in MB")
   public long getHeapUsageMB() {
      long totalMemory = runtime.totalMemory();
      long freeMemory = runtime.freeMemory();
      long used = totalMemory - freeMemory;

      return Math.round(1.0D * used / BYTE_TO_MB);
   }

   @ManagedAttribute(description = "Total heap memory, in MB")
   public long getHeapTotalMB() {
      long totalMemory = runtime.totalMemory();

      return Math.round(1.0D * totalMemory / BYTE_TO_MB);
   }

   @ManagedAttribute(description = "CPU Memory Usage Percent")
   public int getCpuUsagePercent() {
      return (int) (100 * operatingSystemMXBean.getSystemCpuLoad());
   }

   @ManagedAttribute(description = "Live threads count including both daemon and non-daemon threads")
   public int getThreadCount() {
      return threadMXBean.getThreadCount();
   }

   @ManagedAttribute(description = "Current loaded class count")
   public int getCurrentLoadedClassCount() {
      return classLoadingMXBean.getLoadedClassCount();
   }

   @ManagedAttribute(description = "All gc total count")
   public long getGcTotalCount() {
      return gcMXBeans.stream()
         .collect(Collectors.summarizingLong(GarbageCollectorMXBean::getCollectionCount))
         .getSum();
   }

   @ManagedAttribute(description = "All gc total time milliseconds")
   public long getGcTotalTime() {
      return gcMXBeans.stream()
         .collect(Collectors.summarizingLong(GarbageCollectorMXBean::getCollectionTime))
         .getSum();
   }

   @ManagedAttribute(description = "Server uptime")
   public String getServerUptime() {
      long uptime = runtimeMXBean.getUptime();

      return I18nUtil.parseTime(uptime);
   }

   @ManagedAttribute(description = "Classpath")
   public String getClassPath() {
      return runtimeMXBean.getClassPath();
   }

   @ManagedAttribute(description = "JVM Version")
   public String getJvmVersion() {
      return runtimeMXBean.getSpecVersion() +
         ": " +
         runtimeMXBean.getVmName() +
         SPLIT +
         runtimeMXBean.getVmVendor() +
         SPLIT +
         runtimeMXBean.getVmVersion();
   }

   @ManagedAttribute(description = "Run app with arguments")
   public List<String> getStartArguments() {
      return runtimeMXBean.getInputArguments();
   }

   @ManagedOperation(description = "dump all threads")
   public ThreadInfo[] dumpAllThreads(boolean lockedMonitors, boolean lockedSynchronizers) {
      return threadMXBean.dumpAllThreads(lockedMonitors, lockedSynchronizers);
   }

}
