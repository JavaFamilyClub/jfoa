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

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class I18nUtilTest {

   @Test
   public void testGet() {
      String home = I18nUtil.getString("Home", Locale.SIMPLIFIED_CHINESE);

      Assert.assertEquals("I18n Encoding error.", "主页", home);
   }
}
