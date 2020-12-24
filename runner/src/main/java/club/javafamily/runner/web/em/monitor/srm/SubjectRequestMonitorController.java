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

package club.javafamily.runner.web.em.monitor.srm;

import club.javafamily.commons.utils.EnumUtil;
import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.commons.enums.ChartType;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.em.monitor.model.SubjectRequestMonitorModel;
import club.javafamily.echarts.model.EChartModel;
import club.javafamily.runner.web.widget.echarts.EChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SubjectRequestMonitorController {

   @Autowired
   public SubjectRequestMonitorController(SubjectRequestService subjectRequestService,
                                          EChartService chartService)
   {
      this.subjectRequestService = subjectRequestService;
      this.chartService = chartService;
   }

   @GetMapping("/subject-request/monitor")
   public SubjectRequestMonitorModel getSubjectMonitorModel(
      @RequestParam(value = "width", required = false) Double width,
      @RequestParam(value = "height", required = false) Double height)
   {
      SubjectRequestMonitorModel model = new SubjectRequestMonitorModel();

      model.setSummaryChartModel(getSummaryChartModel(width, height, "bar"));
      model.setSupportChartModel(getSupportChartModel(true, width, height, "pie"));
      model.setOpposeChartModel(getSupportChartModel(false, width, height, "pie"));

      return model;
   }

   @GetMapping("/subject-request/chart/summary")
   public EChartModel getSummaryChartModel(
      @RequestParam(value = "width", required = false) Double width,
      @RequestParam(value = "height", required = false) Double height,
      @RequestParam(value = "type", required = false, defaultValue = "bar") String type)
   {
      List<SubjectRequest> list = subjectRequestService.getList();
      ChartType chartType = EnumUtil.matchEnum(ChartType.class, type);

      return chartService.getSummaryChartModel(list, width, height, chartType);
   }

   @GetMapping("/subject-request/chart/support")
   public EChartModel getSupportChartModel(
      @RequestParam("support") boolean support,
      @RequestParam(value = "width", required = false) Double width,
      @RequestParam(value = "height", required = false) Double height,
      @RequestParam(value = "type", required = false, defaultValue = "pie") String type)
   {
      List<SubjectRequest> list = subjectRequestService.getList();
      ChartType chartType = EnumUtil.matchEnum(ChartType.class, type);

      String title = I18nUtil.getString(
         support ? "common.subjectRequestSupport" : "common.subjectRequestOppose");

      return chartService.getCustomizedPieModel(
         list, width, height, chartType, title, support);
   }

   private final SubjectRequestService subjectRequestService;
   private final EChartService chartService;
}
