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

import { Component, Input } from "@angular/core";
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { ExpandStringDirective } from "./expand-string.directive";

@Component({
   selector: "w-content-test",
   template: `<span [wExpandString]="values" class="content-test-span">{{content}}</span>`
})
class ContentTestComponent {
   @Input() values: any[];
   @Input() content: string;
}

@Component({
   selector: "w-attribute-test",
   template: `<span [wExpandString]="values" [wExpandStringAttr]="attribute" title="{{content}}" [attr.data-test]="content" class="attribute-test-span">Static test</span>`
})
class AttributeTestComponent {
   @Input() values: any[];
   @Input() content: string;
   @Input() attribute: string;
}

describe("expand-string.directive", () => {
   let contentTest: ComponentFixture<ContentTestComponent>;
   let attributeTest: ComponentFixture<AttributeTestComponent>;

   beforeEach(() => {
      TestBed.configureTestingModule({
         declarations: [ExpandStringDirective, ContentTestComponent, AttributeTestComponent]
      }).compileComponents();

      contentTest = TestBed.createComponent(ContentTestComponent);
      attributeTest = TestBed.createComponent(AttributeTestComponent);
   });

   it("should replace single string content", () => {
      contentTest.componentInstance.values = ["Test input"];
      contentTest.componentInstance.content = "The input is \"%s\"!";
      contentTest.detectChanges();

      let element = contentTest.debugElement.query(By.css(".content-test-span"));
      let actual = element.nativeElement.innerHTML;
      expect(actual).toBe("The input is \"Test input\"!");

      contentTest.componentInstance.values = ["Different input"];
      contentTest.detectChanges();

      element = contentTest.debugElement.query(By.css(".content-test-span"));
      actual = element.nativeElement.innerHTML;
      expect(actual).toBe("The input is \"Different input\"!");
   });

   it("should replace string sequence content", () => {
      contentTest.componentInstance.values = [ "first", "second" ];
      contentTest.componentInstance.content = "The first value is \"%s\". The second value is \"%s\"";
      contentTest.detectChanges();

      let element = contentTest.debugElement.query(By.css(".content-test-span"));
      let actual = element.nativeElement.innerHTML;
      expect(actual).toBe("The first value is \"first\". The second value is \"second\"");

      contentTest.componentInstance.values = [ "a", "b" ];
      contentTest.detectChanges();

      element = contentTest.debugElement.query(By.css(".content-test-span"));
      actual = element.nativeElement.innerHTML;
      expect(actual).toBe("The first value is \"a\". The second value is \"b\"");
   });

   it("should replace indexed string content", () => {
      contentTest.componentInstance.values = [ "first", "second" ];
      contentTest.componentInstance.content = "The second value is \"%s$1\". The first value is \"%s$0\"";
      contentTest.detectChanges();

      let element = contentTest.debugElement.query(By.css(".content-test-span"));
      let actual = element.nativeElement.innerHTML;
      expect(actual).toBe("The second value is \"second\". The first value is \"first\"");

      contentTest.componentInstance.values = [ "a", "b" ];
      contentTest.detectChanges();

      element = contentTest.debugElement.query(By.css(".content-test-span"));
       actual = element.nativeElement.innerHTML;
      expect(actual).toBe("The second value is \"b\". The first value is \"a\"");
   });

   it("should replace single string attribute", () => {
      attributeTest.componentInstance.values = ["Test input"];
      attributeTest.componentInstance.content = "The input is \"%s\"!";
      attributeTest.componentInstance.attribute = "title";
      attributeTest.detectChanges();

      let element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      let actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("The input is \"Test input\"!");

      attributeTest.componentInstance.values = ["Different input"];
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("The input is \"Different input\"!");

      attributeTest.componentInstance.values = ["Test input"];
      attributeTest.componentInstance.attribute = "data-test";
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("data-test");
      expect(actual).toBe("The input is \"Test input\"!");

      attributeTest.componentInstance.values = ["Different input"];
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("data-test");
      expect(actual).toBe("The input is \"Different input\"!");
   });

   it("should replace string sequence attribute", () => {
      attributeTest.componentInstance.values = [ "first", "second" ];
      attributeTest.componentInstance.content = "The first value is \"%s\". The second value is \"%s\"";
      attributeTest.componentInstance.attribute = "title";
      attributeTest.detectChanges();

      let element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      let actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("The first value is \"first\". The second value is \"second\"");

      attributeTest.componentInstance.values = [ "a", "b" ];
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("The first value is \"a\". The second value is \"b\"");

      attributeTest.componentInstance.values = [ "first", "second" ];
      attributeTest.componentInstance.attribute = "data-test";
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("data-test");
      expect(actual).toBe("The first value is \"first\". The second value is \"second\"");

      attributeTest.componentInstance.values = [ "a", "b" ];
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("data-test");
      expect(actual).toBe("The first value is \"a\". The second value is \"b\"");
   });

   it("should replace indexed string attribute", () => {
      attributeTest.componentInstance.values = [ "first", "second" ];
      attributeTest.componentInstance.content = "The second value is \"%s$1\". The first value is \"%s$0\"";
      attributeTest.componentInstance.attribute = "title";
      attributeTest.detectChanges();

      let element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      let actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("The second value is \"second\". The first value is \"first\"");

      attributeTest.componentInstance.values = [ "a", "b" ];
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("The second value is \"b\". The first value is \"a\"");

      attributeTest.componentInstance.values = [ "first", "second" ];
      attributeTest.componentInstance.attribute = "data-test";
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("data-test");
      expect(actual).toBe("The second value is \"second\". The first value is \"first\"");

      attributeTest.componentInstance.values = [ "a", "b" ];
      attributeTest.detectChanges();

      element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      actual = element.nativeElement.getAttribute("data-test");
      expect(actual).toBe("The second value is \"b\". The first value is \"a\"");
   });

   it("replacement marker at the beginning of string", () => {
      attributeTest.componentInstance.values = [ "first" ];
      attributeTest.componentInstance.content = "%s$0 has expired";
      attributeTest.componentInstance.attribute = "title";
      attributeTest.detectChanges();

      let element = attributeTest.debugElement.query(By.css(".attribute-test-span"));
      let actual = element.nativeElement.getAttribute("title");
      expect(actual).toBe("first has expired");
   });
});
