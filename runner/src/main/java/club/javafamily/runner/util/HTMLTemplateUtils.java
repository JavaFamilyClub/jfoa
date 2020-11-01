/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

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
