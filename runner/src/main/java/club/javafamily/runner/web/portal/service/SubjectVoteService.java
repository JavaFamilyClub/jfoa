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

package club.javafamily.runner.web.portal.service;

import club.javafamily.runner.service.SubjectRequestService;
import org.springframework.stereotype.Service;

@Service
public class SubjectVoteService {

   public SubjectVoteService(SubjectRequestService subjectRequestService) {
      this.subjectRequestService = subjectRequestService;
   }

   public void changeVote(int id, boolean incr) {

   }

   private final SubjectRequestService subjectRequestService;
}
