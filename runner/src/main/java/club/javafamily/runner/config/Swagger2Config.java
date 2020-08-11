package club.javafamily.runner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {

   @Bean
   public Docket createRestApi() {
      return new Docket(DocumentationType.SWAGGER_2)
         .apiInfo(apiInfo())
         .select()
         .paths(PathSelectors.any())
         .build();
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
         .title("JavaFamily OA APIs")
         .version("1.0")
         .description("JavaFamily OA RESTful APIs")
         .build();
   }
}
