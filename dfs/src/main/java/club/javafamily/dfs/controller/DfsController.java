/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

package club.javafamily.dfs.controller;

import club.javafamily.commons.utils.CoreSecurityUtil;
import club.javafamily.dfs.config.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(CoreSecurityUtil.API_VERSION)
public class DfsController {

   @GetMapping("/public/dfs/img/{imgPath}")
   public void getImage(@PathVariable("imgPath") String imgPath,
                        HttpServletResponse response) throws IOException
   {
      ServletOutputStream out = response.getOutputStream();
      this.fastDFSClient.downloadFile(imgPath, out);
   }

   @Autowired
   public DfsController(FastDFSClient fastDFSClient) {
      this.fastDFSClient = fastDFSClient;
   }

   private final FastDFSClient fastDFSClient;
}
