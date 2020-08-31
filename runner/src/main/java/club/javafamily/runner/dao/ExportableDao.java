package club.javafamily.runner.dao;

import club.javafamily.runner.annotation.ExportField;
import club.javafamily.runner.common.filter.DaoFilter;
import club.javafamily.runner.common.table.cell.Cell;
import club.javafamily.runner.common.table.cell.CellValueType;
import club.javafamily.runner.common.table.lens.DefaultTableLens;
import club.javafamily.runner.common.table.lens.TableLens;
import club.javafamily.runner.util.CellValueTypeUtils;
import club.javafamily.runner.util.ExportUtil;
import org.springframework.util.ReflectionUtils;

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

   public <R extends Comparable<R>> TableLens getTableLens(DaoFilter<R> filter) {
      DefaultTableLens lens = new DefaultTableLens();
      List<T> data = getAll(filter);
      Class<T> clazz = getClazz();

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
            ExportField exportField = field.getDeclaredAnnotation(ExportField.class);
            Cell cell = new Cell();
            cell.setValue(exportField.description());
            lens.setObject(headerIndex, colIndex, cell);
         }
      }

      // 4. fill data
      final int rowCount = lens.getHeaderRowCount() + data.size();

      for(int rowIndex = lens.getHeaderRowCount(); rowIndex < rowCount; rowIndex++) {
         T row = data.get(rowIndex);

         for(int colIndex = 0; colIndex < exportFields.length; colIndex++) {
            Field field = exportFields[colIndex];
            field.setAccessible(true);
            Object value = ReflectionUtils.getField(field, row.getClass());
            Cell cell = new Cell();
            cell.setValue(value);
            cell.setType(CellValueTypeUtils.getCellType(value));
            lens.setObject(rowIndex, colIndex, cell);
         }
      }

      return lens;
   }

}
