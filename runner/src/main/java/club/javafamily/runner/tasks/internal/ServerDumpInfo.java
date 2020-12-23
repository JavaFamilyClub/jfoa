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

import java.io.Serializable;
import java.util.Date;

public class ServerDumpInfo implements Serializable {
   private Date date;
   private int cpuUsage;
   private int memoryUsagePercent;
   private long memoryUsageMB;
   private int threadCount;
   private long gcTotalCount;
   private long gcTotalTime;

   public ServerDumpInfo() {
   }

   public ServerDumpInfo(Date date, int cpuUsage, int memoryUsagePercent,
                         long memoryUsageMB, int threadCount,
                         long gcTotalCount, long gcTotalTime)
   {
      this.date = date;
      this.cpuUsage = cpuUsage;
      this.memoryUsagePercent = memoryUsagePercent;
      this.memoryUsageMB = memoryUsageMB;
      this.threadCount = threadCount;
      this.gcTotalCount = gcTotalCount;
      this.gcTotalTime = gcTotalTime;
   }

   public long getGcTotalCount() {
      return gcTotalCount;
   }

   public void setGcTotalCount(long gcTotalCount) {
      this.gcTotalCount = gcTotalCount;
   }

   public long getGcTotalTime() {
      return gcTotalTime;
   }

   public void setGcTotalTime(long gcTotalTime) {
      this.gcTotalTime = gcTotalTime;
   }

   public long getMemoryUsageMB() {
      return memoryUsageMB;
   }

   public void setMemoryUsageMB(long memoryUsageMB) {
      this.memoryUsageMB = memoryUsageMB;
   }

   public int getThreadCount() {
      return threadCount;
   }

   public void setThreadCount(int threadCount) {
      this.threadCount = threadCount;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public int getCpuUsage() {
      return cpuUsage;
   }

   public void setCpuUsage(int cpuUsage) {
      this.cpuUsage = cpuUsage;
   }

   public int getMemoryUsagePercent() {
      return memoryUsagePercent;
   }

   public void setMemoryUsagePercent(int memoryUsagePercent) {
      this.memoryUsagePercent = memoryUsagePercent;
   }

   @Override
   public String toString() {
      return "ServerDumpInfo{" +
         "date=" + date +
         ", cpuUsage=" + cpuUsage +
         ", memoryUsagePercent=" + memoryUsagePercent +
         ", memoryUsageMB=" + memoryUsageMB +
         ", threadCount=" + threadCount +
         ", gcTotalCount=" + gcTotalCount +
         ", gcTotalTime=" + gcTotalTime +
         '}';
   }
}
