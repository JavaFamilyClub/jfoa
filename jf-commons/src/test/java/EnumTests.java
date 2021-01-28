/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

import club.javafamily.commons.enums.UserType;
import club.javafamily.commons.utils.EnumUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumTests {

   @Test
   void testEnumMethods() {
      UserType type = EnumUtil.matchEnum(UserType.class, e -> e.name().equals("DingTalk"));

      Assertions.assertEquals(type, UserType.DingTalk, "EnumUtils match enum error!");
   }

}
