package club.javafamily.runner.util;

import club.javafamily.runner.annotation.ExportField;
import org.springframework.util.ReflectionUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ExportUtil {
   private ExportUtil() {}

   public static Field[] getExportFields(Class clazz) {
      List<Field> exportFields = new ArrayList<>();

      ReflectionUtils.doWithFields(clazz, (field) -> exportFields.add(field), (
         field) -> field.getDeclaredAnnotation(ExportField.class) != null);

      // sort by order
      exportFields.sort(Comparator.comparingInt(
         field -> field.getDeclaredAnnotation(ExportField.class).order()));

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
