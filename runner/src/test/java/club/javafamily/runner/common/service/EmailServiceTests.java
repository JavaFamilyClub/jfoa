/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.common.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Disabled("Tested success.")
public class EmailServiceTests {

   @Autowired
   private EmailService mailService;

   private static final String TO = "18829346477@163.com";
   private static final String SUBJECT = "JFOA Test Email";
   private static final String CONTENT = "test content";

   /**
    * 测试发送普通邮件
    */
   @Test
   public void sendSimpleMailMessage() {
      mailService.sendSimpleMailMessage(TO, SUBJECT, CONTENT);
   }

   /**
    * 测试发送html邮件
    */
   @Test
   public void sendHtmlMessage() throws MessagingException {
      String htmlStr = "<h1>Test</h1>";
      mailService.sendMimeMessage(TO, SUBJECT, htmlStr);
   }

   /**
    * 测试发送带附件的邮件
    * @throws FileNotFoundException
    */
   @Test
   public void sendAttachmentMessage() throws Exception {
      File file = ResourceUtils.getFile("classpath:banner.txt");
      String filePath = file.getAbsolutePath();
      mailService.sendMimeMessage(TO, SUBJECT, CONTENT, filePath);
   }

   /**
    * 测试发送带附件的邮件
    * @throws FileNotFoundException
    */
   @Test
   public void sendPicMessage() throws Exception {
      String htmlStr = "<html><body>Test：Image1 <br> <img src=\'cid:pic1\'/> <br>Img2 <br> <img src=\'cid:pic2\'/></body></html>";
      Map<String, String> rscIdMap = new HashMap<>(2);
      rscIdMap.put("pic1", ResourceUtils.getFile("classpath:public/jfoa-icon.ico").getAbsolutePath());
      rscIdMap.put("pic2", ResourceUtils.getFile("classpath:public/jfoa-icon.ico").getAbsolutePath());
      mailService.sendMimeMessage(TO, SUBJECT, htmlStr, rscIdMap);
   }
}
