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

import club.javafamily.runner.admin.ServerMBean;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.properties.MainServerProperties;
import club.javafamily.runner.tasks.internal.ServerDumpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
      int memoryUsage = serverMBean.memoryPercent();
      Date date = new Date();

      ServerDumpInfo info = new ServerDumpInfo(date, cpuUsage, memoryUsage);

      redisClient.pushFixedList(SYSTEM_MONITOR_KEY, info,
         serverProperties.getDumpCount(), SYSTEM_MONITOR_EXPIRED_TIME);
   }

   /**
    * Getting all server dump info
    */
   public List<ServerDumpInfo> getServerDump() {
      return redisClient.getFixedListAllValues(
         SYSTEM_MONITOR_KEY, serverProperties.getDumpCount());
   }

   public static final String SYSTEM_MONITOR_KEY = "jfoa-server-dump";
   private static final int SYSTEM_MONITOR_EXPIRED_TIME = 60 * 60 * 24; // one day

   private final MainServerProperties serverProperties;
   private final ServerMBean serverMBean;
   private final RedisClient<ServerDumpInfo> redisClient;
}
