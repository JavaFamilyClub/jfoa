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

export class EmUrlConstants {

   /**
    * Audit Export
    */
   public static readonly AUDIT_EXPORT = "/public/log/export";

   /**
    * Notify all
    */
   public static readonly NOTIFY_ALL = "/notification/all";

   /**
    * Search Action
    */
   public static readonly SEARCH = "/public/tool/search";

   /**
    * Upload Installer
    */
   public static readonly UPLOAD_INSTALLER = "/client/upload";

   /**
    * Getting installers
    */
   public static readonly INSTALLERS = "/clients";

   /**
    * Getting users
    */
   public static readonly USERS = "/users";

   /**
    * Getting users tree
    */
   public static readonly USERS_TREE = "/users/tree";

   /**
    * User api
    */
   public static readonly USER = "/user";

   /**
    * Role
    */
   public static readonly ROLE = "/role/";

   /**
    * Getting roles
    */
   public static readonly ROLES_TREE = "/roles/tree";

   /**
    * Getting subject request monitor model
    */
   public static readonly SUBJECT_REQUEST_MONITOR = "/subject-request/monitor";

   /**
    * Getting subject request chart model
    */
   public static readonly SUBJECT_REQUEST_CHART = "/subject-request/chart";

   /**
    * Getting subject request summary chart model
    */
   public static readonly SUBJECT_REQUEST_CHART_SUMMARY = "/subject-request/chart/summary";

   /**
    * Getting subject request support chart model
    */
   public static readonly SUBJECT_REQUEST_CHART_SUPPORT = "/subject-request/chart/support";

   /**
    * Getting security resources model
    */
   public static readonly SECURITY_RESOURCES = "/security/resources";

   /**
    * Getting security resources edit permission model
    */
   public static readonly SECURITY_RESOURCES_PERMISSION = "/security/resources/permission/";

   /**
    * Getting security resources edit permission tree model
    */
   public static readonly SECURITY_RESOURCES_PERMISSION_TREE = "/security/resources/permission/tree";

   /**
    * Getting em system monitor model
    */
   public static readonly MONITOR_SYSTEM_SUMMARY = "/em/monitor/system/summary";

   /**
    * Download thread dump
    */
   public static readonly MONITOR_SYSTEM_THREAD_DUMP = "/em/monitor/system/thread-dump";

   /**
    * Download heap dump
    */
   public static readonly MONITOR_SYSTEM_HEAP_DUMP = "/em/monitor/system/heap-dump";

   /**
    * Download heap dump
    */
   public static readonly MONITOR_SYSTEM_GC = "/em/monitor/system/gc";

   /**
    * Getting em system monitor heap chart model
    */
   public static readonly SYSTEM_SUMMARY_HEAP_CHART = "/em/monitor/system/summary/chart/heap";

   /**
    * Getting em system monitor thread chart model
    */
   public static readonly SYSTEM_SUMMARY_THREAD_CHART = "/em/monitor/system/summary/chart/thread";

   /**
    * Getting em system monitor disk memory chart model
    */
   public static readonly SYSTEM_SUMMARY_MEMORY_CHART = "/em/monitor/system/summary/chart/memory";

   /**
    * Getting em system monitor cpu chart model
    */
   public static readonly SYSTEM_SUMMARY_CPU_CHART = "/em/monitor/system/summary/chart/cpu";

   /**
    * Getting em system monitor jvm detail model
    */
   public static readonly MONITOR_JVM_DETAIL = "/em/monitor/system/jvm/detail";

}
