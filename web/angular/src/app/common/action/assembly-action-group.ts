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

import { AssemblyAction } from "./assembly-action";

export class AssemblyActionGroup {

   /**
    * A boolean flag indicating if this group is visible.
    */
   public get visible(): boolean {
      return this.actions && this.actions.some(v => v && v.visible());
   }

   /**
    * Creates a new instance of AssemblyActionGroup.
    *
    * @param actions the actions in the group.
    */
   constructor(public actions: AssemblyAction[] = [],
               public label: () => string = () => "options...",
               public icon: () => string = () => "menu-vertical-icon")
   {
   }
}
