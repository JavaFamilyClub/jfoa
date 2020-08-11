package club.javafamily.runner.util;

import club.javafamily.runner.domain.Permission;
import club.javafamily.runner.domain.Role;
import club.javafamily.runner.enums.PermissionEnum;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public final class SecurityUtil {
   // api
   public static final String API_VERSION = "/api/1.0";

   // user constant
   public static final String Admin = "admin";
   public static final String Anonymous = "Anonymous";

   // role constant
   public static final String Administrator = "Administrator";

   // permission constant
   public static final String SYSTEM_PERMISSION_FLAG = "system";
   public static final String ADMINISTRATOR_PERMISSION_FLAG = "admin";
   public static final String USER_PERMISSION_FLAG = "user";

   public static final String ALL_PERMISSION = "*";
   public static final String PART_DIVIDER_TOKEN = ":";
   public static final String SUBPART_DIVIDER_TOKEN = ",";

   // session
   public static final String REGISTERED_TOKEN = "registered-token";
   public static final String REGISTERED_USER_STORE_PREFIX = "jf-registered-user^^_^^";
   public static final Integer DEFAULT_USER_ACTIVE_TIME = 10 * 60; // 10 m (based on second)

   public static String buildPermissionString(String principle,
                                              Role role,
                                              Permission permission)
   {
      StringBuilder sb = new StringBuilder();

      if(Admin.equals(principle)) {
         sb.append(SYSTEM_PERMISSION_FLAG);
      }
      else if(Administrator.equals(role.getName())) {
         sb.append(ADMINISTRATOR_PERMISSION_FLAG);
      }
      else {
         sb.append(USER_PERMISSION_FLAG);
      }

      sb.append(PART_DIVIDER_TOKEN);

      sb.append(permission.getResource());

      sb.append(PART_DIVIDER_TOKEN);

      sb.append(getOperatorPermission(permission.getOperator()));

      return sb.toString();
   }

   public static String getOperatorPermission(Integer operator) {
      if(operator == null) {
         return "";
      }

      int permission = operator;
      List<String> permissions = new ArrayList<>();

      if(PermissionEnum.READ.getPermission()
         == (permission & PermissionEnum.READ.getPermission()))
      {
         permissions.add(getOperatorPermission(PermissionEnum.READ));
      }

      if(PermissionEnum.WRITE.getPermission()
         == (permission & PermissionEnum.WRITE.getPermission()))
      {
         permissions.add(getOperatorPermission(PermissionEnum.WRITE));
      }

      if(PermissionEnum.DELETE.getPermission()
         == (permission & PermissionEnum.DELETE.getPermission()))
      {
         permissions.add(getOperatorPermission(PermissionEnum.DELETE));
      }

      if(PermissionEnum.ACCESS.getPermission()
         == (permission & PermissionEnum.ACCESS.getPermission()))
      {
         permissions.add(getOperatorPermission(PermissionEnum.ACCESS));
      }

      if(PermissionEnum.ADMIN.getPermission()
         == (permission & PermissionEnum.ADMIN.getPermission()))
      {
         permissions.add(getOperatorPermission(PermissionEnum.ADMIN));
      }

      return permissions.stream().collect(Collectors.joining(SUBPART_DIVIDER_TOKEN));
   }

   public static String getOperatorPermission(PermissionEnum permission) {
      switch(permission) {
         case READ:
            return "r";
         case WRITE:
            return "w";
         case DELETE:
            return "d";
         case ACCESS:
            return "a";
         case ADMIN:
            return ALL_PERMISSION;
         default:
            return "";
      }
   }

   public static StringBuilder getBaseUrl(HttpServletRequest request) {
      StringBuilder path = new StringBuilder();
      path.append(request.getScheme());
      path.append("://");
      path.append(request.getServerName());
      path.append(":");
      path.append(request.getServerPort());

      return path;
   }

   public static String generatorPassword(String userName, String password) {
      SimpleHash simpleHash = new SimpleHash("MD5", password, userName, 1024);

      return simpleHash.toHex();
   }

   public static String generatorRegisterSuccessToken() {
      return UUID.randomUUID().toString();
   }

   public static String generatorRegisterUserPassword() {
      return UUID.randomUUID().toString();
   }
}
