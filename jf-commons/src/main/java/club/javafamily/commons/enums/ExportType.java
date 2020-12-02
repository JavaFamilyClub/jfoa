package club.javafamily.commons.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum ExportType {
   Excel("Excel", 0, ".xlsx"),
   Excel_2003("Excel2003", 1, ".xls"),
   PDF("PDF", 2, ".pdf");

   ExportType(String label, int type, String suffix) {
      this.label = label;
      this.type = type;
      this.suffix = suffix;
   }

   private String label;
   private int type;
   private String suffix;

   public static ExportType parse(int type) {
      switch(type) {
         case 0:
            return Excel;
         case 1:
            return Excel_2003;
         case 2:
            return PDF;

         default:
            return null;
      }
   }

   public String getLabel() {
      return label;
   }

   public int getType() {
      return type;
   }

   public String getSuffix() {
      return suffix;
   }
}
