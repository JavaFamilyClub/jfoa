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

import club.javafamily.runner.common.service.EmailService;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.model.MailAuthorModel;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static club.javafamily.runner.util.SecurityUtil.Author_Email;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class MailAuthorController {

   @RequiresAuthentication
   @PostMapping("/mail-author")
   public void mailAuthor(@RequestBody MailAuthorModel model)
      throws Exception
   {
      emailService.sendMimeMessage(Author_Email, model.getSubject(),
         model.getContent());
   }

   @Autowired
   public MailAuthorController(EmailService emailService) {
      this.emailService = emailService;
   }

   private final EmailService emailService;
}
