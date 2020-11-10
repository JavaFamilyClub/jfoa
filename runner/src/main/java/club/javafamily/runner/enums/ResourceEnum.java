package club.javafamily.runner.enums;

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
