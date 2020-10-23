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

package club.javafamily.runner.controller;

import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.Tool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
@Api("Help Context")
public class HelpContextController {

   @ApiOperation(
      value = "Getting help url",
      httpMethod = "GET",
      response = String.class
   )
   @GetMapping("/public/help-url")
   public String getHelpUrl() {
      return Tool.USER_DOCUMENT_URI;
   }
}
