package club.javafamily.runner.util;

import club.javafamily.runner.annotation.Exportable;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ExportUtil {
   private ExportUtil() {}

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
      throws UnsupportedEncodingException
   {
      String type = new MimetypesFileTypeMap().getContentType(fileName);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setContentType(type);
      String downloadFileName = new String(
         fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
      response.setHeader("Content-Disposition",
         "attachment;fileName=" + downloadFileName);
   }
}
