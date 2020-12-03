package club.javafamily.echarts.service.legend;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.info.ObjectInfo;
import club.javafamily.echarts.service.ChartHelper;
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
