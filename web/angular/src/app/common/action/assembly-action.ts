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
/**
 * Interface for classes that provide the implementation of a toolbar or menu action.
 */
import { AssemblyActionGroup } from "./assembly-action-group";

export interface AssemblyAction {
   /**
    * The id for the menu item or toolbar button.
    */
   id: () => string;

   /**
    * The label for the menu item or tooltip text for the toolbar button.
    */
   label: () => string;

   /**
    * The CSS class used for the toolbar button icon.
    */
   icon: () => string;

   /**
    * Determines if the action is currently displayed.
    */
   visible: () => boolean;

   /**
    * Determines if the action is currently enabled.
    */
   enabled: () => boolean;

   /**
    * Action to be performed when the action is invoked.
    */
   action?: (sourceEvent: MouseEvent) => any;

   /**
    * URL to be opened when the action is clicked.
    */
   link?: () => string;

   /**
    * The target window in which the link URL should be opened.
    */
   target?: () => string;

   /**
    * Space-separated classes to apply to the context menu action.
    */
   classes?: () => string;

   /**
    * child action.
    */
   childAction?: () => AssemblyActionGroup[];
}
