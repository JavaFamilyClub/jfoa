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

package club.javafamily.runner.util;

import org.springframework.web.context.request.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class WebMvcUtil {

   private static final String UNKNOWN = "unknown";
   private static final String LOCALHOST = "127.0.0.1";
   private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
   private static final String SEPARATOR = ",";

   public static boolean isLocalhost(String ip) {
      return LOCALHOST.equals(ip) || LOCALHOST_IPV6.equals(ip);
   }

   public static HttpServletRequest getRequest() {
      return ((ServletRequestAttributes) getRequestAttributes()).getRequest();
   }

   public static RequestAttributes getRequestAttributes() {
      return RequestContextHolder.currentRequestAttributes();
   }

   public static String getIP() {
      HttpServletRequest request = getRequest();
      String ipAddress;

      try {
         ipAddress = request.getHeader("x-forwarded-for");
         if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
         }
         if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
         }
         if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            if(isLocalhost(ipAddress)) {
               InetAddress inet = null;

               try {
                  inet = InetAddress.getLocalHost();
                  ipAddress = inet.getHostAddress();
               }
               catch(UnknownHostException e) {
                  e.printStackTrace();
               }
            }
         }
         // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
         // "***.***.***.***".length()
         if(ipAddress != null && ipAddress.length() > 15) {
            if(ipAddress.indexOf(SEPARATOR) > 0) {
               ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
         }
      }
      catch(Exception e) {
         ipAddress = UNKNOWN;
      }

      return ipAddress;
   }
}
