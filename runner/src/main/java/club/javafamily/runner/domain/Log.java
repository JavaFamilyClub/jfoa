package club.javafamily.runner.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_log")
public class Log implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "integer") // integer COMMENT '编号'
  private Integer id;
  @Column(columnDefinition = "varchar(30) COMMENT '资源'")
  private String resource;
  @Column(columnDefinition = "varchar(30) COMMENT '操作'")
  private String action;
  @Column(columnDefinition = "varchar(30) COMMENT '操作人员'")
  private String customer;
  @Column(columnDefinition = "DATETIME COMMENT '执行时间'")
  private Date date;
  @Column(columnDefinition = "varchar(255) COMMENT '备注'")
  private String message;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Log{" +
       "id=" + id +
       ", date=" + date +
       ", customer='" + customer + '\'' +
       ", action='" + action + '\'' +
       ", resource='" + resource + '\'' +
       ", message='" + message + '\'' +
       '}';
  }
}
