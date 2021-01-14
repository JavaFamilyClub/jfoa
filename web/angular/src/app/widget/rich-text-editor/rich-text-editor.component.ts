/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
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

import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from "@angular/core";
import { TextEditorModel } from "../model/text-editor-model";
import { SplitPaneComponent } from "../split/split-pane.component";
import { TextEditorState } from "./text-editor-state";

const MIN_HEIGHT = 50;

@Component({
   selector: "rich-text-editor",
   templateUrl: "./rich-text-editor.component.html",
   styleUrls: ["./rich-text-editor.component.scss"]
})
export class RichTextEditorComponent implements OnInit, AfterViewInit {
   @Input() model: TextEditorModel;
   @Input() hiddenToolbar = false;
   @Input() changeModeDisabled = false;
   @Input() hiddenApplyBtns = false;
   @Input() state = TextEditorState.ALL;
   @Input() placeholder: string;
   @Input() height: string;
   @Output() onContentChanged = new EventEmitter<string>();
   @Output() onApply = new EventEmitter<TextEditorModel>();
   @Output() onCancel = new EventEmitter<void>();

   @ViewChild("mdEditorBody", { static: true}) mdEditorBody: ElementRef;
   @ViewChild("froalaContainer") froalaContainer: ElementRef;
   @ViewChild(SplitPaneComponent) splitPane: SplitPaneComponent;
   TextEditorState = TextEditorState;
   defaultSplitSizes = [50, 50];

   viewInit = false;

   constructor() {
   }

   ngOnInit(): void {
      if(!!this.placeholder) {
         this.options.placeholderText = this.placeholder;
      }

      if(!!this.height) {
         this.options.height = this.height;
      }
      else if(!!this.mdEditorBody) {
         /**
          * -50: top toolbar
          * -38: bottom toolbar
          */
         const height =  this.mdEditorBody.nativeElement.clientHeight
            - 50 - 38;

         this.options.height = Math.max(height, MIN_HEIGHT);
      }

      this.defaultSplitSizes = this.getSplitSize(this.state);

      this.viewInit = true;
   }

   options: any = {
      language: "zh_cn",
      placeholderText: "",
      height: "280",
      toolbarButtonsXS: ["undo", "redo", "|", "bold", "italic", "underline", "|", "fontSize", "align", "color"],
      pasteAllowedStyleProps: ["font-size", "color"],
      htmlAllowComments: false,
      fontSizeDefaultSelection: "16",
      colorsHEXInput: false,
      toolbarSticky: false,
      colorsBackground: ["#2E2E2E", "#767676", "#DF281B", "#F4821C", "#46AC43", "#2E5BF7", "#A249B3", "REMOVE"],
      colorsText: ["#2E2E2E", "#767676", "#DF281B", "#F4821C", "#46AC43", "#2E5BF7", "#A249B3", "REMOVE"],
      fontSize: ["14", "16", "18", "20"],
      charCounterCount: true,
      quickInsertButtons: ["image", "table", "ol", "ul"],
      quickInsertEnabled: true,
      embedlyKey: "JavaFamily",
      embedlyEditButtons: []
   };

   get content(): string {
      return this.model.content;
   }

   set content(content: string) {
      this.model.content = content;
   }

   changeState(state: TextEditorState): void {
      this.state = state;

      console.log("=============splitPane========", this.splitPane);

      if(!!!this.splitPane) {
         return;
      }

      this.splitPane.setSizes(this.getSplitSize(state));
   }

   getSplitSize(state: TextEditorState): number[] {
      switch(state) {
         case TextEditorState.EDIT:
            return [100, 0];
         case TextEditorState.PREVIEW:
            return [0, 100];
         default:
            return [50, 50];
      }
   }

   apply(apply: boolean = false): void {
      if(apply) {
         this.onApply.emit(this.model);
      }
      else {
         this.onCancel.emit();
      }
   }

   ngAfterViewInit(): void {
   }
}
