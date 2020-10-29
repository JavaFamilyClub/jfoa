package club.javafamily.runner.common.table.format;

import java.util.HashMap;
import java.util.Map;

public class FormatInfo {

   public FormatModel getCellFormat(int row, int col) {
      return formatMap.get(buildKey(row, col));
   }

   public void setCellFormat(int row, int col, FormatModel formatModel) {
      formatMap.put(buildKey(row, col), formatModel);
   }

   public FormatModel getRowFormat(int row) {
      return formatMap.get(buildKey(row, -1));
   }

   public void setRowFormat(int row, FormatModel formatModel) {
      formatMap.put(buildKey(row, -1), formatModel);
   }

   public FormatModel getColFormat(int col) {
      return formatMap.get(buildKey(-1, col));
   }

   public void setColFormat(int col, FormatModel formatModel) {
      formatMap.put(buildKey(-1, col), formatModel);
   }

   private String buildKey(int row, int col) {
      return row + "-" + col;
   }

   private Map<String, FormatModel> formatMap = new HashMap<>();
}

