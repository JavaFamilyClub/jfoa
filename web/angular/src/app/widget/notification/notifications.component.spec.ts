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

import { TestBed, async, ComponentFixture } from "@angular/core/testing";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NotificationsComponent } from "./notifications.component";

describe("NotificationsComponent Integration Tests", () => {
   beforeEach(async(() => {
      TestBed.configureTestingModule({
         imports: [
            // NoopAnimationsModule,
            NgbModule
         ],
         declarations: [
            NotificationsComponent
         ]
      });
      TestBed.compileComponents();
   }));


   function testAlert(type: string, message: string) {
      let fixture: ComponentFixture<NotificationsComponent> =
         TestBed.createComponent(NotificationsComponent);
      fixture.componentInstance[type](message);
      fixture.detectChanges();
      let alertElement: Element =
         fixture.nativeElement.querySelector("ngb-alert>div.alert-" + type);
      expect(alertElement).toBeTruthy();
      let alertText: string = "";
      let child: Node = alertElement.firstChild;

      while(child) {
         if(child.nodeType === 3) { // Node.TEXT_NODE
            alertText += child.nodeValue;
         }

         child = child.nextSibling;
      }

      alertText = alertText.replace(/^\s*(.+)\s*$/, "$1");
      expect(alertText).toEqual(message);
   }

   xit("should create success alert", () => {
      testAlert("success", "This is a success message");
   });

   xit("should create info alert", () => {
      testAlert("info", "This is a info message");
   });

   xit("should create warning alert", () => {
      testAlert("warning", "This is a warning message");
   });

   xit("should create danger alert", () => {
      testAlert("danger", "This is a danger message");
   });
});
