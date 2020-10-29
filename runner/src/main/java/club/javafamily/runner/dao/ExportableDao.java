package club.javafamily.runner.dao;

import club.javafamily.runner.annotation.Exportable;
import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.common.table.cell.Cell;
import club.javafamily.runner.common.table.lens.*;
import club.javafamily.runner.util.CellValueTypeUtils;
import club.javafamily.runner.util.ExportUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * For exportable dao.
 */
public abstract class ExportableDao<T, R extends Serializable> extends BaseDao<T, R> {

   /**
    * get meta data.
    */
   public Metamodel getMetadata() {
      return getSession().getMetamodel();
   }

   @Transactional(readOnly = true)
   public <R extends Comparable<R>> ExportTableLens getTableLens(DaoFilter<R> filter) {
      DefaultTableLens lens = new DefaultTableLens();
      List<T> data = getAll(filter);
      Class<T> clazz = getClazz();

      // 0. get table name(sheet name)
      String desc = ExportUtil.getExportableTableName(clazz);

      if(StringUtils.isEmpty(desc)) {
         // TODO get table name of db
         desc = clazz.getSimpleName();
      }

      // TODO get table name
      ExportTableLens result = new ExportTableLens(lens, desc, desc);

      // 1. get export field by order
      Field[] exportFields = ExportUtil.getExportFields(clazz);

      // 2. build tablelens data
      lens.setRowCount(lens.getHeaderRowCount() + data.size());
      lens.setColCount(lens.getHeaderColCount() + exportFields.length);
      lens.reset();

      // 3. fill header by order
      for(int headerIndex = 0; headerIndex < lens.getHeaderRowCount(); headerIndex++) {
         for(int colIndex = 0; colIndex < exportFields.length; colIndex++) {
            Field field = exportFields[colIndex];
            Exportable exportField = field.getDeclaredAnnotation(Exportable.class);
            Cell cell = new Cell();
            cell.setValue(exportField.description());
            lens.setObject(headerIndex, colIndex, cell);
         }
      }

      int rowIndex = lens.getHeaderRowCount();

      // 4. fill data
      for(int i = 0; i < data.size(); i++, rowIndex++) {
         T row = data.get(i);

         for(int colIndex = 0; colIndex < exportFields.length; colIndex++) {
            Field field = exportFields[colIndex];
            field.setAccessible(true);
            Object value = ReflectionUtils.getField(field, row);
            Cell cell = new Cell();
            cell.setValue(value);
            cell.setType(CellValueTypeUtils.getCellType(value));
            lens.setObject(rowIndex, colIndex, cell);
         }
      }

      return result;
   }

}
