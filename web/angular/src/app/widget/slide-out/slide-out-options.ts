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

import { NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";

/**
 * Additional options for the slide out component.
 */
export interface SlideOutOptions extends NgbModalOptions {
   /**
    * The panel title is automatically set via ".modal-title" selector content,
    * however in the case of binded titles, this will not work.
    * It is best to set it in that case.
    */
   title?: string;
   /**
    * Which side of the container to attach to.
    */
   side?: "left" | "right";
   // assembly id associated with this pane
   objectId?: string;
   // true to show popup dialog
   popup?: boolean;
}
