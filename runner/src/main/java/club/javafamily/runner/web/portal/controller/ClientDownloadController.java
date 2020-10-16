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
import club.javafamily.runner.enums.Platform;
import club.javafamily.runner.service.InstallerService;
import club.javafamily.runner.util.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("Download Installer Api")
@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class ClientDownloadController {

   @Autowired
   public ClientDownloadController(InstallerService installerService) {
      this.installerService = installerService;
   }

   @ApiOperation(value = "Get Client Download Link", httpMethod = "GET")
   @GetMapping("/public/installer/download")
   public String download(@ApiParam(value = "platform", example = "1") @RequestParam("platform") int platform,
                          @ApiParam(value = "version", example = "latest") @RequestParam("version") String version)
   {
      Installer installer = installerService.getInstaller(Platform.parse(platform), version);

      if(installer == null) {
         throw new MessageException("Installer is not exist!");
      }

      return installer.getLink();
   }


   private final InstallerService installerService;
}
