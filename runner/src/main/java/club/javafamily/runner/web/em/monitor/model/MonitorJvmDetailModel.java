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

package club.javafamily.runner.web.em.monitor.model;

import java.util.List;

public class MonitorJvmDetailModel {
   private String jvmVersion;
   private String[] classpath;
   private List<String> vmArgs;

   public List<String> getVmArgs() {
      return vmArgs;
   }

   public void setVmArgs(List<String> vmArgs) {
      this.vmArgs = vmArgs;
   }

   public String getJvmVersion() {
      return jvmVersion;
   }

   public void setJvmVersion(String jvmVersion) {
      this.jvmVersion = jvmVersion;
   }

   public String[] getClasspath() {
      return classpath;
   }

   public void setClasspath(String[] classpath) {
      this.classpath = classpath;
   }
}
