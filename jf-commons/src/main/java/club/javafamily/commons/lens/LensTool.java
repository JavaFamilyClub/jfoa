package club.javafamily.commons.lens;

import club.javafamily.commons.annotation.TableLensColumn;
import club.javafamily.commons.cell.Cell;
import club.javafamily.commons.cell.CellValueType;
import club.javafamily.commons.utils.Tool;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

public class LensTool {

   /**
    * Default text font.
    */
   public static final Font DEFAULT_TEXT_FONT = new Font("Times New Roman", Font.PLAIN, 16);

   /**
    * Default header text font.
    */
   public static final Font DEFAULT_HEADER_FONT = new Font("Times New Roman", Font.BOLD, 18);

   public static final Color DEFAULT_TEXT_BG = Color.WHITE;

   public static final Color DEFAULT_HEADER_BG = Color.LIGHT_GRAY;

   public static final Font getDefaultHeaderFont() {
      return getFont(true);
   }

   public static final Font getDefaultTextFont() {
      return getFont(false);
   }

   private static final Font getFont(boolean bold) {
      Font font = bold ? DEFAULT_HEADER_FONT : DEFAULT_TEXT_FONT;

      return new Font(font.getName(),
         bold ? Font.BOLD : Font.PLAIN,
         font.getSize());
   }

   public static Field[] processTableLensColumn(Field[] fields) {
      List<Field> fieldList = Arrays.asList(fields);
      Iterator<Field> iterator = fieldList.iterator();
      Field field;

      while(iterator.hasNext()) {
         field = iterator.next();
         field.setAccessible(true);
         TableLensColumn column = field.getAnnotation(TableLensColumn.class);

         if(column == null) {
            continue;
         }

         if(column.ignore()) {
            iterator.remove();
         }
      }

      return fieldList.toArray(new Field[0]);
   }

   public static Object getFieldValue(Field field, Object targetObj) {
      field.setAccessible(true);
      Object value = ReflectionUtils.getField(field, targetObj);
      TableLensColumn column = field.getAnnotation(TableLensColumn.class);

      if(value == null || column == null || StringUtils.isEmpty(column.value())) {
         return value;
      }

      String expression = column.value();

      StandardEvaluationContext context = new StandardEvaluationContext(value);
      Expression expr = LensTool.spelParser.parseExpression(expression);
      value = expr.getValue(context, column.valueType());

      return value;
   }

   public static String toString(Cell cell) {
      if(cell == null || cell.getValue() == null) {
         return "";
      }

      Object data = cell.getValue();
      CellValueType type = cell.getType();

      switch(type) {
         case TIME:
            Date time = (Date) data;
            return DateTimeFormatter.ofPattern(Tool.DEFAULT_TIME_FORMAT)
               .format(LocalDateTime.ofInstant(time.toInstant(),
                  Tool.DEFAULT_TIME_ZONE.toZoneId()));

         case DATE:
            Date date = (Date) data;
            return DateTimeFormatter.ofPattern(Tool.DEFAULT_DATETIME_FORMAT)
               .format(LocalDateTime.ofInstant(date.toInstant(),
                  Tool.DEFAULT_TIME_ZONE.toZoneId()));
         default:
            return data.toString();
      }
   }

   private static final SpelExpressionParser spelParser = new SpelExpressionParser();
}
