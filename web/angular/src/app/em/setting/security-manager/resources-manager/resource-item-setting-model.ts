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

import { ResourceSettingType } from "../../../../common/enum/resource-setting-type";

export interface ResourceItemSettingModel {
   id?: number;
   roleId: number;
   type: ResourceSettingType;
   name: string;
   read: boolean;
   write: boolean;
   delete: boolean;
   access: boolean;
}
