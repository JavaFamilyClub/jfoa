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

package club.javafamily.runner.dao;

import club.javafamily.runner.common.table.cell.Cell;
import club.javafamily.runner.common.table.lens.*;
import club.javafamily.runner.util.CellValueTypeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public interface TableLensDao <T, R extends Serializable> extends CURDDao<T, R> {

   Class<T> getClazz();

   default TableLens getTableLens() {
      DefaultTableLens lens = new DefaultTableLens();
      List<T> data = getAll(null);
      Class<T> clazz = getClazz();

      Field[] fields = clazz.getDeclaredFields();

      if(CollectionUtils.isEmpty(data) || ArrayUtils.isEmpty(fields)) {
         return lens;
      }

      fields = LensTool.processTableLensColumn(fields);

      lens.setColCount(fields.length);
      lens.setRowCount(data.size() + 1);
      lens.reset();

      // fill header
      Field field;
      int row = 0;

      for(int i = 0; i < fields.length; i++) {
         field = fields[i];

         lens.setObject(row, i, new Cell(field.getName()));
      }

      // fill data

      for(int i = 0; i < data.size(); i++) {
         row++;
         T obj = data.get(i);

         for(int col = 0; col < fields.length; col++) {
            field = fields[col];
            Object value = LensTool.getFieldValue(field, obj);
            Cell cell = new Cell(value, CellValueTypeUtils.getCellType(value));

            lens.setObject(row, col, cell);
         }
      }

      return lens;
   }

}
