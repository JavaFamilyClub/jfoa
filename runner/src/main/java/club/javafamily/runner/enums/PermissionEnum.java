package club.javafamily.runner.enums;

public enum PermissionEnum {
   READ(1),
   WRITE(2),
   DELETE(4),
   ACCESS(8),
   ADMIN(16);

   private int permission;

   PermissionEnum(int permission) {
      this.permission = permission;
   }

   public int getPermission() {
      return this.permission;
   }

   public static PermissionEnum valueOf(int permission) {
      switch(permission) {
         case 1:
            return READ;
         case 2:
            return WRITE;
         case 4:
            return DELETE;
         case 8:
            return ACCESS;
         case 16:
            return ADMIN;
         default:
            throw new RuntimeException("Permission is not found! " + permission);
      }
   }
}
