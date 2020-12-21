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
   private int memoryUsage;

   public ServerDumpInfo() {
   }

   public ServerDumpInfo(Date date, int cpuUsage, int memoryUsage) {
      this.date = date;
      this.cpuUsage = cpuUsage;
      this.memoryUsage = memoryUsage;
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

   public int getMemoryUsage() {
      return memoryUsage;
   }

   public void setMemoryUsage(int memoryUsage) {
      this.memoryUsage = memoryUsage;
   }

   @Override
   public String toString() {
      return "SystemMonitorInfo{" +
         "date=" + date +
         ", cpuUsage=" + cpuUsage +
         ", memoryUsage=" + memoryUsage +
         '}';
   }
}
