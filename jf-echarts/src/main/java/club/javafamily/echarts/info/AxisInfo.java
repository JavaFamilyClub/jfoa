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

import club.javafamily.commons.lens.TableLens;
import club.javafamily.commons.utils.Tool;

public interface AxisInfo {
   String getType();

   String DEFAULT_X_AXIS_TYPE = "category";
   String DEFAULT_Y_AXIS_TYPE = "value";

   default String getBindingColumn() {
      return null;
   }

   default int getBindingColIndex() {
      return -1;
   }

   default boolean isAxisBindingEnabled() {
      return false;
   }

   default int getBindingColIndex(TableLens lens) {
      int colIndex = getBindingColIndex();

      if(colIndex < 0) {
         colIndex = Tool.unboxingNumber(lens.getColumnIndex(getBindingColumn()), -1);
      }

      if(colIndex < 0) {
         throw new IllegalStateException("Invalid column index. column: " + getBindingColumn());
      }

      return colIndex;
   }
}
