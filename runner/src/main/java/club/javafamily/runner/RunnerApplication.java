package club.javafamily.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {
   JpaRepositoriesAutoConfiguration.class,
   HibernateJpaAutoConfiguration.class
})
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@EnableSwagger2
public class RunnerApplication {

   public static void main(String[] args) {
      SpringApplication.run(RunnerApplication.class, args);
   }

}
