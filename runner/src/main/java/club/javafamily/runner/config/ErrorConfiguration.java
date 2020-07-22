/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.config;

import org.springframework.boot.web.server.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfiguration implements ErrorPageRegistrar {

   @Override
   public void registerErrorPages(ErrorPageRegistry registry) {
      ErrorPage[] errorPages = new ErrorPage[1];
      // Angular need server redirect 404 to index of angular.
      errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/app/index.html");

      registry.addErrorPages(errorPages);
   }
}
