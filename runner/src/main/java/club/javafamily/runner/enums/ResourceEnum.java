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

package club.javafamily.runner.enums;

import club.javafamily.runner.util.I18nUtil;

public enum ResourceEnum {
   // pages
   PAGE_Portal(1, "Portal"),
   PAGE_MailAuthor(2 | PAGE_Portal.id, "portal.toolbar.mailAuthor"),

   PAGE_EM(4096, "security.link.em"),

   PAGE_EM_Monitor(8192 | PAGE_EM.id, "em.Monitor"),
   PAGE_Audit(16384 | PAGE_EM_Monitor.id, "em.audit.Audit"),
   PAGE_Subject_Request(32768 | PAGE_EM_Monitor.id, "common.subjectRequest"),

   PAGE_EM_Setting(0x100000, "em.Setting"),
   PAGE_Installer(0x200000 | PAGE_EM_Setting.id, "em.client.installer"),

   // ops
   Customer(0x6000000, "User"), // 100663296
   Installer(0x6000001, "em.client.installer"),
   Role(0x6000002, "Role"),
   Subject_Request(0x6000003, "common.subjectRequest"),

   // others, no permission control
   Password(0x8000000, "Password")
   ;

   private int id;
   private String label;
   private String permissionFlag;

   ResourceEnum(int id, String label) {
    this.id = id;
    this.label = label;
    this.permissionFlag = this.name();
   }

   ResourceEnum(int id, String label, String permissionFlag) {
      this.id = id;
      this.label = label;
      this.permissionFlag = permissionFlag;
   }

   public int getId() {
    return id;
   }

   public String getLabel() {
    return I18nUtil.getString(label);
   }

   public String getPermissionFlag() {
      return permissionFlag;
   }
}
