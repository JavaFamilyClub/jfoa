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
