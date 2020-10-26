package club.javafamily.runner.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "t_role")
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String name;

  @Column
  private String desc;

  /**
   * default role for registered user
   */
  @Column
  private boolean defaultRole;

  /**
   * administrator role
   */
  @Column
  private boolean administrator;

  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @ManyToMany(targetEntity = Permission.class, fetch = FetchType.EAGER)
  private Set<Permission> permissions;

  public Role() {
  }

  public Role(String roleName) {
    this.name = roleName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public boolean isDefaultRole() {
    return defaultRole;
  }

  public void setDefaultRole(boolean defaultRole) {
    this.defaultRole = defaultRole;
  }

  public boolean isAdministrator() {
    return administrator;
  }

  public void setAdministrator(boolean administrator) {
    this.administrator = administrator;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  @Override
  public String toString() {
    return "Role{" +
       "id=" + id +
       ", name='" + name + '\'' +
       ", permissions=" + permissions +
       '}';
  }
}
