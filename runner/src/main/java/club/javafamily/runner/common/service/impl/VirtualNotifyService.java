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

package club.javafamily.runner.common.service.impl;

import club.javafamily.runner.common.service.NotifyService;
import club.javafamily.runner.domain.Customer;

import javax.servlet.http.HttpServletRequest;

public class VirtualNotifyService implements NotifyService {
   @Override
   public void changePasswordNotify(Customer user, HttpServletRequest request) {
      // no op
   }

   @Override
   public boolean accept(Customer user) {
      return false;
   }
}
