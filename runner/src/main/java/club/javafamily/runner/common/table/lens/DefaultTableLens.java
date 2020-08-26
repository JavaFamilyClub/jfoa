package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.cell.Cell;

public class DefaultTableLens implements TableLens {

   public DefaultTableLens(int rowCount, int colCount) {
      this.rowCount = rowCount;
      this.colCount = colCount;
      this.data = new Cell[rowCount][colCount];
   }

   @Override
   public Cell getObject(int row, int col) {
      return data[row][col];
   }

   @Override
   public void setObject(int row, int col, Cell cell) {
      data[row][col] = cell;
   }

   @Override
   public int getRowCount() {
      return rowCount;
   }

   @Override
   public int getColCount() {
      return colCount;
   }

   @Override
   public String getDescription() {
      return desc;
   }

   public void setDesc(String desc) {
      this.desc = desc;
   }

   private Cell[][] data;
   private int rowCount;
   private int colCount;
   private String desc;
}
