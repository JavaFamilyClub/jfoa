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

import club.javafamily.runner.enums.ExceptionEnum;
import org.apache.shiro.authc.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletRequest;

public abstract class BaseSecurityExceptionHandle <T> {

   @ExceptionHandler(UnknownAccountException.class)
   public T unknownAccountExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_UNKNOWN_ACCOUNT);
   }

   @ExceptionHandler(IncorrectCredentialsException.class)
   public T incorrectCredentialsExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_INCORRECT_CREDENTIALS);
   }

   @ExceptionHandler(LockedAccountException.class)
   public T lockedAccountExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_LOCKED_ACCOUNT);
   }

   @ExceptionHandler(ExcessiveAttemptsException.class)
   public T excessiveAttemptsExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_EXCESSIVE_ATTEMPTS);
   }

   @ExceptionHandler(AuthenticationException.class)
   public T authenticationExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_AUTHENTICATION);
   }

   protected abstract T buildAuthenticationResponse(Model model,
                                                    ServletRequest request,
                                                    ExceptionEnum securityAuthentication);
}
