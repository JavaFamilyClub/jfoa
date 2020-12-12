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

package club.javafamily.runner.util;

import club.javafamily.commons.enums.PermissionEnum;
import club.javafamily.runner.domain.*;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static club.javafamily.commons.enums.PermissionEnum.*;

public final class SecurityUtil {
   // api
   public static final String API_VERSION = "/api/1.0";
   public static final String CLIENT_API_VERSION = "/client/api/1.0";

   public static final String Author_Email = "javafamily.club@outlook.com";

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
      EnumSet<PermissionEnum> enums = parsePermissionOperator(operator);

      return enums.stream()
         .map(SecurityUtil::getOperatorPermission)
         .collect(Collectors.joining(SUBPART_DIVIDER_TOKEN));
   }

   public static EnumSet<PermissionEnum> parsePermissionOperator(Integer operator) {
      if(operator == null) {
         return EnumSet.noneOf(PermissionEnum.class);
      }

      int permission = operator;
      List<PermissionEnum> permissions = new ArrayList<>();

      if(READ.getPermission()
         == (permission & READ.getPermission()))
      {
         permissions.add(READ);
      }

      if(WRITE.getPermission()
         == (permission & WRITE.getPermission()))
      {
         permissions.add(WRITE);
      }

      if(DELETE.getPermission()
         == (permission & DELETE.getPermission()))
      {
         permissions.add(DELETE);
      }

      if(ACCESS.getPermission()
         == (permission & ACCESS.getPermission()))
      {
         permissions.add(ACCESS);
      }

      if(ADMIN.getPermission()
         == (permission & ADMIN.getPermission()))
      {
         permissions.add(ADMIN);
      }

      return EnumSet.copyOf(permissions);
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

   public static String generatorPassword(String account, String password) {
      SimpleHash simpleHash = new SimpleHash("MD5",
         Objects.toString(password, ""), account, 1024);

      return simpleHash.toHex();
   }

   public static boolean isAdmin(Customer user) {
      return user != null && SecurityUtil.Admin.equals(user.getAccount());
   }

   public static String generatorRegisterSuccessToken() {
      return UUID.randomUUID().toString();
   }

   public static String generatorRegisterUserPassword() {
      return UUID.randomUUID().toString();
   }
}
