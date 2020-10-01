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

import org.springframework.util.ResourceUtils;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.*;

/**
 * UrlRewriteFilter, will wrapper to FilterRegistrationBean<UrlRewriteFilter>.
 *
 * {{@link UrlRewriteFilterConfig#urlRewriteFilter()}} for adjust filter order
 */
public class MainUrlRewriteFilter extends UrlRewriteFilter {

   // Override the loadUrlRewriter method, and write your own implementation
   protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {

      try {
         File config = ResourceUtils.getFile(UrlRewriteFilterConfig.URL_REWRITE);

         //Create a UrlRewrite Conf object with the injected resource
         Conf conf = new Conf(filterConfig.getServletContext(), new FileInputStream(config),
            config.getName(), "@@JavaFamily@@");
         checkConf(conf);
      } catch (IOException ex) {
         throw new ServletException("Unable to load URL rewrite configuration file from "
            + UrlRewriteFilterConfig.URL_REWRITE, ex);
      }
   }

}
