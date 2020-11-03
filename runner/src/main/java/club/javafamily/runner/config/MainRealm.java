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

package club.javafamily.runner.config;

import club.javafamily.runner.domain.*;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class MainRealm extends AuthorizingRealm {

  private final CustomerService customerService;

  @Autowired
  @Lazy // dependencies service must lazy loading for realm initialize.
  public MainRealm(CustomerService customerService,
                   CredentialsMatcher credentialsMatcher,
                   WildcardPermissionResolver wildcardPermissionResolver)
  {
    super(credentialsMatcher);
    setPermissionResolver(wildcardPermissionResolver);
    this.customerService = customerService;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String account = (String) principals.getPrimaryPrincipal();

    Customer user = customerService.getCustomerByAccount(account);

    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    // 给该用户设置角色
    Set<Role> roles = user.getRoles();

    if(user.isAdmin()) {
      authorizationInfo.addStringPermission(SecurityUtil.ALL_PERMISSION);
    }

    for(Role role : roles) {
      // 添加角色
      authorizationInfo.addRole(role.getName());

      if(role.isAdministrator()) {
        authorizationInfo.addStringPermission(SecurityUtil.ALL_PERMISSION);
      }

      // 添加权限
      for(Permission permission: role.getPermissions()) {
        authorizationInfo.addStringPermission(SecurityUtil.buildPermissionString(account, role, permission));
      }
    }

    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String account = (String) token.getPrincipal();

    Customer user = customerService.getCustomerByAccount(account);

    if(user == null) {
      return null;
    }

    ByteSource credentialsSalt = ByteSource.Util.bytes(account);

    AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(account,
       Objects.toString(user.getPassword(), ""), credentialsSalt, getName());

    return authcInfo;
  }
}
