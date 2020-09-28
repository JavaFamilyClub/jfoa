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

export class AnimationFrameBatcher {
   private reads;
   private writes;
   private rafScheduled;
   private animationID;

   constructor() {
      this.reads = [];
      this.writes = [];
      this.rafScheduled = false;
      this.animationID = -1;
   }

   public requestRead(cb: () => void): number {
      this.reads.push(cb);
      return this.scheduleAnimationFrame();
   }

   public requestWrite(cb: () => void): number {
      this.writes.push(cb);
      return this.scheduleAnimationFrame();
   }

   public cancelAnimationFrame(): void {
      cancelAnimationFrame(this.animationID);
   }

   private scheduleAnimationFrame(): number {
      if(!this.rafScheduled) {
         try {
            cancelAnimationFrame(this.animationID);
            this.animationID = requestAnimationFrame(() => {
               cancelAnimationFrame(this.animationID);
               this.flush();
            });

            this.rafScheduled = true;
         }
         catch(e) {
            this.rafScheduled = false;
            throw e;
         }
      }

      return this.animationID;
   }

   private flush(): void {
      this.reads.forEach((cb) => cb());
      this.writes.forEach((cb) => cb());
      this.reads = [];
      this.writes = [];
      this.rafScheduled = false;
      this.animationID = -1;
   }
}

