package club.javafamily.runner.enums;

public enum ActionType {
  ADD(1, "Add"),
  DELETE(2, "Delete"),
  MODIFY(3, "Modify"),
  VERIFY(4, "Verify");

  private int type;
  private String label;

  ActionType(int type, String label) {
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
