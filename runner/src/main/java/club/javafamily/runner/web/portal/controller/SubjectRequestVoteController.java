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

import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION + "/public/")
public class SubjectRequestVoteController {

   @Autowired
   public SubjectRequestVoteController(CustomerService customerService, SubjectRequestService subjectRequestService) {
      this.customerService = customerService;
      this.subjectRequestService = subjectRequestService;
   }

   @PutMapping("/subject-request/vote/{id}/{add}")
   public void vote(@PathVariable("id") int id,
                    @PathVariable("add") boolean add)
   {
   }

   private final CustomerService customerService;
   private final SubjectRequestService subjectRequestService;
}
