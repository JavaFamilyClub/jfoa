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
import club.javafamily.runner.web.portal.model.CreateSubjectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class SubjectRequestController {

   @Autowired
   public SubjectRequestController(CustomerService customerService, SubjectRequestService subjectRequestService) {
      this.customerService = customerService;
      this.subjectRequestService = subjectRequestService;
   }

   @GetMapping("/public/subject-request/list")
   public List<SubjectRequest> getSubjectRequestList() {
      List<SubjectRequest> list = subjectRequestService.getList();

      return list;
   }

   @PostMapping("/subject-request")
   public void createSubjectRequest(@RequestBody CreateSubjectModel model) {
      Customer customer = customerService.getCurrentCustomer();
      SubjectRequest subjectRequest = model.convertToDomain(customer);
      subjectRequestService.insert(subjectRequest);
   }

   private final CustomerService customerService;
   private final SubjectRequestService subjectRequestService;
}
