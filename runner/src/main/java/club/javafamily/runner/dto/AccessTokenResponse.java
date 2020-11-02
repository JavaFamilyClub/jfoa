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

package club.javafamily.runner.dto;

import java.io.Serializable;

public class AccessTokenResponse implements Serializable {
   private String access_token;
   private String token_type;

   public String getAccess_token() {
      return access_token;
   }

   public void setAccess_token(String access_token) {
      this.access_token = access_token;
   }

   public String getToken_type() {
      return token_type;
   }

   public void setToken_type(String token_type) {
      this.token_type = token_type;
   }

   @Override
   public String toString() {
      return "AccessTokenResponse{" +
         "access_token='" + access_token + '\'' +
         ", token_type='" + token_type + '\'' +
         '}';
   }
}
