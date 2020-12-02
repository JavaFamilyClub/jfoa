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

/**
 * Provider composite two tableLens's tableLens.
 * @DevWarning ignore right table col header's style.
 */
public class CompositeTableLens implements TableLens {

   private TableLens left;
   private TableLens right;

   private CompositeTableLens(TableLens left, TableLens right) {
      this.left = left;
      this.right = right;
   }

   public static TableLens createCompositeTableLens(TableLens left, TableLens right) {
      if(left == null) {
         return right;
      }
      else if(right == null) {
         return left;
      }
      else {
         return new CompositeTableLens(left, right);
      }
   }

   @Override
   public Cell getObject(int row, int col) {
      final boolean headerCell = isRowHeader(row);
      TableLens table;

      if(col < left.getColCount()) {
         table = left;
      }
      else {
         table = right;
         col = col - left.getColCount();
      }

      boolean smallHeaderTable = table.getHeaderRowCount() < getHeaderRowCount();

      // invalid header region
      if(headerCell && smallHeaderTable && row >= table.getHeaderRowCount()) {
         return null;
      }
      else if(smallHeaderTable) {
         row = row - (getHeaderRowCount() - table.getHeaderRowCount());
      }

      // invalid row region
      if(row >= table.getRowCount()) {
         return null;
      }

      return table.getObject(row, col);
   }

   @Override
   public int getRowCount() {
      return Math.max(left.getRowCount(), right.getRowCount());
   }

   @Override
   public int getColCount() {
      return left.getColCount() + right.getColCount();
   }

   @Override
   public int getHeaderRowCount() {
      return Math.max(left.getHeaderRowCount(), right.getHeaderRowCount());
   }

   /**
    * @DevWarning just left header col
    */
   @Override
   public int getHeaderColCount() {
      return left.getHeaderColCount();
   }

}
