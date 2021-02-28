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

package club.javafamily.runner.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jfoa.server")
public class MainServerProperties {
   private long dumpInterval = 1000L;
   private int dumpCount = 60;
   private boolean sslEnable;

   public long getDumpInterval() {
      return dumpInterval;
   }

   public void setDumpInterval(long dumpInterval) {
      this.dumpInterval = dumpInterval;
   }

   public int getDumpCount() {
      return dumpCount;
   }

   public void setDumpCount(int dumpCount) {
      this.dumpCount = dumpCount;
   }

   public boolean isSslEnable() {
      return sslEnable;
   }

   public void setSslEnable(boolean sslEnable) {
      this.sslEnable = sslEnable;
   }
}
