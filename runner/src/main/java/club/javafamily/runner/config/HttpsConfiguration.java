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

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.*;

@Configuration
@Profile("prod")
public class HttpsConfiguration {

   /**
    * it's for set http url auto change to https
    */
   @Bean
   public TomcatServletWebServerFactory servletContainer(){
      TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
         @Override
         protected void postProcessContext(Context context) {
            SecurityConstraint securityConstraint=new SecurityConstraint();
            securityConstraint.setUserConstraint("CONFIDENTIAL"); //confidential
            SecurityCollection collection=new SecurityCollection();
            collection.addPattern("/*");
            securityConstraint.addCollection(collection);
            context.addConstraint(securityConstraint);
         }
      };
      tomcat.addAdditionalTomcatConnectors(httpConnector());

      return tomcat;
   }

   @Bean
   public Connector httpConnector(){
      Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
      connector.setScheme("http");
      connector.setPort(80);
      connector.setSecure(false);

      LOGGER.info("###### Change Http({}) to Https({}). ######", 80, serverPort);

      connector.setRedirectPort(serverPort);

      return connector;
   }

   @Value("${server.port}")
   private int serverPort;

   private static final Logger LOGGER = LoggerFactory.getLogger(HttpsConfiguration.class);
}
