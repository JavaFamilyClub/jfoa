package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.cell.Cell;

/**
 * TableLens interface
 */
public interface TableLens {

   /**
    * get cell
    * @param row
    * @param col
    * @return
    */
   Cell getObject(int row, int col);

   void setObject(int row, int col, Cell cell);

   int getRowCount();

   int getColCount();

   String getDescription();

}
