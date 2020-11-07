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

package club.javafamily.runner.filter;

import club.javafamily.runner.config.ThreadContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

/**
 * Request Filter:
 * 1. Getting locale info
 */
public class RequestFilter extends HttpFilter {

   @Override
   protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      // Getting locale
      Locale locale = RequestContextUtils.getLocale(request);
      ThreadContext.setLocale(locale);

      // do chain
      super.doFilter(request, response, chain);
   }
}
