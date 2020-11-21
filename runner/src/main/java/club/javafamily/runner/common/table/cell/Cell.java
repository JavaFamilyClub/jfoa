package club.javafamily.runner.common.table.cell;

import club.javafamily.runner.util.ExportUtil;

/**
 * Database Cell
 */
public class Cell {
   private Object value;
   private CellValueType type = CellValueType.STRING;

   public Cell() {
   }

   public Cell(Object value) {
      this.value = value;
   }

   public Cell(Object value, CellValueType type) {
      this.value = value;
      this.type = type;
   }

   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public CellValueType getType() {
      return type;
   }

   public void setType(CellValueType type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return ExportUtil.toString(this);
   }
}
