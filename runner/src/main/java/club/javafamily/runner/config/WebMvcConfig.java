package club.javafamily.runner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      LOGGER.info("Registry web resource in: " + webDist);
      registry.addResourceHandler("/app/**").addResourceLocations("file:" + webDist + "/app/");
   }

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/index.html").setViewName("index");
      registry.addViewController("/login.html").setViewName("login");
   }

   @Value("${jfoa.web.dist}")
   private String webDist;

   private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
}
