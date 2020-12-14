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

import club.javafamily.commons.annotation.Exportable;
import club.javafamily.commons.utils.Tool;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_log")
@Exportable("JavaFamily OA Audit")
public class Log implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Exportable(description = "编号", order = 0)
   private Integer id;

   @Exportable(description = "资源", order = 1)
   private String resource;

   @Exportable(description = "操作", order = 2)
   private String action;

   @Exportable(description = "操作人员", order = 3)
   private String customer;

   @Exportable(description = "IP", order = 4)
   private String ip;

   @Exportable(description = "执行时间", order = 5)
   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = Tool.DEFAULT_TIME_ZONE_STR)
   @Temporal(TemporalType.TIMESTAMP)
   private Date date;

   @Exportable(description = "备注", order = 6)
   private String message;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public String getCustomer() {
      return customer;
   }

   public void setCustomer(String customer) {
      this.customer = customer;
   }

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getResource() {
      return resource;
   }

   public void setResource(String resource) {
      this.resource = resource;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public void fillErrorMessage(Exception ex) {
      this.message = "Failed: " + ex.getMessage();
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   @Override
   public String toString() {
      return "Log{" +
         "id=" + id +
         ", resource='" + resource + '\'' +
         ", action='" + action + '\'' +
         ", customer='" + customer + '\'' +
         ", ip='" + ip + '\'' +
         ", date=" + date +
         ", message='" + message + '\'' +
         '}';
   }
}
