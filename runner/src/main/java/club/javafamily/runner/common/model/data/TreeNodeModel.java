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

package club.javafamily.runner.common.model.data;

import java.util.*;

public class TreeNodeModel {

   public static final String ROOT_PATH = "/";

   private String label;
   private String value;
   private List<TreeNodeModel> children = new ArrayList<>();
   private boolean leaf;
   private String tooltip;
   private boolean expanded;
   private Object type;
   private String path;
   private Object Data;

   public static TreeNodeModel build() {
      return new TreeNodeModel();
   }

   public String getLabel() {
      return label;
   }

   public TreeNodeModel setLabel(String label) {
      this.label = label;
      return this;
   }

   public String getValue() {
      return value;
   }

   public TreeNodeModel setValue(String value) {
      this.value = value;

      return this;
   }

   public List<TreeNodeModel> getChildren() {
      return Collections.unmodifiableList(children);
   }

   public TreeNodeModel setChildren(List<TreeNodeModel> children) {
      this.children = children;
      return this;
   }

   public boolean isLeaf() {
      return leaf || children == null || children.size() < 1;
   }

   public TreeNodeModel setLeaf(boolean leaf) {
      this.leaf = leaf;
      return this;
   }

   public String getTooltip() {
      return tooltip;
   }

   public TreeNodeModel setTooltip(String tooltip) {
      this.tooltip = tooltip;
      return this;
   }

   public boolean isExpanded() {
      return expanded;
   }

   public TreeNodeModel setExpanded(boolean expanded) {
      this.expanded = expanded;
      return this;
   }

   public Object getType() {
      return type;
   }

   public TreeNodeModel setType(Object type) {
      this.type = type;
      return this;
   }

   public String getPath() {
      return path;
   }

   public TreeNodeModel setPath(String path) {
      this.path = path;
      return this;
   }

   public Object getData() {
      return Data;
   }

   public TreeNodeModel setData(Object data) {
      Data = data;
      return this;
   }

   @Override
   public String toString() {
      return "TreeNodeModel{" +
         "label='" + label + '\'' +
         ", value='" + value + '\'' +
         ", children=" + children +
         ", leaf=" + leaf +
         ", tooltip='" + tooltip + '\'' +
         ", expanded=" + expanded +
         ", type='" + type + '\'' +
         ", path='" + path + '\'' +
         ", Data=" + Data +
         '}';
   }
}
