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

package club.javafamily.echarts.info.dynamicLine;

import club.javafamily.echarts.info.DefaultAxisInfo;

public class DynamicLineAxisInfo extends DefaultAxisInfo {

   public DynamicLineAxisInfo(String type) {
      super(type);
   }

   public DynamicLineAxisInfo(String type, Object[] boundaryGap) {
      super(type);
      this.boundaryGap = boundaryGap;
   }

   public Object[] getBoundaryGap() {
      return boundaryGap;
   }

   public void setBoundaryGap(Object[] boundaryGap) {
      this.boundaryGap = boundaryGap;
   }

   private Object[] boundaryGap;
}
