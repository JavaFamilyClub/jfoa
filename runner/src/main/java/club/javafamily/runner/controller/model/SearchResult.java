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

package club.javafamily.runner.controller.model;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

   public SearchResult() {
   }

   public SearchResult(List<Searchable> searchables) {
      this.searchables = searchables;
   }

   public long getTotal() {
      return total;
   }

   public void setTotal(long total) {
      this.total = total;
   }

   public List<Searchable> getSearchables() {
      return searchables;
   }

   public void setSearchables(List<Searchable> searchables) {
      this.searchables = searchables;
   }

   private long total;
   private List<Searchable> searchables;
}
