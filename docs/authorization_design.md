
# Authorization Design

```text
level/resource/operator
```

> level: 级别,有以下值:
> * system: 代表超级管理员
> * admin: 代表非超级管理员的 Administrator
> * user: 普通用户

> resource: 受授权管理的系统资源
>
> 具体映射参看 `club.javafamily.runner.enums.ResourceEnum`.

```java
public enum ResourceEnum {
   // pages
   Portal(1, "Portal"),
   MailAuthor(67, "portal.toolbar.mailAuthor"),

   EM(2, "security.link.em"),
   EM_Monitor(3, "em.Monitor"),
   Audit(65, "em.audit.Audit"),
   EM_Setting(4, "em.Setting"),

   // ops
   Customer(66, "User"),
   Password(68, "Password"),
   Upload_Installer(69, "em.client.uploadInstaller"),
   Role(70, "Role"),
   SubjectRequest(71, "common.subjectRequest");
}
```

> operator: 对资源的访问, 具体可参看 `club.javafamily.runner.util.SecurityUtil.getOperatorPermission(club.javafamily.commons.enums.PermissionEnum)`

```java
public final class SecurityUtil {

    // ...

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
}
```
