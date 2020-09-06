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

import { AfterViewInit, ChangeDetectorRef, Component, Inject, Input } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { MatMsgModel } from "./mat-msg-model";

@Component({
   selector: "mat-message-dialog",
   templateUrl: "mat-message-dialog.html",
   styleUrls: ["mat-message-dialog.scss"]
})
export class MatMessageDialog implements AfterViewInit {

   constructor(private dialogRef: MatDialogRef<MatMessageDialog>,
               private changeRef: ChangeDetectorRef,
               @Inject(MAT_DIALOG_DATA) private data: MatMsgModel)
   {
   }

   ngAfterViewInit(): void {
      this.changeRef.detectChanges();
   }

   get message(): string {
      return this.data?.message;
   }

   get title(): string {
      return this.data?.title;
   }

   get confirm(): boolean {
      return this.data?.confirm;
   }

   close(event: MouseEvent, ok: boolean = false): void {
      event.stopPropagation();
      event.preventDefault();
      this.dialogRef.close(ok);
   }

}

