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

package club.javafamily.runner.web.widget.echarts;

import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.util.I18nUtil;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EChartService {

   @Autowired
   public EChartService(SubjectVoteService voteService) {
      this.voteService = voteService;
   }

   public EChartModel getSubjectRequestChartModel(List<SubjectRequest> list,
                                                  int width,
                                                  int height,
                                                  ChartType chartType)
   {
      EChartInitOptions initOptions = new EChartInitOptions(width, height);
      EChartOption options = buildEChartOption(list, chartType);

      return new EChartModel(initOptions, options);
   }

   private EChartOption buildEChartOption(List<SubjectRequest> list,
                                          ChartType chartType)
   {
      EChartOption options = new EChartOption();

      List<EChartSeries> series = new ArrayList<>();
      List<List<Object>> source = new ArrayList<>();
      EChartDataSet dataSet = new EChartDataSet(source);
      List<String> color = new ArrayList<>();

      EChartAxis xAxis = new EChartAxis("category");
      EChartAxis yAxis = new EChartAxis("value");

      // support
      series.add(new EChartSeries(chartType, "row"));
      color.add("green");
      // oppose
      series.add(new EChartSeries(chartType, "row"));
      color.add("red");

      List<Object> subjectRequest = new ArrayList<>();
      subjectRequest.add(I18nUtil.getString("common.subjectRequest"));
      List<Object> supportVotes = new ArrayList<>();
      supportVotes.add(I18nUtil.getString("Support"));
      List<Object> opposeVotes = new ArrayList<>();
      opposeVotes.add(I18nUtil.getString("Oppose"));
      int support, oppose;

      for(SubjectRequest sr : list) {
         subjectRequest.add(sr.getSubject());
         support = voteService.getVoteCurrentCount(sr.getId(), true);
         oppose = voteService.getVoteCurrentCount(sr.getId(), false);
         supportVotes.add(support);
         opposeVotes.add(oppose);
      }

      source.add(subjectRequest);
      source.add(supportVotes);
      source.add(opposeVotes);

      options.setColor(color);
      options.setxAxis(Collections.singletonList(xAxis));
      options.setyAxis(Collections.singletonList(yAxis));
      options.setSeries(series);
      options.setDataset(dataSet);

      return options;
   }

   private final SubjectVoteService voteService;
}
