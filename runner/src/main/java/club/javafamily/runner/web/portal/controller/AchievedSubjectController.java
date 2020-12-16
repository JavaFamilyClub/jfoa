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

package club.javafamily.runner.web.portal.controller;

import club.javafamily.runner.domain.ArchivedSubject;
import club.javafamily.runner.service.ArchivedSubjectService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.model.ArchivedSubjectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class AchievedSubjectController {

   @GetMapping("/public/achieve/sr/article/{srId}")
   public ArchivedSubjectModel getAchievedSubjectBySr(@PathVariable("srId") int srId) {
      ArchivedSubject archivedSubject = archivedService.get(srId);

      if(archivedSubject == null) {
         return null;
      }

      return new ArchivedSubjectModel(archivedSubject);
   }

   @Autowired
   public AchievedSubjectController(ArchivedSubjectService archivedService) {
      this.archivedService = archivedService;
   }

   private final ArchivedSubjectService archivedService;

}
