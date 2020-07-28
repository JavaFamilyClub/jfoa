package club.javafamily.runner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addRedirectViewController("/", "/app/index.html");
      registry.addViewController("/index.html").setViewName("index");
      registry.addViewController("/login.html").setViewName("login");
      registry.addViewController("/login").setViewName("login");
      registry.addViewController("/signupSuccess").setViewName("signupSuccess");
//      registry.addViewController("/signup").setViewName("signup");
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
}
