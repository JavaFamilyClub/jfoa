package club.javafamily.runner.enums;

public enum ResourceEnum {
  PORTAL(1, "Portal"),
  EM(2, "Enterprise Manager");

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
