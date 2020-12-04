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

package club.javafamily.runner.echarts;

import club.javafamily.echarts.ChartModelBuilderService;
import club.javafamily.echarts.chart.ChartHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestPropertySource(properties = {
//   "spring.autoconfigure.exclude=" +
//      "org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration," +
//      "org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration"
//})
public class ChartModelBuilderServiceTests {

   @BeforeAll
   public void init() {
      HELPER_SIZE = chartHelpers.size();
   }

   @Test
   public void notNull() {
      Assertions.assertTrue(HELPER_SIZE > 0, "No usable chart helper.");
      Assertions.assertNotNull(service, "ChartModelBuilderService inject failed.");
   }

   @ParameterizedTest
   @MethodSource("buildChartHelperIndexRange")
   public void testBuild(int helperIndex) {
      ChartHelper chartHelper = chartHelpers.get(helperIndex);

      Assertions.assertNotNull(chartHelper, "Autowired chart helper is null for index" + helperIndex);
   }

   public static IntStream buildChartHelperIndexRange() {
      return IntStream.range(0, HELPER_SIZE);
   }

   @Autowired
   private List<ChartHelper> chartHelpers;

   @Autowired
   private ChartModelBuilderService service;

   private static Integer HELPER_SIZE;
}
