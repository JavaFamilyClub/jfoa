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

import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.WebMvcUtil;
import club.javafamily.runner.web.portal.service.SubjectVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION + "/public/")
public class SubjectRequestVoteController {

   @Autowired
   public SubjectRequestVoteController(SubjectVoteService voteService) {
      this.voteService = voteService;
   }

   @PutMapping("/subject-request/vote/{id}/{support}")
   public void vote(@PathVariable("id") int id,
                    @PathVariable("support") boolean support)
   {
      String ip = WebMvcUtil.getIP();
      this.voteService.changeVote(ip, id, support);
   }

   private final SubjectVoteService voteService;
}
