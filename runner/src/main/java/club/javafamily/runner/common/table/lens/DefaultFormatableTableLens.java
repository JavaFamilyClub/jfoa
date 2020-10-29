package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.format.FormatInfo;
import club.javafamily.runner.common.table.format.FormatModel;

public class DefaultFormatableTableLens extends DefaultTableLens
   implements FormatableTableLens
{

   @Override
   public FormatModel getCellFormat(int row, int col) {
      return formatInfo.getCellFormat(row, col);
   }

   @Override
   public void setCellFormat(int row, int col, FormatModel formatModel) {
      formatInfo.setCellFormat(row, col, formatModel);
   }

   @Override
   public FormatModel getRowFormat(int row) {
      return formatInfo.getRowFormat(row);
   }

   @Override
   public void setRowFormat(int row, FormatModel formatModel) {
      formatInfo.setRowFormat(row, formatModel);
   }

   @Override
   public FormatModel getColFormat(int col) {
      return formatInfo.getColFormat(col);
   }

   @Override
   public void setColFormat(int col, FormatModel formatModel) {
      formatInfo.setColFormat(col, formatModel);
   }

   private FormatInfo formatInfo = new FormatInfo();
}
