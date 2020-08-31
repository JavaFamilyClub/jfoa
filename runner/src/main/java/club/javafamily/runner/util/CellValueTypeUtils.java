package club.javafamily.runner.util;

import club.javafamily.runner.common.table.cell.CellValueType;

import java.lang.reflect.Array;
import java.util.Date;

public final class CellValueTypeUtils {
   public static CellValueType getCellType(Object obj) {
      if(obj instanceof Integer) {
         return CellValueType.INTEGER;
      }
      else if(obj instanceof Double) {
         return CellValueType.DOUBLE;
      }
      else if(obj instanceof Float) {
         return CellValueType.FLOAT;
      }
      else if(obj instanceof Date) {
         return CellValueType.DATE;
      }
      else if(obj instanceof Array) {
         return CellValueType.ARRAY;
      }
      else {
         return CellValueType.STRING;
      }
   }
}
