/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
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

package club.javafamily.runner.enums;

import club.javafamily.runner.util.I18nUtil;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum ExceptionEnum {

   /**
    * Login Exceptions.
    */
   SECURITY_UNKNOWN_ACCOUNT("0x001", "security.errorMsg.accountNotExist"),
   SECURITY_INCORRECT_CREDENTIALS("0x002", "user.pwd.warn.notMatch"),
   SECURITY_LOCKED_ACCOUNT("0x003", "security.errorMsg.accountLocked"),
   SECURITY_EXCESSIVE_ATTEMPTS("0x004", "security.errorMsg.manyAttempts"),
   SECURITY_AUTHENTICATION("0x005", "security.errorMsg.loginError"),
   SECURITY_OAUTH_AUTHENTICATION("0x006", "security.errorMsg.oAuth.authFailed"),

   MESSAGE_EXCEPTION("0x201", null),

   EXCEPTION("0x500", "Server Error");

   /**
    * 错误代码
    */
   private String code;

   /**
    * 用于提示用户的信息
    */
   private String message;

   ExceptionEnum(String code, String message) {
      this.code = code;
      this.message = message;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getMessage() {
      return message;
   }

   public String getLocaleMessage() {
      return I18nUtil.getString(message);
   }

   public String getLocaleMessage(ServletRequest request) {
      if(request instanceof HttpServletRequest) {
         Locale locale = RequestContextUtils.getLocale((HttpServletRequest) request);

         return I18nUtil.getString(message, locale);
      }

      return I18nUtil.getString(message);
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
