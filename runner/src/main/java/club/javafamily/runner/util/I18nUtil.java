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

import club.javafamily.runner.config.ThreadContext;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * i18n support.
 * default lang is <tt>en-us</tt>
 */
public final class I18nUtil {
   private static final Map<String, ResourceBundle> bundles = new ConcurrentHashMap<>();

   public static final Locale DEFAULT_LOCALE = Locale.US;

   private I18nUtil() {
   }

   public static String getString(String key, Object...params) {
      Locale locale = ThreadContext.getLocaleOrElseDefault();
      return getString(key, params, locale);
   }

   public static String getString(String key, Locale locale) {
      return getString(key, null, locale);
   }

   public static String getString(String key, Object[] params, Locale locale) {
      if(key == null) {
         return null;
      }

      ResourceBundle bundle = getBundle(locale);
      String value;

      try {
         value = bundle.getString(key);
      }
      catch(MissingResourceException e) {
         return key;
      }

      if(ArrayUtils.isEmpty(params)) {
         return value;
      }

      MessageFormat messageFormat = new MessageFormat(value, locale);

      return messageFormat.format(params);

   }

   private static ResourceBundle getBundle(Locale locale) {
      String cacheKey = locale.toString();

      if(bundles.containsKey(cacheKey)) {
         return bundles.get(cacheKey);
      }

      ResourceBundle bundle
         = ResourceBundle.getBundle("i18n.messages", locale);

      bundles.put(cacheKey, bundle);

      return bundle;
   }

}
