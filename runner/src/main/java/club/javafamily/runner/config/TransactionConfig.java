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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
public class TransactionConfig implements TransactionManagementConfigurer {

   @Autowired
   public TransactionConfig(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Bean("transactionManager")
   public HibernateTransactionManager getTransactionManager(){
      HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
      hibernateTransactionManager.setSessionFactory(sessionFactory);

      return hibernateTransactionManager;
   }

   @Override
   public PlatformTransactionManager annotationDrivenTransactionManager() {
      return getTransactionManager();
   }

   private final SessionFactory sessionFactory;
}
