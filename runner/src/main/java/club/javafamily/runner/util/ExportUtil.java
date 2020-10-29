package club.javafamily.runner.util;

import club.javafamily.runner.annotation.Exportable;
import club.javafamily.runner.common.table.cell.Cell;
import club.javafamily.runner.common.table.cell.CellValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class ExportUtil {
   private ExportUtil() {}

   private static final MimetypesFileTypeMap MIME_TYPES = new MimetypesFileTypeMap();

   public static Exportable getExportableTable(Class clazz) {
      Exportable exportable =
         (Exportable) clazz.getDeclaredAnnotation(Exportable.class);

      if(exportable == null) {
         throw new UnsupportedOperationException("Export action is unsupported.");
      }

      return exportable;
   }

   public static String getExportableTableName(Class clazz) {
      Exportable exportableTable = ExportUtil.getExportableTable(clazz);

      String tableName = exportableTable.description();

      if(StringUtils.isEmpty(tableName)) {
         tableName = exportableTable.value();
      }

      return tableName;
   }

   public static Field[] getExportFields(Class clazz) {
      List<Field> exportFields = new ArrayList<>();

      ReflectionUtils.doWithFields(clazz, (field) -> exportFields.add(field), (
         field) -> field.getDeclaredAnnotation(Exportable.class) != null);

      // sort by order
      exportFields.sort(Comparator.comparingInt(
         field -> field.getDeclaredAnnotation(Exportable.class).order()));

      if(exportFields.size() < 1) {
         throw new UnsupportedOperationException("Export action is unsupported.");
      }

      return exportFields.toArray(new Field[0]);
   }

   public static void writeDownloadHeader(HttpServletResponse response,
                                          String fileName)
   {
      String mimeType = MIME_TYPES.getContentType(fileName);

      if (mimeType == null) {
         // set to binary type if MIME mapping not found
         mimeType = "application/octet-stream";
         LOGGER.warn("Can't find mime type for {} download", fileName);
      }

      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setContentType(mimeType);
      String downloadFileName = new String(
         fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
      response.setHeader("Content-Disposition",
         "attachment;fileName=" + downloadFileName);
   }

   public static String toString(Cell cell) {
      if(cell == null) {
         return "";
      }

      Object data = cell.getValue();
      CellValueType type = cell.getType();

      switch(type) {
         case DATE:
            Date date = (Date) data;
            return DateTimeFormatter.ofPattern(Tool.DEFAULT_DATETIME_FORMAT)
               .format(LocalDateTime.ofInstant(date.toInstant(),
                  ZoneId.systemDefault()));
         default:
            return data.toString();
      }
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(ExportUtil.class);
}
