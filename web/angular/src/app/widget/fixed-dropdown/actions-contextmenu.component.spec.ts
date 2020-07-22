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

import { Component } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { AssemblyActionGroup } from "../../common/action/assembly-action-group";

import { ActionsContextmenuComponent } from "./actions-contextmenu.component";

const visibleAndInvisibleActions = [
   new AssemblyActionGroup([
      {
         id: () => "1",
         label: () => "label",
         icon: () => "icon",
         enabled: () => true,
         visible: () => true,
         action: () => {}
      },
      {
         id: () => "2",
         label: () => "label",
         icon: () => "icon",
         enabled: () => true,
         visible: () => false,
         action: () => {}
      },
   ])];
const disabledActions = [
   new AssemblyActionGroup([
      {
         id: () => "1",
         label: () => "label",
         icon: () => "icon",
         enabled: () => false,
         visible: () => true,
         action: () => {}
      }
   ])];
const multipleActionGroups = [
   new AssemblyActionGroup([
      {
         id: () => "1",
         label: () => "label",
         icon: () => "icon",
         enabled: () => true,
         visible: () => true,
         action: () => {}
      }
   ]),
   new AssemblyActionGroup([
      {
         id: () => "2",
         label: () => "label",
         icon: () => "icon",
         enabled: () => true,
         visible: () => true,
         action: () => {}
      }
   ])];
const invisibleActions = [
   new AssemblyActionGroup([
      {
         id: () => "1",
         label: () => "label",
         icon: () => "icon",
         enabled: () => true,
         visible: () => false,
         action: () => {}
      }
   ])];

@Component({
   selector: "test-app",
   template: `<actions-contextmenu [actions]="actions"></actions-contextmenu>`
})
class TestApp {
   actions: AssemblyActionGroup[];
}

describe("Actions Contextmenu Component Tests", () => {
   beforeEach(() => {
      TestBed.configureTestingModule({
         declarations: [TestApp, ActionsContextmenuComponent],
      }).compileComponents();
   });

   it("should display only visible actions", () => {
      let fixture = TestBed.createComponent(TestApp);
      let app = fixture.componentInstance;
      app.actions = visibleAndInvisibleActions;
      fixture.detectChanges(true);

      expect(fixture.debugElement.queryAll(By.css("a")).length).toBe(1);
   });

   it("should apply a css class to disabled actions", () => {
      let fixture = TestBed.createComponent(TestApp);
      let app = fixture.componentInstance;
      app.actions = disabledActions;
      fixture.detectChanges(true);

      expect(fixture.debugElement.query(By.css("a.disable-link"))).toBeTruthy();
   });

   it("should create a divider to separate action groups", () => {
      let fixture = TestBed.createComponent(TestApp);
      let app = fixture.componentInstance;
      app.actions = multipleActionGroups;
      fixture.detectChanges(true);

      expect(fixture.debugElement.queryAll(By.css(".dropdown-divider")).length).toBe(1);
   });

   it("should not create a dropdown when there are no visible actions", () => {
      let fixture = TestBed.createComponent(TestApp);
      let app = fixture.componentInstance;
      app.actions = invisibleActions;
      fixture.detectChanges(true);

      expect(fixture.debugElement.query(By.css(".show"))).toBeFalsy();
   });
});
