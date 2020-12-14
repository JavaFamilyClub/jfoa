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

package club.javafamily.runner.domain;

import club.javafamily.runner.util.SecurityUtil;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "t_permission")
public class Permission implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private int resource;

   @Column
   private Integer operator;

   public Permission() {
   }

   public Permission(Integer id, int resource, Integer operator) {
      this.id = id;
      this.resource = resource;
      this.operator = operator;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public int getResource() {
      return resource;
   }

   public void setResource(int resource) {
      this.resource = resource;
   }

   public Integer getOperator() {
      return operator;
   }

   public void setOperator(Integer operator) {
      this.operator = operator;
   }

   public String displayOperator() {
      return SecurityUtil.getOperatorPermission(operator);
   }

   @Override
   public String toString() {
      return "Permission{" +
         "id=" + id +
         ", resource='" + resource + '\'' +
         ", operator=" + operator +
         '}';
   }
}
