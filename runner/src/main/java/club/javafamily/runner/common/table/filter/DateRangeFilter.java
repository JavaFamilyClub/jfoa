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

package club.javafamily.runner.common.table.filter;

import java.util.*;

public class DateRangeFilter implements Filter<Date> {

   @Override
   public List<FilterInfo<Date>> filters() {
      List<FilterInfo<Date>> filters = new ArrayList<>();

      if(this.startDate != null) {
         filters.add(new DateRangeFilterInfo(DATE_RANGE_START, startDate));
      }

      if(this.endDate != null) {
         filters.add(new DateRangeFilterInfo(DATE_RANGE_END, endDate));
      }

      return filters;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public static final String DATE_RANGE_START = "start";
   public static final String DATE_RANGE_END = "end";

   private Date startDate;
   private Date endDate;
}
