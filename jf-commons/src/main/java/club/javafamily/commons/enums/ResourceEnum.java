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

package club.javafamily.commons.enums;

public enum ResourceEnum {
  // pages
  Portal(1, "Portal"),
  EM(2, "Enterprise Manager"),

  // ops
  Audit(65, "Audit"),
  Customer(66, "Customer"),
  MailAuthor(67, "MailAuthor"),
  Password(68, "Password"),
  Upload_Installer(69, "Upload Installer"),
  Role(70, "Role"),
  SubjectRequest(71, "Subject Request")
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
    return label;
  }
}
