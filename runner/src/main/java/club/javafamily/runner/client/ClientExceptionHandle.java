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

package club.javafamily.runner.client;

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.ResponseResult;
import club.javafamily.runner.config.BaseSecurityExceptionHandle;
import club.javafamily.runner.config.ExceptionHandle;
import club.javafamily.runner.enums.ExceptionEnum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletRequest;

import static club.javafamily.runner.enums.ExceptionEnum.EXCEPTION;
import static club.javafamily.runner.enums.ExceptionEnum.MESSAGE_EXCEPTION;

@RestControllerAdvice("club.javafamily.runner.client")
public class ClientExceptionHandle
   extends BaseSecurityExceptionHandle<ResponseResult<String>>
   implements ExceptionHandle<ResponseResult<String>>
{

   @Override
   protected ResponseResult<String> buildAuthenticationResponse(
      Model model,
      ServletRequest request,
      ExceptionEnum exception)
   {
      return ResponseResult.build(
         exception.getCode(),
         exception.getLocaleMessage(request));
   }

   @ExceptionHandler(MessageException.class)
   public ResponseResult<String> messageException(MessageException exception,
                                                  Model model) {
     return ResponseResult.build(MESSAGE_EXCEPTION.getCode(), exception.getMessage());
   }

   @ExceptionHandler(Exception.class)
   public ResponseResult<String> exceptionHandle(ServletRequest request) {
      return ResponseResult.build(EXCEPTION.getCode(),
         EXCEPTION.getLocaleMessage(request));
   }
}
