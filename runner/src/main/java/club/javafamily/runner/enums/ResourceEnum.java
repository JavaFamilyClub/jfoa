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
   Portal(1, "Portal"),
   MailAuthor(67, "portal.toolbar.mailAuthor"),

   EM(2, "security.link.em"),
   EM_Monitor(3, "em.Monitor"),
   Audit(65, "em.audit.Audit"),
   EM_Setting(4, "em.Setting"),

   // ops
   Customer(66, "User"),
   Installer(69, "em.client.installer"),
   Role(70, "Role"),
   SubjectRequest(71, "common.subjectRequest"),

   // others, no permission control
   Password(68, "Password")
   ;

   private int type;
   private String label;

   ResourceEnum(int type, String label) {
    this.type = type;
    this.label = label;
   }

   public int getType() {
    return type;
   }

   public String getLabel() {
    return I18nUtil.getString(label);
   }
}
