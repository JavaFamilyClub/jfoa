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

package club.javafamily.commons.lens;

import club.javafamily.commons.cell.Cell;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.ArrayList;

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

   /**
    * Getting data row count.
    */
   default int getDataRowCount() {
      return getRowCount() - getHeaderRowCount();
   }

   /**
    * Check is empty data lens.
    */
   default boolean isEmptyData() {
      return getDataRowCount() < 1;
   }

   /**
    * decoration tableLens.
    */
   default TableLens getTable() {
      return null;
   }

   /**
    * Check is empty lens.
    */
   default boolean isEmpty() {
      return getRowCount() < 1;
   }

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
         String value = LensTool.toString(getObject(i, col));

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

   default Integer getColumnIndex(String columnName) {
      if(getRowCount() < 1 || getColCount() < 1 || StringUtils.isEmpty(columnName)) {
         return null;
      }

      for(int row = 0; row < getHeaderRowCount(); row++) {
         for(int col = 0; col < getColCount(); col++) {
            Cell cell = getObject(row, col);

            if(cell != null && columnName.equals(cell.getValue())) {
               return col;
            }
         }
      }

      return null;
   }

   /**
    * Getting col data.
    */
   default java.util.List<Object> getColData(int colIndex) {
      if(colIndex < 0 || colIndex >= getColCount()) {
         return null;
      }

      java.util.List<Object> data = new ArrayList<>();

      for(int i = getHeaderRowCount(); i < getRowCount(); i++) {
         data.add(LensTool.toString(getObject(i, colIndex)));
      }

      return data;
   }

}
