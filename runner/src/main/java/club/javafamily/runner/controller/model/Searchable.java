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

package club.javafamily.runner.controller.model;

import java.io.Serializable;
import java.util.Arrays;

public class Searchable implements Serializable {

   public String getRoute() {
      return route;
   }

   public void setRoute(String route) {
      this.route = route;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String[] getKeywords() {
      return keywords;
   }

   public void setKeywords(String[] keywords) {
      this.keywords = keywords;
   }

   @Override
   public String toString() {
      return "Searchable{" +
         "route='" + route + '\'' +
         ", title='" + title + '\'' +
         ", keywords=" + Arrays.toString(keywords) +
         '}';
   }

   private String route;
   private String title;
   private String[] keywords;
}
