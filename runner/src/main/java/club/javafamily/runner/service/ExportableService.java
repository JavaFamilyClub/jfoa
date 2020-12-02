package club.javafamily.runner.service;

import club.javafamily.commons.lens.TableLens;
import club.javafamily.runner.common.filter.DaoFilter;

public interface ExportableService {

   /**
    * get table lens
    */
   <R extends Comparable<R>> TableLens getTableLens(DaoFilter<R> filter);

   default TableLens getTableLens() {
      return getTableLens(null);
   }
}
