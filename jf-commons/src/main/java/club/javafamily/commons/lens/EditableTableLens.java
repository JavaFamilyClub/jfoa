package club.javafamily.commons.lens;

import club.javafamily.commons.cell.Cell;

public interface EditableTableLens {
   void setObject(int row, int col, Cell cell);

   default void reset() {
      // no op
   }
}
