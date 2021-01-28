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

package club.javafamily.runner.rest;

import club.javafamily.commons.enums.UserType;
import club.javafamily.runner.dto.RestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryEngineFactory {

   public QueryEngine<? extends RestUser> getQueryEngine(UserType userType) {
      for(QueryEngine<? extends RestUser> queryEngine : queryEngines) {
         if(queryEngine.isAccept(userType)) {
            return queryEngine;
         }
      }

      return null;
   }

   @Autowired
   public QueryEngineFactory(List<QueryEngine<? extends RestUser>> queryEngines) {
      this.queryEngines = queryEngines;
   }

   private final List<QueryEngine<? extends RestUser>> queryEngines;
}
