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

package club.javafamily.runner.web.em.settings.installer;

import club.javafamily.runner.domain.Installer;
import club.javafamily.runner.service.InstallerService;
import club.javafamily.runner.util.SecurityUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
public class InstallerController {

   @Autowired
   public InstallerController(InstallerService installerService) {
      this.installerService = installerService;
   }

//   /**
//    * TODO: require upload permission.
//    */
//   @Deprecated
//   @RequiresAuthentication
//   @PostMapping("/client/upload")
//   public void uploadClient(@RequestBody ClientUploadModel model) throws Exception {
//      Installer installer = model.getInstaller();
//      File file = Tool.getInstallerFile(installer);
//
//      try(OutputStream output = new FileOutputStream(file)) {
//         ByteArrayInputStream input = new ByteArrayInputStream(
//            Base64.getDecoder().decode(model.getFileData().getContent()));
//         FileCopyUtils.copy(input, output);
//      }
//      catch(Exception e) {
//         if(LOGGER.isDebugEnabled()) {
//            LOGGER.debug("Installer upload failed.", e);
//         }
//
//         throw new MessageException("Installer upload failed.");
//      }
//
//      installerService.save(installer);
//   }

   /**
    * TODO: require upload permission.
    */
   @RequiresAuthentication
   @PostMapping("/client/upload")
   public void uploadClient(@RequestBody Installer installer) throws Exception {
      this.installerService.save(installer);
   }

   @RequiresAuthentication
   @GetMapping("/clients")
   public List<Installer> getClients() {
      List<Installer> list = installerService.getAll();

      return list;
   }

   private final InstallerService installerService;

   private static final Logger LOGGER = LoggerFactory.getLogger(InstallerController.class);
}
