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

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.WebMvcUtil;
import club.javafamily.runner.web.portal.model.CreateSubjectModel;
import club.javafamily.runner.web.portal.model.ListSubjectModel;
import club.javafamily.runner.web.portal.service.SubjectVoteHandle;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SubjectRequestController {

   @Autowired
   public SubjectRequestController(SubjectVoteHandle voteHandle, CustomerService customerService,
                                   SubjectRequestService subjectRequestService,
                                   SubjectVoteService voteService)
   {
      this.voteHandle = voteHandle;
      this.customerService = customerService;
      this.subjectRequestService = subjectRequestService;
      this.voteService = voteService;
   }

   @GetMapping("/public/subject-request/list")
   public ListSubjectModel getSubjectRequestList() {
      final String ip = WebMvcUtil.getIP();
      ListSubjectModel listSubjectModel = voteHandle.getListSubjectModel(ip);

      return listSubjectModel;
   }

   @PostMapping("/subject-request")
   public void createSubjectRequest(@RequestBody CreateSubjectModel model) {
      Customer customer = customerService.getCurrentCustomer();
      SubjectRequest subjectRequest = model.convertToDomain(customer);
      subjectRequestService.insert(subjectRequest);
   }

   private final SubjectVoteHandle voteHandle;
   private final CustomerService customerService;
   private final SubjectRequestService subjectRequestService;
   private final SubjectVoteService voteService;
}
