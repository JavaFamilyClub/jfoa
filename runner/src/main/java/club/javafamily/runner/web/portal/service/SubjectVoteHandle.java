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

import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.domain.SubjectRequest;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.service.SubjectRequestService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.web.portal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SubjectVoteHandle {

   /**
    * Getting subject request list pane model
    */
   public ListSubjectModel getListSubjectModel(final String ip) {
      ListSubjectModel listSubjectModel = new ListSubjectModel();
      List<SubjectRequestVO> list = getFullSubjectList(ip);
      listSubjectModel.setSubjects(list);

      return listSubjectModel;
   }

   private List<SubjectRequestVO> getFullSubjectList(final String ip) {
      List<SubjectRequest> list = subjectRequestService.getList();

      List<SubjectRequestVO> subjectVoList = list.stream()
         .map(subjectRequest -> this.convertSubjectVo(subjectRequest, ip))
         .collect(Collectors.toList());

      return subjectVoList;
   }

   private SubjectRequestVO convertSubjectVo(SubjectRequest subjectRequest,
                                             String ip)
   {
      Customer currUser = customerService.getCurrentCustomer();
      SubjectRequestVO vo = new SubjectRequestVO();
      Integer id = subjectRequest.getId();

      SubjectRequestVoteDto voteVO = voteService.getSubjectVoteDto(ip, id);
      Customer srUser = subjectRequest.getCustomer();
      boolean deletable = !subjectRequest.isArchived()
         && (Objects.equals(getUserAccount(currUser), getUserAccount(srUser))
         || SecurityUtil.isAdmin(currUser));
      boolean canArchive = SecurityUtil.isAdmin(currUser);

      vo.setId(id);
      vo.setCreateDate(subjectRequest.getCreateDate());
      vo.setCreateUserName(srUser.getName());
      vo.setDescription(subjectRequest.getDescription());
      vo.setSubject(subjectRequest.getSubject());
      vo.setVote(voteVO);
      vo.setDeletable(deletable);
      vo.setCanArchive(canArchive);
      vo.setArchived(subjectRequest.isArchived());

      return vo;
   }

   private String getUserAccount(Customer user) {
      return user != null ? user.getAccount() : null;
   }


   @Autowired
   public SubjectVoteHandle(CustomerService customerService,
                            SubjectVoteService voteService,
                            SubjectRequestService subjectRequestService)
   {
      this.voteService = voteService;
      this.customerService = customerService;
      this.subjectRequestService = subjectRequestService;
   }

   private final CustomerService customerService;
   private final SubjectRequestService subjectRequestService;
   private final SubjectVoteService voteService;
}
