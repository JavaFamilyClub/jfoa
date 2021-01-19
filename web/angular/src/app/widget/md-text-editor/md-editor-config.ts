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

export class MdEditorConfig {
   public width = "100%";
   public height = "100%";
   public path = "assets/editor.md/lib/";
   public codeFold: true;
   public searchReplace = true;
   public toolbar = true;
   public emoji = false;
   public taskList = false;
   public tex = true; // 数学公式类默认不解析
   public readOnly = false;
   public tocm = true;
   public watch = true;
   public previewCodeHighlight = true;
   public saveHTMLToTextarea = true;
   public markdown = "";
   public flowChart = true; //流程图
   public syncScrolling = true;
   public sequenceDiagram = false; //UML时序图
   public imageUpload = true;
   public imageFormats = ["jpg", "jpeg", "gif", "png", "bmp", "webp"];
   public imageUploadURL = "";
   public htmlDecode = "style,script,iframe"; // you can filter tags decode
   public editorFunction = ""; //定义调用的方法
   public onchange: () => void;

   constructor(onchange?: () => void, height?: string) {
      this.onchange = onchange;
      this.height = height || this.height;
   }
}
