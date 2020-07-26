package club.javafamily.runner.config;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

public class MainWildcardPermissionResolver extends WildcardPermissionResolver {

   @Override
   public Permission resolvePermission(String permissionString) {
      return new MainWildcardPermission(permissionString, isCaseSensitive());
   }
}
