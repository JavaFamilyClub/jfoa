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

package club.javafamily.runner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "ds.hibernate")
public class SessionFactoryConfig {
   @Autowired
   public SessionFactoryConfig(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   @Bean("sessionFactory")
   public LocalSessionFactoryBean getSessionFactory() throws IOException {
      LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
      localSessionFactoryBean.setDataSource(dataSource);

      localSessionFactoryBean.setAnnotatedPackages("classpath*:club/javafamily/runner/domain");
      Properties hibernateProperties = new Properties();
      hibernateProperties.put("hibernate.dialect",dialect);
//      hibernateProperties.put("current_session_context_class", sessionContextClass);
      hibernateProperties.put("hibernate.show_sql", showSql);
      hibernateProperties.put("hibernate.format_sql", formatSql);
      hibernateProperties.put("hibernate.hbm2ddl.auto", ddlAuto);
      localSessionFactoryBean.setHibernateProperties(hibernateProperties);
      localSessionFactoryBean.setPackagesToScan("club.javafamily.runner.domain");

      return localSessionFactoryBean;
   }

   public String getDialect() {
      return dialect;
   }

   public void setDialect(String dialect) {
      this.dialect = dialect;
   }

   public String getSessionContextClass() {
      return sessionContextClass;
   }

   public void setSessionContextClass(String sessionContextClass) {
      this.sessionContextClass = sessionContextClass;
   }

   public boolean isShowSql() {
      return showSql;
   }

   public void setShowSql(boolean showSql) {
      this.showSql = showSql;
   }

   public boolean isFormatSql() {
      return formatSql;
   }

   public void setFormatSql(boolean formatSql) {
      this.formatSql = formatSql;
   }

   public String getDdlAuto() {
      return ddlAuto;
   }

   public void setDdlAuto(String ddlAuto) {
      this.ddlAuto = ddlAuto;
   }

   private String dialect;
   private String sessionContextClass;
   private boolean showSql;
   private boolean formatSql;
   private String ddlAuto;

   private final DataSource dataSource;
}
