package club.javafamily.runner.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.*;

@Configuration
@ConditionalOnClass(DruidDataSource.class)
public class DruidConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "ds.druid")
    public DataSource dataSource() {
       DruidDataSource druid = new DruidDataSource();

       return druid;
    }

    // 1. Config servlet of Druid
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
           new StatViewServlet(), "/druid/*");

        Map<String, String> initParams = new HashMap<>(10);
        initParams.put("loginUsername", "jfoa");
        initParams.put("loginPassword", "jfoa");
        initParams.put("deny", "localhost"); // will pop up parse ip error.
        initParams.put("allow", "127.0.0.1");

        bean.setInitParameters(initParams);

        return bean;
    }

    // 2. Config filter of Druid
    @Bean
    public FilterRegistrationBean<WebStatFilter> statViewFilter() {
        FilterRegistrationBean<WebStatFilter> bean =
           new FilterRegistrationBean(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList("/*"));

        Map<String, String> initParams = new HashMap<>(10);

        // Don't intercept static resource.
        initParams.put("exclusions", "*.js, *.css, *.html, /druid/*");

        bean.setInitParameters(initParams);

        return bean;
    }
}
