package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.format.FormatModel;

public interface FormatableTableLens extends TableLens {
   FormatModel getCellFormat(int row, int col);
   void setCellFormat(int row, int col, FormatModel formatModel);

   FormatModel getRowFormat(int row);
   void setRowFormat(int row, FormatModel formatModel);

   FormatModel getColFormat(int col);
   void setColFormat(int col, FormatModel formatModel);
}
