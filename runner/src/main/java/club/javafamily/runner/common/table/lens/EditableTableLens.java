package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.cell.Cell;

public interface EditableTableLens {
   void setObject(int row, int col, Cell cell);

   default void reset() {
      // no op
   }
}
