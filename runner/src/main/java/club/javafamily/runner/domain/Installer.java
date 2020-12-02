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

package club.javafamily.runner.domain;

import club.javafamily.commons.enums.Platform;
import club.javafamily.commons.utils.Tool;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_installer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Installer implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private Platform platform;
   private String version;
   private String link;
   @DateTimeFormat(pattern = Tool.DEFAULT_DATETIME_FORMAT)
   @JsonFormat(pattern=Tool.DEFAULT_DATETIME_FORMAT, timezone = Tool.DEFAULT_TIME_ZONE_STR)
   private Date uploadDate;

   public Installer() {
      uploadDate = new Date();
   }

   public Installer(Platform platform, String version, String link) {
      this();
      this.platform = platform;
      this.version = version;
      this.link = link;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Platform getPlatform() {
      return platform;
   }

   public void setPlatform(Platform platform) {
      this.platform = platform;
   }

   public String getVersion() {
      return version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public Date getUploadDate() {
      return uploadDate;
   }

   public void setUploadDate(Date uploadDate) {
      this.uploadDate = uploadDate;
   }

   @Override
   public String toString() {
      return "Installer{" +
         "id=" + id +
         ", platform=" + platform +
         ", version='" + version + '\'' +
         ", link='" + link + '\'' +
         ", uploadDate='" + uploadDate + '\'' +
         '}';
   }
}
