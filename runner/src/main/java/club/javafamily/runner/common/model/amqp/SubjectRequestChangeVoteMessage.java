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

package club.javafamily.runner.common.model.amqp;

import java.io.Serializable;

public class SubjectRequestChangeVoteMessage implements Serializable {

   private Integer id;
   private String ip;
   private boolean support;
   private String account;

   public SubjectRequestChangeVoteMessage() {
   }

   public SubjectRequestChangeVoteMessage(Integer id,
                                          String ip,
                                          boolean support,
                                          String account)
   {
      this.id = id;
      this.ip = ip;
      this.support = support;
      this.account = account;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public boolean isSupport() {
      return support;
   }

   public void setSupport(boolean support) {
      this.support = support;
   }

   public String getAccount() {
      return account;
   }

   public void setAccount(String account) {
      this.account = account;
   }
}
