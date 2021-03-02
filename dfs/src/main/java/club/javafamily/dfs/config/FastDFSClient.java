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

package club.javafamily.dfs.config;

import com.github.tobato.fastdfs.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
@SuppressWarnings("unused")
public class FastDFSClient {
   private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSClient.class);

   private final FastFileStorageClient fastFileStorageClient;

   private final FdfsWebServer fdfsWebServer;

   @Autowired
   public FastDFSClient(FastFileStorageClient fastFileStorageClient,
                        FdfsWebServer fdfsWebServer)
   {
      this.fastFileStorageClient = fastFileStorageClient;
      this.fdfsWebServer = fdfsWebServer;
   }

   /**
    * @param multipartFile 文件对象
    * @return 返回文件地址
    */
   public String uploadFile(MultipartFile multipartFile) {
      try {
         StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
         return storePath.getFullPath();
      } catch (IOException e) {
         LOGGER.error(e.getMessage());
         return null;
      }
   }

   /**
    * 上传缩略图
    * @param multipartFile 图片对象
    * @return 返回图片地址
    */
   public String uploadImageAndCrtThumbImage(MultipartFile multipartFile) throws IOException {
      try {
         StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
            multipartFile.getInputStream(),
            multipartFile.getSize(),
            FilenameUtils.getExtension(multipartFile.getOriginalFilename()),
            null);

         return storePath.getFullPath();
      }
      catch (Exception e) {
         throw e;
      }
   }

   /**
    * @param file 文件对象
    * @return 返回文件地址
    */
   public String uploadFile(File file) throws Exception {
      try {
         FileInputStream inputStream = new FileInputStream(file);
         StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
         return storePath.getFullPath();
      } catch (Exception e) {
         throw e;
      }
   }

   /**
    * 上传缩略图
    * @param file 图片对象
    * @return 返回图片地址
    */
   public String uploadImageAndCrtThumbImage(File file) throws FileNotFoundException {
      try {
         FileInputStream inputStream = new FileInputStream(file);
         StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);

         return storePath.getFullPath();
      }
      catch (Exception e) {
         throw e;
      }
   }

   /**
    * 将byte数组生成一个文件上传
    * @param bytes byte数组
    * @param fileExtension 文件扩展名
    * @return 返回文件地址
    */
   public String uploadFile(byte[] bytes, String fileExtension) {
      ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
      StorePath storePath = fastFileStorageClient.uploadFile(stream, bytes.length, fileExtension, null);
      return storePath.getFullPath();
   }

   /**
    * @param fileUrl 文件访问地址
    * @param file 文件保存路径
    */
   public boolean downloadFile(String fileUrl, File file) throws IOException {
      try {
         StorePath storePath = StorePath.praseFromUrl(fileUrl);
         byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
         FileOutputStream stream = new FileOutputStream(file);
         stream.write(bytes);
      }
      catch (Exception e) {
         throw e;
      }
      return true;
   }

   /**
    * @param fileUrl 文件访问地址
    */
   public boolean deleteFile(String fileUrl) throws Exception {
      if (StringUtils.isEmpty(fileUrl)) {
         return false;
      }

      StorePath storePath = StorePath.praseFromUrl(fileUrl);
      fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());

      return true;
   }

   // 封装文件完整URL地址
   public String getResAccessUrl(String path) {
      String url = fdfsWebServer.getWebServerUrl() + path;
      LOGGER.info("Upload file url is：\n" + url);

      return url;
   }
}
