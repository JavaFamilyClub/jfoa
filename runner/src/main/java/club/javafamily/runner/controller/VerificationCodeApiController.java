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

import club.javafamily.runner.util.WebMvcUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api("Verification Code Api")
@RestController
public class VerificationCodeApiController {

   @ApiOperation("Getting Verification Code Image")
   @GetMapping("/public/verification/code")
   public void getVerificationCode(HttpServletResponse response) throws IOException {
      WebMvcUtil.writeCode(response);
   }

   @ApiOperation("Verify User Code")
   @GetMapping("/public/verification/code/verify/{code}")
   public boolean verifyCode(@ApiParam(value = "User Input Code", required = true)
                                @PathVariable("code") String code)
   {
      return WebMvcUtil.verifyCode(code);
   }

}
