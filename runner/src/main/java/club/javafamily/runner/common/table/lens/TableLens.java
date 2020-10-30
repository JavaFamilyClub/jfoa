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

package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.cell.Cell;
import club.javafamily.runner.util.ExportUtil;

import java.awt.*;

/**
 * TableLens interface
 */
public interface TableLens {

   /**
    * get cell
    */
   Cell getObject(int row, int col);

   /**
    * include header row count
    */
   int getRowCount();

   /**
    * include header col count
    */
   int getColCount();

   default int getHeaderRowCount() {
      return 1;
   }

   default int getHeaderColCount() {
      return 0;
   }

   default int getColWidth(int col) {
      int max = 20; // default char count;
      int checkLength = Math.min(getRowCount(), 50); // max check 50 rows

      for(int i = 0; i < checkLength; i++) {
         String value = ExportUtil.toString(getObject(i, col));

         if(value.length() > max) {
            max = value.length();
         }
      }

      return max;
   }

   default boolean isRowHeader(int row) {
      return row < getHeaderRowCount();
   }

   default boolean isColHeader(int col) {
      return col < getHeaderColCount();
   }

   default boolean isHeader(int row, int col) {
      return isRowHeader(row) || isColHeader(col);
   }

   default Font getFont(int row, int col) {
      if(isHeader(row, col)) {
         return LensTool.DEFAULT_HEADER_FONT;
      }

      return LensTool.DEFAULT_TEXT_FONT;
   }

   default Color getBackground(int row, int col) {
      if(isHeader(row, col)) {
         return LensTool.DEFAULT_HEADER_BG;
      }

      return LensTool.DEFAULT_TEXT_BG;
   }

}
