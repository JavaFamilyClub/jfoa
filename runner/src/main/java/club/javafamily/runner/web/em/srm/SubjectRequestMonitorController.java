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

package club.javafamily.runner.web.em.srm;

import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.util.EnumUtil;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.widget.echarts.EChartModel;
import club.javafamily.runner.web.widget.echarts.EChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SubjectRequestMonitorController {

   @Autowired
   public SubjectRequestMonitorController(SubjectRequestService subjectRequestService, EChartService chartService) {
      this.subjectRequestService = subjectRequestService;
      this.chartService = chartService;
   }

   @GetMapping("/subject-request/chart")
   public EChartModel getChartModel(@RequestParam(value = "width", required = false, defaultValue = "500") int width,
                                    @RequestParam(value = "height", required = false, defaultValue = "500") int height,
                                    @RequestParam(value = "type", required = false, defaultValue = "bar") String type)
   {
      List<SubjectRequest> list = subjectRequestService.getList();
      ChartType chartType = EnumUtil.matchEnum(ChartType.class, type);

      return chartService.getSubjectRequestChartModel(list, width, height, chartType);
   }

   private final SubjectRequestService subjectRequestService;
   private final EChartService chartService;
}
