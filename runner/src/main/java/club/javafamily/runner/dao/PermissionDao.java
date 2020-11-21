package club.javafamily.runner.dao;

import club.javafamily.runner.domain.Permission;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDao extends BaseDao<Permission, Integer>  {
   @Override
   public Class<Permission> getClazz() {
      return Permission.class;
   }
}
