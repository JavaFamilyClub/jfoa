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
import { ChangeDetectorRef, Component, Input, OnInit } from "@angular/core";
import { NotificationType, Notification } from "../../common/data/notification";

/**
 * Component that displays notification messages to the user.
 */
@Component({
   selector: "notifications", // tslint:disable-line:component-selector
   templateUrl: "notifications.component.html",
   styleUrls: ["notifications.component.scss"]
})
export class NotificationsComponent implements OnInit {
   /* Optional timeout for notifications. */
   @Input() timeout: number = 0;
   @Input() message: string = "";
   alerts: ({id: number} & Notification)[] = [];
   private counter: number = 0;

   constructor(private changeDetectionRef: ChangeDetectorRef) {
   }

   ngOnInit() {
      if(this.message) {
         this.info(this.message);
      }
   }

   /**
    * Displays a success message.
    *
    * @param message the message text.
    */
   public success(message: string): void {
      this.addAlert(message, "success");
   }

   /**
    * Displays an information message.
    *
    * @param message the message text.
    */
   public info(message: string): void {
      this.addAlert(message, "info");
   }

   /**
    * Displays a warning message.
    *
    * @param message the message text.
    */
   public warning(message: string): void {
      this.addAlert(message, "warning");
   }

   /**
    * Displays a danger message.
    *
    * @param message the message text.
    */
   public danger(message: string): void {
      this.addAlert(message, "danger");
   }

   private alertShowing(message: string): boolean {
      return this.alerts.map(a => a.message).indexOf(message) >= 0;
   }

   private addAlert(message: string, type: NotificationType): void {
      // ignore duplicates
      if(this.alertShowing(message)) {
         return;
      }

      const alert = {
         id: this.counter++,
         type: type,
         message: message
      };
      this.alerts.push(alert);

      if(this.timeout > 0) {
         setTimeout(this.closeAlert.bind(this), this.timeout, alert);
      }
   }

   closeAlert(alert: any): void {
      const index: number = this.alerts.indexOf(alert);

      if(index >= 0) {
         this.alerts.splice(index, 1);
      }

      if(!this.changeDetectionRef["destroyed"]) {
         this.changeDetectionRef.detectChanges();
      }
   }
}
