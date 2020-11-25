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

package club.javafamily.runner.web.widget.echarts.service.cpie;

import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.enums.ChartType;
import club.javafamily.runner.web.widget.echarts.EChartTextStyle;
import club.javafamily.runner.web.widget.echarts.EChartTitle;
import club.javafamily.runner.web.widget.echarts.info.*;
import club.javafamily.runner.web.widget.echarts.service.ChartTitleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomizedPieTitleFactory extends ChartTitleFactory {

   @Autowired
   public CustomizedPieTitleFactory(CustomizedPieChartHelper chartHelper) {
      super(chartHelper);
   }

   @Override
   public EChartTitle build(TableLens lens, BindingInfo bindingInfo,
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
