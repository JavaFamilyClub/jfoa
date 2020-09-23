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

import { Tool } from "../../util/tool";

export class InstallerClientUrlConstants {

   /**
    * Base href
    */
   public static readonly BASE_HREF = Tool.INSTALLER_API_VERSION;

   /**
    * Client Base href
    */
   public static readonly CLIENT_BASE_HREF = Tool.INSTALLER_URI + Tool.INSTALLER_CLIENT_API_VERSION;

   /**
    * sign in
    */
   public static readonly LOGIN_URI = "/login";

   /**
    * sign up
    */
   public static readonly SIGN_UP_URI = "/public/signup";

   /**
    * Installer download
    */
   public static readonly CLIENT_DOWNLOAD = "/public/installer/download";
}
