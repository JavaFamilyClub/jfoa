package club.javafamily.commons.utils;

import com.jayway.jsonpath.JsonPath;

public final class JsonPathUtils {

   private JsonPathUtils() {
   }

   public static String parsePath(String json, String jsonPath) {
      return JsonPath.read(json, jsonPath);
   }
}
