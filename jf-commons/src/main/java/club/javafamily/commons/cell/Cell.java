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

package club.javafamily.commons.cell;

import club.javafamily.commons.utils.ExportUtil;

/**
 * Database Cell
 */
public class Cell {
   private Object value;
   private CellValueType type = CellValueType.STRING;

   public Cell() {
   }

   public Cell(Object value) {
      this.value = value;
   }

   public Cell(Object value, CellValueType type) {
      this.value = value;
      this.type = type;
   }

   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public CellValueType getType() {
      return type;
   }

   public void setType(CellValueType type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return ExportUtil.toString(this);
   }
}
