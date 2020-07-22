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
 * Class that is used to notify subscribers that an assembly action has been performed.
 */
export class AssemblyActionEvent<T> {
   /**
    /**
    * Creates a new instance of AssemblyActionEvent.
    *
    * @param id the action identifier.
    * @param model the target assembly model.
    * @param event the mouse event that triggered this event.
    */
   constructor(public id: string, public model: T, public event: MouseEvent = null) {
   }
}
