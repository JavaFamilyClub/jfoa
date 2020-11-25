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

package club.javafamily.runner.web.widget.echarts.info;

public class TooltipInfo {
   private String trigger;
   private String formatter;

   public TooltipInfo() {
   }

   public TooltipInfo(String trigger, String formatter) {
      this.trigger = trigger;
      this.formatter = formatter;
   }

   public String getTrigger() {
      return trigger;
   }

   public void setTrigger(String trigger) {
      this.trigger = trigger;
   }

   public String getFormatter() {
      return formatter;
   }

   public void setFormatter(String formatter) {
      this.formatter = formatter;
   }
}
