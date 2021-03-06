/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

package club.javafamily.runner.rest.github;

import club.javafamily.runner.rest.dto.AccessTokenDTO;

public class GitHubAccessTokenDTO extends AccessTokenDTO {
   private String client_id;
   private String client_secret;
   private String redirect_uri;

   public String getClient_id() {
      return client_id;
   }

   public void setClient_id(String client_id) {
      this.client_id = client_id;
   }

   public String getClient_secret() {
      return client_secret;
   }

   public void setClient_secret(String client_secret) {
      this.client_secret = client_secret;
   }

   public String getRedirect_uri() {
      return redirect_uri;
   }

   public void setRedirect_uri(String redirect_uri) {
      this.redirect_uri = redirect_uri;
   }

   @Override
   public String toString() {
      return "AccessTokenDTO{" +
         "client_id='" + client_id + '\'' +
         ", client_secret='" + client_secret + '\'' +
         ", redirect_uri='" + redirect_uri + '\'' +
         '}';
   }
}
