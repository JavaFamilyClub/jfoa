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

package club.javafamily.runner.web.widget.echarts.model;

import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.commons.enums.ChartType;
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

   public EChartModel getCustomizedPieModel(List<SubjectRequest> list,
                                            Double width,
                                            Double height,
                                            ChartType chartType,
                                            String title,
                                            boolean support)
   {
      EChartInitOptions initOptions = new EChartInitOptions(width, height);
      EChartOption options = buildCustomizedPieOption(list, chartType, title, support);

      return new EChartModel(initOptions, options);
   }

   private EChartOption buildCustomizedPieOption(List<SubjectRequest> list,
                                                 ChartType chartType,
                                                 String title,
                                                 boolean support)
   {
      EChartOption options = new EChartOption();

      options.setBackgroundColor("#2c343c");

      options.setTitle(buildCustomizedPieTitle(title));

      options.setTooltip(buildCustomizedPieTooltip());

      options.setVisualMap(buildCustomizedPieVisualMap());

      options.setSeries(buildCustomizedPieSeries(list, chartType, title, support));

      return options;
   }

   private List<EChartSeries> buildCustomizedPieSeries(
      List<SubjectRequest> list,
      ChartType chartType,
      String title,
      boolean support)
   {
      EChartSeries series = new EChartSeries();
      ColorObject labelAndLineColor = new ColorObject("rgba(255, 255, 255, 0.3)");

      series.setName(title);
      series.setType(chartType);
      series.setRadius("65%");
      series.setCenter(new String[] {"50%", "55%"});
      series.setRoseType("radius");
      series.setLabel(labelAndLineColor);
      series.setLabelLine(new EChartSeriesLabelLine(labelAndLineColor, 0D, 10, 20));

      series.setData(buildCustomizedPieSeriesData(list, support));

      EChartSeriesItemStyle itemStyle
         = new EChartSeriesItemStyle(support ? "green" : "red");
      itemStyle.setShadowBlur(200);
      itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
      series.setItemStyle(itemStyle);

      series.setAnimationType("scale");
      series.setAnimationEasing("animationEasing");
      series.setAnimationDelay(200D);

      return Collections.singletonList(series);
   }

   private List<Object> buildCustomizedPieSeriesData(List<SubjectRequest> list,
                                                     boolean support)
   {
      List<Object> data = new ArrayList<>();
      int vote;

      for(SubjectRequest sr : list) {
         vote = voteService.getVoteCurrentCount(sr.getId(), support);
         data.add(new NameValueData(sr.getSubject(), vote));
      }

      data.sort(Comparator.comparingDouble(
         item -> ((NameValueData) item).getValue()));

      return data;
   }

   private EChartVisualMap buildCustomizedPieVisualMap() {
      EChartVisualMap visualMap = new EChartVisualMap();

      visualMap.setMax(0);
      visualMap.setMin(0);
      visualMap.setShow(false);
      visualMap.setInRange(buildCustomizedPieVisualMapInRange());

      return visualMap;
   }

   private EChartVisualInRange buildCustomizedPieVisualMapInRange() {
      EChartVisualInRange inRange = new EChartVisualInRange();

      inRange.setColorLightness(new Double[] {0D, 1D});

      return inRange;
   }

   private EChartTooltip buildCustomizedPieTooltip() {
      EChartTooltip tooltip = new EChartTooltip();

      tooltip.setTrigger("item");
      tooltip.setFormatter("{a} <br/>{b} : {c} ({d}%)");

      return tooltip;
   }

   private EChartTitle buildCustomizedPieTitle(String title) {
      EChartTitle titleModel = new EChartTitle(title);

      titleModel.setLeft("center");
      titleModel.setTop(10);
      titleModel.setTextStyle(new EChartTextStyle("#ffffff"));

      return titleModel;
   }

   public EChartModel getSummaryChartModel(List<SubjectRequest> list,
                                           Double width,
                                           Double height,
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

      options.setTooltip(new Object());
      options.setLegend(new Object());
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
