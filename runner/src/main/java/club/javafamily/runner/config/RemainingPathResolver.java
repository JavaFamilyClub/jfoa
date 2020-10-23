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

import org.springframework.core.MethodParameter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Class that resolves handler method arguments for the remaining path for a ** pattern.
 */
public class RemainingPathResolver implements HandlerMethodArgumentResolver {
   @Override
   public boolean supportsParameter(MethodParameter parameter) {
      return parameter.getParameterType().isAssignableFrom(String.class) &&
         parameter.hasParameterAnnotation(RemainingPath.class);
   }

   @Override
   public Object resolveArgument(MethodParameter parameter,
                                 ModelAndViewContainer mavContainer,
                                 NativeWebRequest request,
                                 WebDataBinderFactory binderFactory) throws Exception
   {
      String path = null;
      String fullPath = (String) request.getAttribute(
         HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE,
         NativeWebRequest.SCOPE_REQUEST);
      String pattern = (String) request.getAttribute(
         HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,
         NativeWebRequest.SCOPE_REQUEST);

      if(fullPath != null && pattern != null) {
         AntPathMatcher matcher = new AntPathMatcher();
         path = matcher.extractPathWithinPattern(pattern, fullPath);

         if(path.isEmpty()) {
            path = "/";
         }
         else if(fullPath.endsWith("/") && !path.endsWith("/")) {
            path = path + "/";
         }
      }

      if(path == null) {
         path = "/";
      }

      // value already decoded by web server, shouldn't need to decode again
      //return URLDecoder.decode(path, "UTF-8");
      return path;
   }
}
