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

package club.javafamily.runner.web.portal.model;

import java.io.Serializable;

public class SubjectRequestVoteDto implements Serializable {
   private Integer support;
   private Integer oppose;

   public SubjectRequestVoteDto() {
   }

   public SubjectRequestVoteDto(Integer support, Integer oppose) {
      this.support = support;
      this.oppose = oppose;
   }

   public Integer getSupport() {
      return support;
   }

   public void setSupport(Integer support) {
      this.support = support;
   }

   public Integer getOppose() {
      return oppose;
   }

   public void setOppose(Integer oppose) {
      this.oppose = oppose;
   }

   @Override
   public String toString() {
      return "SubjectRequestVoteVO{" +
         "support=" + support +
         ", oppose=" + oppose +
         '}';
   }
}
