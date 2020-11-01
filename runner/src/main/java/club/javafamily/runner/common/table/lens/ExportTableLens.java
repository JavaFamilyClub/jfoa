package club.javafamily.runner.common.table.lens;

import club.javafamily.runner.common.table.cell.Cell;

import java.awt.*;
import java.util.Map;

public class ExportTableLens implements TableLens {

   private TableLens table;
   private String tableName;
   private String description;
   private Map<String, String> exportProperties;

   public ExportTableLens() {
   }

   public ExportTableLens(TableLens table, String tableName, String description) {
      this(table, tableName, description, null);
   }

   public ExportTableLens(TableLens table, String tableName, String description,
                          Map<String, String> exportProperties)
   {
      this.table = table;
      this.tableName = tableName;
      this.description = description;
      this.exportProperties = exportProperties;
   }

   public Color getTitleBackground() {
      return Color.orange;
   }

   public Font getTitleFont() {
      return new Font(LensTool.DEFAULT_HEADER_FONT.getName(), Font.BOLD, 22);
   }

   public TableLens getTable() {
      return table;
   }

   public String getTableName() {
      return this.tableName;
   }

   public String getDescription() {
      return this.description;
   }

   public String getProperty(String key) {
      return exportProperties.get(key);
   }

   @Override
   public Cell getObject(int row, int col) {
      return table.getObject(row, col);
   }

   @Override
   public int getRowCount() {
      return table.getRowCount();
   }

   @Override
   public int getColCount() {
      return table.getColCount();
   }

   @Override
   public int getHeaderRowCount() {
      return table.getHeaderRowCount();
   }

   @Override
   public int getHeaderColCount() {
      return table.getHeaderColCount();
   }
}
