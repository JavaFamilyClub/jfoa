package club.javafamily.runner.web.widget.echarts.service.cpie;

import club.javafamily.runner.common.table.cell.Cell;
import club.javafamily.runner.common.table.cell.CellValueType;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.util.CellValueTypeUtils;
import club.javafamily.runner.web.widget.echarts.*;
import club.javafamily.runner.web.widget.echarts.info.*;
import club.javafamily.runner.web.widget.echarts.info.cpie.CustomPieBindingInfo;
import club.javafamily.runner.web.widget.echarts.service.BaseChartSeriesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class CustomizedPieSeriesFactory extends BaseChartSeriesFactory {

   @Autowired
   public CustomizedPieSeriesFactory(CustomizedPieChartHelper chartHelper) {
      super(chartHelper);
      this.chartHelper = chartHelper;
   }

   @Override
   public List<EChartSeries> build(TableLens lens, BindingInfo bindingInfo,
                                   ChartType type, Map<String, Object> params)
   {
      CustomPieBindingInfo info = (CustomPieBindingInfo) bindingInfo;
      EChartSeries series = new EChartSeries();
      ColorObject labelAndLineColor = new ColorObject("rgba(255, 255, 255, 0.4)");

      TitleInfo titleInfo = bindingInfo.getTitleInfo();

      series.setName(titleInfo != null ? titleInfo.getTitle() : type.name());
      series.setType(type);
      series.setRadius("65%");
      series.setCenter(new String[] {"50%", "55%"});
      series.setRoseType("radius");
      series.setLabel(labelAndLineColor);
      series.setLabelLine(new EChartSeriesLabelLine(
         labelAndLineColor, 0D, 10, 20));

      series.setData(buildCustomizedPieSeriesData(lens, bindingInfo));

      EChartSeriesItemStyle itemStyle
         = new EChartSeriesItemStyle(info.getSeriesItemStyleColor());
      itemStyle.setShadowBlur(200);
      itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
      series.setItemStyle(itemStyle);

      series.setAnimationType(info.getAnimationType());
      series.setAnimationEasing(info.getAnimationEasing());
      series.setAnimationDelay(info.getAnimationDelay());

      return Collections.singletonList(series);
   }

   private List<Object> buildCustomizedPieSeriesData(TableLens lens, BindingInfo bindingInfo) {
      List<AxisInfo> xAxis = bindingInfo.getXAxis();
      List<AxisInfo> yAxis = bindingInfo.getYAxis();

      if(CollectionUtils.isEmpty(xAxis) || CollectionUtils.isEmpty(yAxis)) {
         return null;
      }

      String xBindingColumn = xAxis.get(0).getBindingColumn();
      String yBindingColumn = yAxis.get(0).getBindingColumn();
      Integer xIndex = lens.getColumnIndex(xBindingColumn);
      Integer yIndex = lens.getColumnIndex(yBindingColumn);

      if(xIndex == null || yIndex == null) {
         return null;
      }

      List<Object> data = new ArrayList<>();

      for(int row = lens.getHeaderRowCount(); row < lens.getRowCount(); row++) {
         Cell xLabel = lens.getObject(row, xIndex);
         Cell yValue = lens.getObject(row, yIndex);

         if(!CellValueType.STRING.equals(xLabel.getType()) ||
            !CellValueTypeUtils.isNumber(yValue.getType()))
         {
            LOGGER.error("Type is not match. x: {}, y: {}", xLabel, yValue);
            continue;
         }

         data.add(new NameValueData(
            Objects.toString(lens.getObject(row, xIndex).getValue(), ""),
            Double.parseDouble(lens.getObject(row, xIndex).toString())));
      }

      data.sort(Comparator.comparingDouble(
         item -> ((NameValueData) item).getValue()));

      return data;
   }

   private final CustomizedPieChartHelper chartHelper;
   private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedPieSeriesFactory.class);
}
