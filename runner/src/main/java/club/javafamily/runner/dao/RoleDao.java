package club.javafamily.runner.dao;

import club.javafamily.runner.domain.Role;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;

@Repository
public class RoleDao extends BaseDao<Role, Integer> {
  public Role getByName(String name) {
    Session session = getSession();

    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Role> query = cb.createQuery(getClazz());
    Root<Role> root = query.from(getClazz());
    query.where(cb.equal(root.get("name"), name));

    return session.createQuery(query).uniqueResultOptional().orElse(null);
  }

  @Override
  protected Class<Role> getClazz() {
    return Role.class;
  }
}
