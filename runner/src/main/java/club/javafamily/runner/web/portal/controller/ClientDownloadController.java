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

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.domain.Installer;
import club.javafamily.runner.util.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class ClientDownloadController {

   @PostMapping("/installer/download")
   public void download(@RequestBody Installer installer,
                        HttpServletResponse response)
      throws Exception
   {
      File downloadFile = Tool.readInstallerFile(installer);

      if(!downloadFile.exists()) {
         throw new MessageException("File is not exist!");
      }

      String fileName = installer.getFileName();
      ExportUtil.writeDownloadHeader(response, fileName);

      ServletOutputStream out = response.getOutputStream();

      try(FileInputStream in = new FileInputStream(downloadFile)) {
         StreamUtils.copy(in, out);
      }
   }

}
