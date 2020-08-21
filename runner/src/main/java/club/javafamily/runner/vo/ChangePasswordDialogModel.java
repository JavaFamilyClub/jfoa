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

package club.javafamily.runner.vo;

public class ChangePasswordDialogModel {
   private String account;
   private String oldPwd;
   private String newPwd;
   private String confirmPwd;

   public String getAccount() {
      return account;
   }

   public void setAccount(String account) {
      this.account = account;
   }

   public String getOldPwd() {
      return oldPwd;
   }

   public void setOldPwd(String oldPwd) {
      this.oldPwd = oldPwd;
   }

   public String getNewPwd() {
      return newPwd;
   }

   public void setNewPwd(String newPwd) {
      this.newPwd = newPwd;
   }

   public String getConfirmPwd() {
      return confirmPwd;
   }

   public void setConfirmPwd(String confirmPwd) {
      this.confirmPwd = confirmPwd;
   }

   @Override
   public String toString() {
      return "ChangePasswordDialogModel{" +
         ", account='" + account + '\'' +
         ", oldPwd='" + oldPwd + '\'' +
         ", newPwd='" + newPwd + '\'' +
         ", confirmPwd='" + confirmPwd + '\'' +
         '}';
   }
}
