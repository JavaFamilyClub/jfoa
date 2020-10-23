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

/**
 * Meta-data used to build the help link map for the annotated component.
 */
export interface ContextHelpDescriptor {
   /**
    * The route to the annotated component, including a wildcard for parameterized routes. For
    * example, '/em/monitor/*'.
    */
   route: string;

   /**
    * The help identifier portion of the help documentation URL.
    */
   link: string;
}

/**
 * Decorator used to associate context help metadata with components. This decorator is not actually
 * used at runtime, but instead is used to statically build a help link map during the build.
 */
export function ContextHelp(descriptor: ContextHelpDescriptor) {
   // NO-OP
   return (target: any) => target;
}