package club.javafamily.runner.web.widget.echarts.service.dssbar;

import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.info.BindingInfo;
import club.javafamily.runner.web.widget.echarts.info.binding.DataSetSimpleBarBindingInfo;
import club.javafamily.runner.web.widget.echarts.info.dssbar.DataSetSimpleBarAxisInfo;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataSetSimpleBarChartHelper implements ChartHelper {
   @Override
   public boolean isAccept(ChartType type, BindingInfo bindingInfo) {
      return type == ChartType.bar
         && (bindingInfo == null || bindingInfo instanceof DataSetSimpleBarBindingInfo);
   }

   @Override
   public BindingInfo buildDefaultBindingInfo(String subject) {
      DataSetSimpleBarBindingInfo bindingInfo = new DataSetSimpleBarBindingInfo(subject);

      bindingInfo.setxAxisInfo(
         Collections.singletonList(new DataSetSimpleBarAxisInfo("category")));
      bindingInfo.setyAxisInfo(
         Collections.singletonList(new DataSetSimpleBarAxisInfo("value")));

      return bindingInfo;
   }
}
