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

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.ResponseResult;
import club.javafamily.runner.enums.ExceptionEnum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletRequest;

@ControllerAdvice({ "club.javafamily.runner.controller", "club.javafamily.runner.web..*.controller" })
public class GlobalExceptionHandle extends BaseSecurityExceptionHandle<String>
   implements ExceptionHandle<String>
{

   @Override
   protected String buildAuthenticationResponse(Model model,
                                                ServletRequest request,
                                                ExceptionEnum exception)
   {
      ResponseResult<String> result = ResponseResult.build(
         exception.getCode(),
         exception.getLocaleMessage(request),
         request.getParameter("userName"));

      model.addAttribute("result", result);

      return "login";
   }

   @Override
   @ExceptionHandler(MessageException.class)
   public String messageException(MessageException exception, Model model) {
      model.addAttribute("message", exception.getMessage());

      return "error/exception";
   }

   @Override
   @ExceptionHandler(Exception.class)
   public String exceptionHandle(ServletRequest request) {
      return "error/exception";
   }
}
