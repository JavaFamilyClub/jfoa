package club.javafamily.runner.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

/**
 * Thymeleaf template utils.
 */
public final class HTMLTemplateUtils {

   private final static TemplateEngine engine;

   static {
      ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
      resolver.setPrefix("/config/templates/");
      resolver.setSuffix(".html");
      engine = new TemplateEngine();
      engine.setTemplateResolver(resolver);
   }

   /**
    * render html to string
    * @param template html file path
    * @param params variables
    * @return rendered HTML string
    */
   public static String render(String template, Map<String,Object> params) {
      Context context = new Context();
      context.setVariables(params);

      return engine.process(template,context);
   }

   private HTMLTemplateUtils() {
   }
}
