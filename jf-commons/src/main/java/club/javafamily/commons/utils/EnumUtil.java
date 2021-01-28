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

package club.javafamily.commons.utils;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.lang.NonNull;

import java.util.function.Predicate;

public final class EnumUtil {

   public static <E extends Enum<E>> E matchEnum(@NonNull final Class<E> clazz,
                                                 String enumName)
   {
      return matchEnum(clazz, enumName, null);
   }

   public static <E extends Enum<E>> E matchEnum(@NonNull final Class<E> clazz,
                                                 String enumName,
                                                 E defaultEnum)
   {
      E anEnum = EnumUtils.getEnum(clazz, enumName);

      return anEnum != null ? anEnum : defaultEnum;
   }

   public static <E extends Enum<E>> E matchEnum(@NonNull final Class<E> enumClass,
                                                 @NonNull Predicate<E> predicate)
   {
      return matchEnum(enumClass, predicate, null);
   }

   public static <E extends Enum<E>> E matchEnum(@NonNull final Class<E> enumClass,
                                                 @NonNull Predicate<E> predicate,
                                                 E defaultEnum)
   {
      if (!enumClass.isEnum()) {
         return null;
      }

      E[] enumConstants = enumClass.getEnumConstants();

      for(E enumConstant : enumConstants) {
         if(predicate.test(enumConstant)) {
            return enumConstant;
         }
      }

      return defaultEnum;
   }

}
