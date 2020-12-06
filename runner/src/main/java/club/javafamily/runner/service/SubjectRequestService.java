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

package club.javafamily.runner.service;

import club.javafamily.runner.domain.SubjectRequest;

import java.util.List;

public interface SubjectRequestService extends TableLensSupport {

   /**
    * get all subject requests
    */
   List<SubjectRequest> getList();

   SubjectRequest get(Integer id);

   /**
    * insert a subject requests
    */
   Integer insert(SubjectRequest subjectRequest);

   /**
    * Update a subject request.
    */
   void update(SubjectRequest sr);

   /**
    * Delete a subject request
    */
   void delete(SubjectRequest sr);
   void delete(Integer id);

   void achieve(int id, String article);

}
