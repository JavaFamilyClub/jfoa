package club.javafamily.runner.util;

import club.javafamily.runner.annotation.ExportField;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
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
}
