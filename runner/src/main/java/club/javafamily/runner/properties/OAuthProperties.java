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

package club.javafamily.runner.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "jfoa.oauth")
public class OAuthProperties {

   private String callback;

   @NestedConfigurationProperty
   private GithubOAuthProperties github = new GithubOAuthProperties();

   public String getCallback() {
      return callback;
   }

   public void setCallback(String callback) {
      this.callback = callback;
   }

   public GithubOAuthProperties getGithub() {
      return github;
   }

   public void setGithub(GithubOAuthProperties github) {
      this.github = github;
   }

   public static class GithubOAuthProperties {
      private String clientId;
      private String clientSecrets;

      public String getClientId() {
         return clientId;
      }

      public void setClientId(String clientId) {
         this.clientId = clientId;
      }

      public String getClientSecrets() {
         return clientSecrets;
      }

      public void setClientSecrets(String clientSecrets) {
         this.clientSecrets = clientSecrets;
      }
   }
}
