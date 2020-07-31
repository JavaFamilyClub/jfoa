package club.javafamily.runner.util;

public class Tool {
   public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

   /**
    * Get the operator to use when concatenating an op parameter to a servlet
    * name.
    */
   public static String getConcat(String servletName) {
      return servletName.indexOf('?') >= 0 ? "&" : "?";
   }
}
