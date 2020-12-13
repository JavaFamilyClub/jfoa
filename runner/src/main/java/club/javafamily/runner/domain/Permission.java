package club.javafamily.runner.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "t_permission")
public class Permission implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private int resource;

   @Column
   private Integer operator;

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

   @Override
   public String toString() {
      return "Permission{" +
         "id=" + id +
         ", resource='" + resource + '\'' +
         ", operator=" + operator +
         '}';
   }
}
