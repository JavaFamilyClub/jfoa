package club.javafamily.runner.config;

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.ResponseResult;
import club.javafamily.runner.enums.ExceptionEnum;
import org.apache.shiro.authc.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@ControllerAdvice({ "club.javafamily.runner.controller", "club.javafamily.runner.web..*.controller" })
public class ExceptionHandle {

   @ExceptionHandler(UnknownAccountException.class)
   public String unknownAccountExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_UNKNOWN_ACCOUNT);
   }

   @ExceptionHandler(IncorrectCredentialsException.class)
   public String incorrectCredentialsExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_INCORRECT_CREDENTIALS);
   }

   @ExceptionHandler(LockedAccountException.class)
   public String lockedAccountExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_LOCKED_ACCOUNT);
   }

   @ExceptionHandler(ExcessiveAttemptsException.class)
   public String excessiveAttemptsExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_EXCESSIVE_ATTEMPTS);
   }

   @ExceptionHandler(AuthenticationException.class)
   public String authenticationExceptionHandle(Model model, ServletRequest request) {
      return buildAuthenticationResponse(model, request,
         ExceptionEnum.SECURITY_AUTHENTICATION);
   }

   private String buildAuthenticationResponse(Model model, ServletRequest request, ExceptionEnum exception) {
      ResponseResult<String> result = ResponseResult.build(
         exception.getCode(),
         exception.getMessage(),
         request.getParameter("userName"));

      model.addAttribute("result", result);

      return "login";
   }

   @ExceptionHandler(MessageException.class)
   public String messageException(MessageException exception, Model model) {
      model.addAttribute("message", exception.getMessage());

      return "error/exception";
   }

   @ExceptionHandler(Exception.class)
   public String arithmeticExceptionHandle() {
      return "error/exception";
   }
}
