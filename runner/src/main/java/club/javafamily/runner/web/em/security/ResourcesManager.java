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
import club.javafamily.runner.enums.ResourceTypeEnum;
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

      resourcesNodes.add(buildOperatorNode(rootPath, ResourceEnum.Customer));
      resourcesNodes.add(buildOperatorNode(rootPath, ResourceEnum.Installer));
      resourcesNodes.add(buildOperatorNode(rootPath, ResourceEnum.Role));

      return resourcesNodes;
   }

   private TreeNodeModel buildOperatorNode(String path, ResourceEnum resourceEnum) {
      String currPath = getPath(path, resourceEnum.name());

      return TreeNodeModel.build()
         .setLabel(resourceEnum.getLabel())
         .setValue(resourceEnum.getType() + "")
         .setPath(currPath)
         .setType(ResourceTypeEnum.RESOURCE.ordinal() + "");
   }

   private TreeNodeModel buildPortalNode(String path) {
      String portalPath = getPath(path, ResourceEnum.Portal.name());

      return buildPageNode(path, ResourceEnum.Portal)
         .setChildren(buildPortalChildren(portalPath))
         ;
   }

   private List<TreeNodeModel> buildPortalChildren(String path) {
      List<TreeNodeModel> children = new ArrayList<>();

      children.add(buildPageNode(path, ResourceEnum.MailAuthor));

      return children;
   }

   private TreeNodeModel buildPageNode(String path, ResourceEnum resourceEnum) {
      String currPath = getPath(path, resourceEnum.name());

      return TreeNodeModel.build()
         .setLabel(resourceEnum.getLabel())
         .setValue(resourceEnum.getType() + "")
         .setPath(currPath)
         .setType(ResourceTypeEnum.PAGE.ordinal() + "");
   }

   private String getPath(String basePath, String pathSegment) {
      return basePath != null && !basePath.endsWith("/")
         ? basePath + "/" + pathSegment
         : Objects.toString(basePath, "") + pathSegment;
   }

   private TreeNodeModel buildEMNode(String path) {
      String emPath = getPath(path, ResourceEnum.EM.name());

      return buildPageNode(path, ResourceEnum.EM)
         .setChildren(buildEMChildren(emPath));
   }

   private List<TreeNodeModel> buildEMChildren(String emPath) {
      List<TreeNodeModel> children = new ArrayList<>();

      children.add(buildPageNode(emPath, ResourceEnum.EM_Monitor));
      children.add(buildPageNode(emPath, ResourceEnum.Audit));
      children.add(buildPageNode(emPath, ResourceEnum.SubjectRequest));

      children.add(buildPageNode(emPath, ResourceEnum.EM_Setting));

      return children;
   }
}
