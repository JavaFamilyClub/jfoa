package club.javafamily.runner.enums;

public enum ActionType {
  ADD(1, "Add"),
  DELETE(2, "Delete"),
  MODIFY(3, "Modify"),
  Login(4, "Login"),
  ;

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
