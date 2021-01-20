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

import {
   AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild
} from "@angular/core";
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TextEditorModel } from "../model/text-editor-model";
import { TextEditorState } from "../rich-text-editor/text-editor-state";
import { MdEditorConfig } from "./md-editor-config";

declare const editormd: any;

@Component({
  selector: "md-text-editor",
  templateUrl: "./md-text-editor.component.html",
  styleUrls: ["./md-text-editor.component.scss"]
})
export class MdTextEditorComponent implements OnInit, AfterViewInit {
   @Input() model: TextEditorModel;
   @Input() hiddenToolbar = false;
   @Input() changeModeDisabled = false;
   @Input() hiddenApplyBtns = false;
   @Input() state = TextEditorState.ALL;
   @Input() placeholder: string;
   @Input() height: string;
   @Output() onContentChanged = new EventEmitter<string>();
   @Output() onApply = new EventEmitter<TextEditorModel>();
   @ViewChild("mdEditor") mdEditor: ElementRef;
   @ViewChild("mdEditorBody", { static: true}) mdEditorBody: ElementRef;
   TextEditorState = TextEditorState;
   form: FormGroup;
   editor: any;
   editorConfig: MdEditorConfig = new MdEditorConfig(() => {
      if(!!this.model && !!this.mdEditor) {
         this.model.content = this.mdEditor.nativeElement.innerText;

         this.onContentChanged.emit(this.model.content);
      }
      else {
         console.error("This model is ", this.model,
            " this mdEditor is ", this.mdEditor);
      }
   });

   viewInit = false;

   constructor(private fb: FormBuilder) {
   }

   ngOnInit(): void {
      this.form = this.fb.group({
         "titleControl": this.fb.control(this.model?.title, [ Validators.required ])
      });

      this.titleControl?.valueChanges.subscribe(title => {
         this.model.title = title;
      });

      if(!!this.height) {
         this.editorConfig.height = this.height;
      }

      if(this.state == TextEditorState.PREVIEW) {
         this.editorConfig.markdown = this.model?.content || "";
      }

      this.changeState(this.state);

      this.viewInit = true;
   }

   ngAfterViewInit(): void {
      this.initMarkdown();
   }

   initMarkdown() {
      this.editor = editormd("article-markdown-editor-container", this.editorConfig);
   }

   get titleControl(): AbstractControl {
      return this.form.get("titleControl");
   }

   get content(): string {
      return this.model.content;
   }

   set content(content: string) {
      this.model.content = content;
   }

   changeState(state: TextEditorState): void {
      this.state = state;

      if(!!!this.editor?.state) {
         return;
      }

      if(state == TextEditorState.PREVIEW) {
         this.editor.state.preview = true;
      }
      else if(state == TextEditorState.EDIT) {
         this.editor.state.preview = false;
      }
      else {
         // edit & preview.
         this.editor.state.preview = false;
      }
   }

   apply(): void {
      this.onApply.emit(this.model);
   }

}
