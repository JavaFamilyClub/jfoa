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

package club.javafamily.runner.service;

import club.javafamily.commons.cell.Cell;
import club.javafamily.commons.cell.CellValueType;
import club.javafamily.commons.lens.DefaultTableLens;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.runner.admin.ServerMBean;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.properties.MainServerProperties;
import club.javafamily.runner.tasks.internal.ServerDumpInfo;
import club.javafamily.runner.util.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.management.ThreadInfo;
import java.util.Date;
import java.util.List;
import java.util.function.*;

@Service
public class ServerDumpService {

   @Autowired
   public ServerDumpService(ServerMBean serverMBean,
                            MainServerProperties serverProperties,
                            RedisClient<ServerDumpInfo> redisClient)
   {
      this.serverMBean = serverMBean;
      this.redisClient = redisClient;
      this.serverProperties = serverProperties;
   }

   /**
    * Dump server info to redis.
    */
   public void dumpServerProperties() {
      int cpuUsage = serverMBean.cpuUsagePercent();
      int memoryUsagePercent = serverMBean.memoryPercent();
      long memoryUsageMB = serverMBean.memoryUsageMB();
      int threadCount = serverMBean.threadCount();
      long gcTotalCount = serverMBean.gcTotalCount();
      long gcTotalTime = serverMBean.gcTotalTime();
      long heapUsageMB = serverMBean.heapUsageMB();
      int currentLoadedClassCount = serverMBean.currentLoadedClassCount();
      Date date = new Date();

      ServerDumpInfo info = new ServerDumpInfo(date, cpuUsage, memoryUsagePercent,
         memoryUsageMB, threadCount, gcTotalCount, gcTotalTime, heapUsageMB,
         currentLoadedClassCount);

      redisClient.pushFixedList(SYSTEM_MONITOR_KEY, info,
         serverProperties.getDumpCount(), SYSTEM_MONITOR_EXPIRED_TIME);
   }

   public int cpuUsagePercent() {
      return serverMBean.cpuUsagePercent();
   }

   public int memoryPercent() {
      return serverMBean.memoryPercent();
   }

   public String serverUptime() {
      return serverMBean.serverUptime();
   }

   public long heapPercent() {
      return serverMBean.heapPercent();
   }

   public ThreadInfo[] dumpAllThreads() {
      return serverMBean.dumpAllThreads(true, true);
   }

   public void writeThreadDump(OutputStream outStream) {
      ThreadInfo[] threadInfos = dumpAllThreads();

      try(OutputStreamWriter out = new OutputStreamWriter(outStream)) {
         for(ThreadInfo threadInfo : threadInfos) {
            out.write("\n---------------------------------------------------------------\n");
            out.write(threadInfo.toString());
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Getting all server dump info
    */
   public List<ServerDumpInfo> getServerDump() {
      return redisClient.getFixedListAllValues(
         SYSTEM_MONITOR_KEY, serverProperties.getDumpCount());
   }

   /**
    * Getting Memory Usage MB tableLens.
    */
   public TableLens heapUsageMBTableLens() {
      return buildTableLens(ServerDumpInfo::getHeapUsageMB,
         I18nUtil.getString("em.system.summary.heapUsedMB"));
   }

   /**
    * Getting Memory Usage MB tableLens.
    */
   public TableLens liveThreadTableLens() {
      return buildTableLens(ServerDumpInfo::getThreadCount,
         I18nUtil.getString("em.system.summary.liveThreadCount"));
   }

   /**
    * Getting CPU Usage tableLens.
    */
   public TableLens cpuUsageTableLens() {
      return buildTableLens(ServerDumpInfo::getCpuUsage,
         I18nUtil.getString("em.system.summary.cpuPercent"));
   }

   /**
    * Getting Memory Usage Percent tableLens.
    */
   public TableLens memoryPercentUsageTableLens() {
      return buildTableLens(ServerDumpInfo::getMemoryUsagePercent,
         I18nUtil.getString("em.system.summary.memoryPercent"));
   }

   /**
    * Getting Memory Usage MB tableLens.
    */
   public TableLens memoryUsageMBTableLens() {
      return buildTableLens(ServerDumpInfo::getMemoryUsageMB,
         I18nUtil.getString("em.system.summary.memoryUsedMB"));
   }

   private TableLens buildTableLens(Function<ServerDumpInfo, Number> valueProcessor,
                                    String yLabel)
   {
      List<ServerDumpInfo> serverDump = getServerDump();
      DefaultTableLens tableLens = new DefaultTableLens(serverDump.size() + 1, 2);

      int row = 0;

      tableLens.setObject(row, 0, new Cell(I18nUtil.getString("Date")));
      tableLens.setObject(row, 1, new Cell(yLabel));

      for(ServerDumpInfo dumpInfo : serverDump) {
         row++;
         tableLens.setObject(
            row, 0, new Cell(dumpInfo.getDate(), CellValueType.TIME));
         tableLens.setObject(
            row, 1, new Cell(valueProcessor.apply(dumpInfo), CellValueType.LONG));
      }

      return tableLens;
   }

   public static final String SYSTEM_MONITOR_KEY = "jfoa-server-dump";
   private static final int SYSTEM_MONITOR_EXPIRED_TIME = 60 * 60 * 24; // one day

   private final MainServerProperties serverProperties;
   private final ServerMBean serverMBean;
   private final RedisClient<ServerDumpInfo> redisClient;
}
