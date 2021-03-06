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

package club.javafamily.echarts.factory.title;

import club.javafamily.commons.enums.ChartType;
import club.javafamily.commons.lens.TableLens;
import club.javafamily.echarts.model.EChartTextStyle;
import club.javafamily.echarts.model.EChartTitle;
import club.javafamily.echarts.info.*;
import club.javafamily.echarts.chart.ChartHelper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * For example:
 * title: {
 *         text: 'Customized Pie',
 *         left: 'center',
 *         top: 20,
 *         textStyle: {
 *             color: '#ccc'
 *         }
 *     }
 */
@Component
public class DefaultTitleFactory extends ChartTitleFactory {

   @Override
   public EChartTitle build(TableLens lens, ObjectInfo bindingInfo,
                            ChartHelper chartHelper,
                            ChartType type, Map<String, Object> params)
   {
      EChartTitle title = new EChartTitle();
      TitleInfo titleInfo = bindingInfo.getTitleInfo();

      if(titleInfo != null) {
         title.setText(titleInfo.getTitle());
         TitleFormat format = titleInfo.getFormat();

         if(format != null) {
            title.setTop(format.getTop());
            title.setLeft(format.getLeft());
            title.setTextStyle(new EChartTextStyle(format.getColor()));
         }
      }

      return title;
   }
}
