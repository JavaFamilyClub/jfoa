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

package club.javafamily.runner.web.em.security;

import club.javafamily.runner.common.model.data.TreeNodeModel;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.util.I18nUtil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ResourcesManager {

   public TreeNodeModel getResourcesTree() {
      String rootPath = "/";
      List<TreeNodeModel> children = new ArrayList<>();

      children.add(buildPortalNode(rootPath));
      children.add(buildEMNode(rootPath));

      children.addAll(buildResourceOperatorNode(rootPath));

      return TreeNodeModel.build()
         .setLabel(I18nUtil.getString("em.security.tabs.resources"))
         .setValue("*")
         .setPath(rootPath)
         .setChildren(children)
         ;
   }

   private List<TreeNodeModel> buildResourceOperatorNode(String rootPath) {
      List<TreeNodeModel> resourcesNodes = new ArrayList<>();

      resourcesNodes.add(buildNode(rootPath, ResourceEnum.Customer));
      resourcesNodes.add(buildNode(rootPath, ResourceEnum.Installer));
      resourcesNodes.add(buildNode(rootPath, ResourceEnum.Role));
      resourcesNodes.add(buildNode(rootPath, ResourceEnum.Subject_Request));

      return resourcesNodes;
   }

   private TreeNodeModel buildPortalNode(String path) {
      String portalPath = getPath(path, ResourceEnum.PAGE_Portal.name());

      return buildNode(path, ResourceEnum.PAGE_Portal)
         .setChildren(buildPortalChildren(portalPath))
         ;
   }

   private List<TreeNodeModel> buildPortalChildren(String path) {
      List<TreeNodeModel> children = new ArrayList<>();

      children.add(buildNode(path, ResourceEnum.PAGE_MailAuthor));

      return children;
   }

   private TreeNodeModel buildNode(String path, ResourceEnum resourceEnum) {
      String currPath = getPath(path, resourceEnum.name());

      return TreeNodeModel.build()
         .setLabel(resourceEnum.getLabel())
         .setValue(resourceEnum.getId() + "")
         .setPath(currPath);
   }

   private String getPath(String basePath, String pathSegment) {
      return basePath != null && !basePath.endsWith("/")
         ? basePath + "/" + pathSegment
         : Objects.toString(basePath, "") + pathSegment;
   }

   private TreeNodeModel buildEMNode(String path) {
      String emPath = getPath(path, ResourceEnum.PAGE_EM.name());

      return buildNode(path, ResourceEnum.PAGE_EM)
         .setChildren(buildEMChildren(emPath));
   }

   private List<TreeNodeModel> buildEMChildren(String emPath) {
      List<TreeNodeModel> children = new ArrayList<>();

      children.add(buildNode(emPath, ResourceEnum.PAGE_EM_Monitor));
      children.add(buildNode(emPath, ResourceEnum.PAGE_Audit));
      children.add(buildNode(emPath, ResourceEnum.PAGE_Subject_Request));

      children.add(buildNode(emPath, ResourceEnum.PAGE_EM_Setting));
      children.add(buildNode(emPath, ResourceEnum.PAGE_Installer));

      return children;
   }
}
