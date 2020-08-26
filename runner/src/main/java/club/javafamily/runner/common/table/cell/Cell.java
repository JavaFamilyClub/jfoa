package club.javafamily.runner.common.table.cell;

/**
 * Database Cell
 */
public class Cell {
   private Object value;
   private CellValueType type;

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
}
