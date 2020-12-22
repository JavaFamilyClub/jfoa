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

package club.javafamily.echarts.info;

public class DefaultAxisInfo implements AxisInfo {

   protected String type;
   protected String bindingColumn;
   protected int bindingColIndex = -1;
   protected boolean axisBindingEnabled;

   public DefaultAxisInfo() {
   }

   public DefaultAxisInfo(String type) {
      this.type = type;
   }

   @Override
   public String getType() {
      return type;
   }

   @Override
   public String getBindingColumn() {
      return bindingColumn;
   }

   public void setType(String type) {
      this.type = type;
   }

   public void setBindingColumn(String bindingColumn) {
      this.bindingColumn = bindingColumn;
   }

   @Override
   public int getBindingColIndex() {
      return bindingColIndex;
   }

   public void setBindingColIndex(int bindingColIndex) {
      this.bindingColIndex = bindingColIndex;
   }

   @Override
   public boolean isAxisBindingEnabled() {
      return axisBindingEnabled;
   }

   public void setAxisBindingEnabled(boolean axisBindingEnabled) {
      this.axisBindingEnabled = axisBindingEnabled;
   }
}
