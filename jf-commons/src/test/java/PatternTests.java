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

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTests {

   @Test
   public void testMatch() {
      Pattern pattern = Pattern.compile("^Cell \\[(\\d+),(\\d+)]$");

      Matcher matcher = pattern.matcher("Cell [100,200]");

      while(matcher.find()) {
         String trigger = matcher.group(2); // 0 是全局匹配对象 "Cell [100,200]", 1 是 "100", 2 是 "200"

         System.out.println(trigger);
      }
   }

}
