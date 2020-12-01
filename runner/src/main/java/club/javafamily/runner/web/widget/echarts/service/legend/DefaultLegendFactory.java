package club.javafamily.runner.web.widget.echarts.service.legend;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.info.ObjectInfo;
import club.javafamily.runner.web.widget.echarts.service.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultLegendFactory implements ChartLegendFactory {

   @Override
   public Object build(TableLens lens, ObjectInfo bindingInfo,
                       ChartHelper chartHelper, ChartType type, Map<String, Object> params)
   {
      return new Object();
   }

}
