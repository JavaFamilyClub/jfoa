package club.javafamily.runner.domain;

import club.javafamily.runner.annotation.Exportable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_log")
@Exportable("JavaFamily OA Audit")
public class Log implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Exportable(description = "编号", order = 0)
  private Integer id;

  @Exportable(description = "资源", order = 1)
  private String resource;

  @Exportable(description = "操作", order = 2)
  private String action;

  @Exportable(description = "操作人员", order = 3)
  private String customer;

  @Exportable(description = "执行时间", order = 4)
  private Date date;

  @Exportable(description = "备注", order = 5)
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
