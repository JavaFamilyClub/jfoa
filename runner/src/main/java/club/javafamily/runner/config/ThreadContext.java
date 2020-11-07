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

import java.util.Locale;
import java.util.Optional;

/**
 * Thread Info.
 */
public class ThreadContext {

   public static Optional<Locale> getLocale() {
      return Optional.ofNullable(locale.get());
   }

   public static void setLocale(Locale locale) {
      ThreadContext.locale.set(locale);
   }

   private static ThreadLocal<Locale> locale = new ThreadLocal<>();
}
