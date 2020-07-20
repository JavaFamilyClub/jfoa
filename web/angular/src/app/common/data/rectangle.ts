/*
 * Copyright (c) 2019, AngBoot Technology Corp, All Rights Reserved.
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

export interface Rectangular {
   x: number;
   y: number;
   width: number;
   height: number;
}

export class Rectangle implements Rectangular {
   constructor(public x: number, public y: number, public width: number, public height: number) {
   }

   /**
    * Check if point contains to this rect.
    */
   public contains(x: number, y: number): boolean {
      return x >= this.x && x <= this.x + this.width &&
         y >= this.y && y <= this.y + this.height;
   }

   /**
    * Create a Rectangle from a client bounding rectangle
    */
   public static fromClientRect(clientRect: ClientRect): Rectangle {
      return new Rectangle(clientRect.left, clientRect.top, clientRect.width, clientRect.height);
   }

   public intersects(rect: Rectangle | ClientRect): boolean {
      if(!rect) {
         return false;
      }

      if(!(rect instanceof Rectangle)) {
         rect = Rectangle.fromClientRect(rect);
      }

      const rLeft = rect.x;
      const rRight = rect.x + rect.width;
      const rTop = rect.y;
      const rBot = rect.y + rect.height;

      const left = this.x;
      const right = this.x + this.width;
      const top = this.y;
      const bot = this.y + this.height;

      return left < rRight && right > rLeft && top < rBot && bot > rTop;
   }

   /**
    * True if this rectangle encloses an area
    */
   public isEmpty(): boolean {
      return this.width <= 0 || this.height <= 0;
   }
}
