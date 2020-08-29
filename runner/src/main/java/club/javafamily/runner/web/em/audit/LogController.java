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

package club.javafamily.runner.web.em.audit;

import club.javafamily.runner.domain.Log;
import club.javafamily.runner.service.LogService;
import club.javafamily.runner.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class LogController {

//   @RequiresPermissions({ "*:2:a", "*:65:r" })
   @PutMapping("/logs")
   public List<Log> getAll() {
      List<Log> logs = logService.getAll();
      // reverse logs. because we more focus in recent logs.
      Collections.reverse(logs);

      return logs;
   }

   @Autowired
   public LogController(LogService logService) {
      this.logService = logService;
   }

   private final LogService logService;
}
