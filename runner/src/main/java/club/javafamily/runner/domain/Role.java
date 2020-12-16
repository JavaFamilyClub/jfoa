/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity(name = "t_role")
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String name;

  /**
   * 'desc' is mysql key words.
   */
  @Column
  private String description;

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

  @Cascade({org.hibernate.annotations.CascadeType.DELETE})
  @OneToMany(targetEntity = Permission.class, fetch = FetchType.LAZY)
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String desc) {
    this.description = desc;
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

  public void clearPermissions() {
     if(permissions != null) {
        permissions.clear();
     }
  }

  @Override
  public boolean equals(Object o) {
    if(this == o){
      return true;
    }

    if(o == null || getClass() != o.getClass()) {
      return false;
    }

    Role role = (Role) o;

    return Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
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
