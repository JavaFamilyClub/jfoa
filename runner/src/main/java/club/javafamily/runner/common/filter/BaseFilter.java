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

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BaseFilter <T extends FilterInfo<R>, R> implements Filter<T, R>  {

   public abstract void buildFilters(List<T> filterInfos);

   @Override
   public final List<T> filters() {
      if(filterInfos == null) {
         lock.lock();

         try{
            if(filterInfos == null) {
               filterInfos = new ArrayList<>();
               buildFilters(filterInfos);
            }
         }
         finally {
            lock.unlock();
         }
      }

      return filterInfos;
   }

   @Override
   public boolean accept(FilterInfo<T> filterInfo) {
      List<T> filters = filters();

      return !CollectionUtils.isEmpty(filters);
   }

   protected List<T> filterInfos;
   private Lock lock = new ReentrantLock();
}
