package club.javafamily.dfs;

import club.javafamily.dfs.config.FastDFSClient;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.File;

@SpringBootTest
@Disabled("this will push data to dfs. execute success -- test by @JackLi")
class DfsApplicationTests {

   @Autowired
   private FastDFSClient fastDFSClient;

   @Test
   public void testUpload() throws Exception {
      String fileUrl = this.getClass().getResource("/jfoa-QR.jpg").getPath();
      File file = new File(fileUrl);

      if(!file.exists()) {
         LOGGER.warn("Tests resource is not found: {}", file.getAbsolutePath());
      }

      String str = fastDFSClient.uploadFile(file);
      String url = fastDFSClient.getResAccessUrl(str);

      Assertions.assertTrue(StringUtils.hasText(url), "Upload failed!");

      LOGGER.info("Upload success, url is {} .", url);
   }

   @Test
   public void testDelete() throws Exception {
      boolean result = fastDFSClient.deleteFile(
         "group1/M00/00/00/wKgDPGA-W7iAH3-NAAAkMBvDwpo918.jpg");

      Assertions.assertTrue(result, "Delete file failed.");
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(DfsApplicationTests.class);
}
