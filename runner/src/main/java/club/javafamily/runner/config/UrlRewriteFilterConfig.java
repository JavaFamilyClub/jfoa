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

package club.javafamily.runner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * TODO This filter should be managed by Shiro or config to execute after shiro filter.
 */
@Configuration
public class UrlRewriteFilterConfig extends UrlRewriteFilter {

   private static final String URL_REWRITE = "classpath:/urlrewrite.xml";

   //Inject the Resource from the given location
   @Value(URL_REWRITE)
   private Resource resource;

   //Override the loadUrlRewriter method, and write your own implementation
   protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
      try {
         //Create a UrlRewrite Conf object with the injected resource
         Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(), "@@traceability@@");
         checkConf(conf);
      } catch (IOException ex) {
         throw new ServletException("Unable to load URL rewrite configuration file from " + URL_REWRITE, ex);
      }
   }
}
