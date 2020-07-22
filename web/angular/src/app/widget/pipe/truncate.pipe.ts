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

import { PipeTransform, Pipe } from "@angular/core";

@Pipe({
   name: "truncate"
})
export class TruncatePipe implements PipeTransform {
   transform(value: string, limit: string, trail: string): string {
      const limit2 = limit ? parseInt(limit, 10) : 100;
      let trail2 = trail ? trail : "...";

      return value.length > limit2 ? value.substring(0, limit2) + trail2 : value;
   }
}
