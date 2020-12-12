package club.javafamily.runner.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "t_permission")
public class Permission implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private int resource;

   @Column
   private Integer operator;

   @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
   @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
   private Set<Role> roles;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public int getResource() {
      return resource;
   }

   public void setResource(int resource) {
      this.resource = resource;
   }

   public Integer getOperator() {
      return operator;
   }

   public void setOperator(Integer operator) {
      this.operator = operator;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   @Override
   public String toString() {
      return "Permission{" +
         "id=" + id +
         ", resource='" + resource + '\'' +
         ", operator=" + operator +
         '}';
   }
}
