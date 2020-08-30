package club.javafamily.runner.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum ExportType {
   Excel,
   Excel_2003;

   public static ExportType parse(int type) {
      switch(type) {
         case 0:
            return Excel;
         case 1:
            return Excel_2003;

         default:
            return null;
      }
   }
}
