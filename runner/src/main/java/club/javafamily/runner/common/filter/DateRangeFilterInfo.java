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

package club.javafamily.runner.common.filter;

import club.javafamily.runner.enums.OperatorEnum;

import java.util.Date;

public class DateRangeFilterInfo extends DaoFilterInfo<Date> {

   public DateRangeFilterInfo() {
   }

   public DateRangeFilterInfo(String key, Date value) {
      this.key = key;
      this.value = value;
   }

   public DateRangeFilterInfo(String key, Date value, OperatorEnum operator) {
      this.key = key;
      this.value = value;
      this.operator = operator;
   }

   @Override
   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   @Override
   public Date getValue() {
      return value;
   }

   public void setValue(Date value) {
      this.value = value;
   }

   @Override
   public OperatorEnum getOperator() {
      return operator;
   }

   public void setOperator(OperatorEnum operator) {
      this.operator = operator;
   }

   private String key;
   private Date value;
   private OperatorEnum operator;
}
