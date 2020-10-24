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

import { of } from "rxjs";

/**
 * Namespace that provides utility methods that are useful in developing unit tests.
 */
export namespace TestUtils {

   export function createModelService(): any {
      return {
         deleteModel: jest.fn(),
         getModel: jest.fn(() => of([]))
      };
   }

   export function createPrincipalService(): any {
      return {
         isAdmin: jest.fn(() => of(false)),
         isAdminUser: jest.fn(() => of(false))
      };
   }

   export function createTranslateService(): any {
      return {
         instant: jest.fn()
      };
   }

}
