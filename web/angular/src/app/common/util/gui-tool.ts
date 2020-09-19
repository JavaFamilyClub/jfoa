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

import { NgZone } from "@angular/core";
import { AbstractControl, FormControl } from "@angular/forms";
import { Observable, of as observableOf, Subject } from "rxjs";
import { HttpParams } from "@angular/common/http";
import { DomService } from "../../widget/dom-service/dom.service";
import { isString, isBoolean, isNumber, isArray } from "util";
import { Dimension } from "../data/dimension";
import { TreeNodeModel } from "../../widget/tree/model/tree-node-model";
import { Tool } from "./tool";
import { NetTool } from "./net-tool";

declare const window: any;

export namespace GuiTool {
   /**
     * Fraction width, 1/16 point.
     */
   export const FRACTION_WIDTH_MASK: number = 0xF0000;

   /**
    * This mask is used to extract the encoded line width from line styles.
    */
   export const WIDTH_MASK: number = 0x0F;

   /**
    * This mask is used to extract the dash length.
    */
   export const DASH_MASK: number = 0x0F0;

   export const DATA_TIP_OFFSET = measureScrollbars();

   export const MINI_TOOLBAR_HEIGHT = 28;

   export const MINIMUM_TITLE_HEIGHT = 18;

   export let scrollbarWidth: number;

   /**
    * Return the text width in pixels.
    */
   export function measureText(str: string, font: string): number {
      const id = "measureText_canvas";
      let canvas: HTMLCanvasElement =
         <HTMLCanvasElement> window.document.querySelector("#" + id);

      if(!canvas) {
         canvas = window.document.createElement("canvas");
         canvas.style.display = "flex";
         canvas.id = id;
         canvas.width = 1;
         canvas.height = 1;
         window.document.body.appendChild(canvas);
      }

      const context: CanvasRenderingContext2D = canvas.getContext("2d");
      context.font = font;
      return context.measureText(str).width;
   }

   /**
    * Get width of the current browsers scrollbars by temporarily creating a div
    * with an overflow and measuring the width.
    *
    * Copied from:
    * http://stackoverflow.com/questions/13382516/getting-scroll-bar-width-using-javascript
    *
    * @returns {number} the width of the scrollbar in pixels
    */
   export function measureScrollbars(): number {
      if(scrollbarWidth === undefined) {
         let outer = window.document.createElement("div");
         outer.style.visibility = "hidden";
         outer.style.width = "100px";
         outer.style.msOverflowStyle = "scrollbar"; // needed for WinJS apps

         window.document.body.appendChild(outer);

         let widthNoScroll = outer.offsetWidth;
         // force scrollbars
         outer.style.overflow = "scroll";

         // add innerdiv
         let inner = window.document.createElement("div");
         inner.style.width = "100%";
         outer.appendChild(inner);

         let widthWithScroll = inner.offsetWidth;

         // remove divs
         outer.parentNode.removeChild(outer);

         scrollbarWidth = widthNoScroll - widthWithScroll;
         // Some browsers (e.g. safari) don"t show scrollbars unless scrolling so it does not
         // find the scroll bar width. Default to 10px
         scrollbarWidth =  scrollbarWidth == 0 ? 10 : scrollbarWidth;
      }

      return scrollbarWidth;
   }

   /**
    * Checks whether the element matches the selector.
    *
    * @param el the element to check
    * @param selector the selector to check
    *
    * @returns true if the element matches the selector, false otherwise
    */
   export function matches(el: Element, selector: string): boolean {
      let matchesFn: string;

      // find vendor prefix
      ["matches", "webkitMatchesSelector", "mozMatchesSelector",
       "msMatchesSelector", "oMatchesSelector"].some(fn => {
         if(typeof document.body[fn] == "function") {
            matchesFn = fn;
            return true;
         }

         return false;
      });

      return el && el[matchesFn](selector);
   }

   // Find the closest parent
   export function closest(el: Element, selector: string): Element {
      let parent: Element;

      // traverse parents
      while(el) {
         parent = el.parentElement;

         if(matches(parent, selector)) {
            return parent;
         }

         el = parent;
      }

      return null;
   }

   // Check if mouse is inside element
   export function isMouseIn(elem: HTMLElement, event: MouseEvent): boolean {
      const rect = elem.getBoundingClientRect();
      return event.pageX >= rect.left &&
         event.pageX <= rect.left + rect.width &&
         event.pageY >= rect.top &&
         event.pageY <= rect.top + rect.height;
   }

   /**
    * Gets the total viewport size of the hosting browser window.
    *
    * @returns {any} a tuple containing the width and height.
    */
   export function getViewportSize(): [number, number] {
      let size: [number, number] = [-1, -1];
      let w: number;
      let h: number;

      if(typeof window.innerWidth !== "undefined") {
         if(window.innerWidth > 0) {
            size[0] = window.innerWidth;
         }

         if(window.innerHeight > 0) {
            size[1] = window.innerHeight;
         }
      }

      if(typeof window.document.documentElement !== "undefined" &&
         typeof window.document.documentElement.clientWidth !== "undefined" &&
         window.document.documentElement.clientWidth != 0) {
         w = window.document.documentElement.clientWidth;
         h = window.document.documentElement.clientHeight;

         if(size[0] == -1) {
            size[0] = w;
            size[1] = h;
         }
         else {
            if(w > 0) {
               size[0] = Math.min(size[0], w);
            }

            if(h > 0) {
               size[1] = Math.min(size[1], h);
            }
         }
      }

      w = window.document.getElementsByTagName("body")[0].clientWidth;
      h = window.document.getElementsByTagName("body")[0].clientHeight;

      if(size[0] == -1) {
         size[0] = w;
         size[1] = h;
      }

      return size;
   }

   export function getMaxModeSize(container: any = null, touch: boolean = false): Dimension {
      if(!!container && typeof(container) === "string") {
         container = window.document.querySelector(container);
      }

      if(container) {
         let {width, height} = getElementClientRect(container);

         if(!touch) {
            if(width < container.offsetWidth) {
               height -= GuiTool.measureScrollbars();
            }

            if(height < container.offsetHeight) {
               width -= GuiTool.measureScrollbars();
            }
         }

         width = Math.min(width, window.document.body.clientWidth);
         height = Math.min(height, window.document.body.clientHeight);
         return new Dimension(width, height);
      }

      return new Dimension(
         Math.max(window.innerWidth, document.documentElement.clientWidth || 0) - 20,
         Math.max(window.innerHeight, document.documentElement.clientHeight || 0) - 75,
      );
   }

   export function getChartMaxModeSize(container: any = null, touch: boolean = false): Dimension {
      let size = GuiTool.getMaxModeSize(container, touch);

      if(container) {
         // subtract size of scroll area divs
         size.width -= 17;
         size.height -= 17;
      }
      else {
         const sbSize = GuiTool.measureScrollbars();
         container = window.document.querySelector(".right-container.split");
         size = GuiTool.getMaxModeSize(container, touch);

         const topContainer = window.document.querySelector(".right-top-container");
         const topSize = GuiTool.getMaxModeSize(topContainer, touch);
         size.width -= 2 * sbSize;
         size.height -= topSize.height + 2 * sbSize;
      }

      return size;
   }

   export function isTouchDevice(): Promise<boolean> {
      let result: Promise<boolean>;

      if(!!window.DocumentTouch && (window.document instanceof DocumentTouch)) {
         result = observableOf(true).toPromise();
      }
      else {
         let div = window.document.querySelector("#inetsoft_touchTest_div");

         if(!div) {
            div = window.document.createElement("div");
            div.id = "inetsoft_touchTest_div";
            window.document.body.appendChild(div);

            const style = window.document.createElement("style");
            style.innerHTML = `#inetsoft_touchTest_div {
               position: absolute;
               z-index: -1;
               display: none;
            }
            @media ("-moz-touch-enabled"),
            ("-ms-touch-enabled"),
            ("-o-touch-enabled"),
            (pointer:coarse),
            not all (pointer:fine) {
              #inetsoft_touchTest_div {
                display: block;
              }
            }`;
            window.document.body.appendChild(style);
         }

         const subject: Subject<boolean> =  new Subject<boolean>();
         result = subject.toPromise();
         window.setTimeout(() => {
            subject.next(!!div.offsetParent);
            subject.complete();
         }, 0);
      }

      return result;
   }

   function getElementClientRect(element: any): {top: number, left: number, bottom: number, right: number, width: number, height: number} {
      const clientRect = (element instanceof window.SVGElement
         ? element.getBoundingClientRect()
         : element.getClientRects()[0]);

      // account for the border on editable-object
      const adj = element.classList.contains("object-editor") ? 2 : 0;

      return clientRect && {
         left  : clientRect.left + adj,
         right : clientRect.right - adj,
         top   : clientRect.top + adj,
         bottom: clientRect.bottom - adj,
         width : (clientRect.width || clientRect.right  - clientRect.left) - adj * 2,
         height: (clientRect.height || clientRect.bottom - clientRect.top) - adj * 2,
      };
   }

   function getScrollXY(relevantWindow: any): {x: number, y: number} {
      relevantWindow = relevantWindow || window;
      return {
         x: relevantWindow.scrollX || relevantWindow.document.documentElement.scrollLeft,
         y: relevantWindow.scrollY || relevantWindow.document.documentElement.scrollTop,
      };
   }

   function isWindow(obj: any): boolean {
      return !!(obj && obj.Window) && (obj instanceof obj.Window);
   }

   function getWindow(node: any): any {
      if(isWindow(node)) {
         return node;
      }

      const rootNode = (node.ownerDocument || node);
      return rootNode.defaultView || rootNode.parentWindow || window;
   }

   export function getElementRect(element: any): {top: number, left: number, bottom: number, right: number, width: number, height: number} {
      const clientRect = getElementClientRect(element);

      if(clientRect) {
         const scroll = getScrollXY(getWindow(element));

         clientRect.left   += scroll.x;
         clientRect.right  += scroll.x;
         clientRect.top    += scroll.y;
         clientRect.bottom += scroll.y;
      }

      return clientRect;
   }

   export function resolveUrl(relativeUrl: string): string {
      const resolver = window.document.createElement("a");
      resolver.href = relativeUrl;
      return <string> resolver.href;
   }

   /**
   * This method needs to be called to append parameters when the type of value is unknown.
   **/
   export function appendHttpParams(params: HttpParams, key: string,
      value: any, allowRepeat: boolean = false): HttpParams
   {
      if(!key || !value) {
         return params;
      }

      if(isString(value) || isBoolean(value) || isNumber(value)) {
         if(!allowRepeat) {
            return params.set(key, value + "");
         }

         return params.has(key) ? params.append(key, value + "") : params.set(key, value + "");
      }

      if(isArray(value)) {
         value.forEach((val) => {
            params = GuiTool.appendHttpParams(params, key, val, allowRepeat);
         });
      }

      return params;
   }

   export function appendParams(url: string, params?: HttpParams): string {
      if(!!params) {
         params.keys().forEach((paramName) => {
            if(!!paramName && !!params.getAll(paramName)) {
               params.getAll(paramName)
                  .filter((paramValue) => !!paramValue)
                  .forEach((paramValue) => {
                     url += url.indexOf("?") < 0 ? "?" : "&";
                     url += paramName + "=" + encodeURIComponent(paramValue);
                  });
            }
         });
      }

      return url;
   }

   export function openBrowserTab(url: string,
                                  params?: HttpParams,
                                  target: string = "_blank"): void {
      url = GuiTool.appendParams(url, params);
      const tokenEntry = NetTool.xsrfToken();

      if(url.indexOf(NetTool.PARAMETER_NAME) < 0 && !!tokenEntry) {
         url += url.indexOf("?") < 0 ? "?" : "&";
         url += tokenEntry;
      }

      window.open(url, target);
   }

   const defsizes: {dragName: string, width: number, height: number}[] = [
      { dragName: "dragchart", width: 500, height: 200 },
      { dragName: "dragcrosstab", width: 400, height: 160 },
      { dragName: "dragtable", width: 400, height: 160 },
      { dragName: "dragfreehandtable", width: 400, height: 160 },
      { dragName: "dragselectionlist", width: 100, height: 120 },
      { dragName: "dragselectiontree", width: 100, height: 120 },
      { dragName: "dragrangeslider", width: 200, height: 40 },
      { dragName: "dragcalendar", width: 300, height: 180 },
      { dragName: "dragselectioncontainer", width: 300, height: 240 },
      { dragName: "dragtext", width: 100, height: 20 },
      { dragName: "dragimage", width: 100, height: 40 },
      { dragName: "draggauge", width: 140, height: 140 },
      { dragName: "dragslider", width: 200, height: 40 },
      { dragName: "dragspinner", width: 100, height: 20 },
      { dragName: "dragcheckbox", width: 200, height: 40 },
      { dragName: "dragradiobutton", width: 200, height: 40 },
      { dragName: "dragcombobox", width: 100, height: 20 },
      { dragName: "dragtextinput", width: 100, height: 20 },
      { dragName: "dragsubmit", width: 100, height: 20 },
      { dragName: "dragupload", width: 220, height: 24 },
      { dragName: "dragline", width: 50, height: 50 },
      { dragName: "dragrectangle", width: 100, height: 75 },
      { dragName: "dragoval", width: 100, height: 75 },
      { dragName: "table", width: 100, height: 120 },
   ];

   /**
    * Find the default object size.
    */
   export function getDefaultObjectSize(dragName: string): any {
      return defsizes.find(d => d.dragName == dragName);
   }

   /**
    * Create a div as a drag image, one line per label.
    */
   export function createDragImage(labels: string[], dragNames: string[] = [],
                                   cols: number = 1): Element
   {
      if(GuiTool.isSafari()) {
         return null;
      }

      let elem: Element = document.querySelector(".show-drag-field");

      if(!elem) {
         elem = document.createElement("div");
         document.body.appendChild(elem);
         elem.setAttribute("class", "show-drag-field txt-primary");
         // Dragging can be done in a modal, make sure the image is visible.
         (<HTMLElement> elem).style.zIndex = "20000";
      }

      (<HTMLElement> elem).style.width = null;
      (<HTMLElement> elem).style.height = null;

      if(dragNames) {
         let w: number = 0;
         let h: number = 0;
         let alignContentLeft: boolean = false;

         dragNames.forEach(dragName => {
            const size = GuiTool.getDefaultObjectSize(dragName);

            if(size) {
               w = Math.max(size.width * cols, w);
               h += size.height;
            }

            if(dragName == "column") {
               alignContentLeft = true;
            }
         });

         if(w > 0 && h > 0) {
            (<HTMLElement> elem).style.width = w + "px";
            (<HTMLElement> elem).style.height = h + "px";
            const fsize = Math.max(12, Math.min(24, h / 10));
            (<HTMLElement> elem).style["font-size"] = fsize.toFixed(0) + "px";

            if(labels.length > 1) {
               (<HTMLElement> elem).style.height = labels.length * h + "px";
            }
         }

         if(alignContentLeft) {
            (<HTMLElement> elem).style.justifyContent = "flex-start";
         }
      }

      elem.innerHTML = labels.join("<br>");
      return elem;
   }

   /**
    * Implement our own drag image because:
    * 1. IE/Edge doesn"t support Dom setDragimage
    * 2. DragImage addes a gradient transparency to the image that makes the image
    * look terrible in many cases.
    * @param event    the drag event.
    * @param image    the image or element to use.
    */
   export function setDragImage(event: any, image: Element, zone: NgZone,
                                domService: DomService): Promise<void>
   {
      if(GuiTool.isSafari()) {
         return new Promise(() => {});
      }

      if(event && event.dataTransfer && event.dataTransfer.setDragImage) {
         if(image instanceof Image) {
            event.dataTransfer.setDragImage(image, 0, 0);
         }
         else {
            event.dataTransfer.setDragImage(new Image(), 0, 0);
         }
      }

      // clear default dragging image
      if(GuiTool.isEdge() || GuiTool.isIE()) {
         const odisplay = event.srcElement.style.display;
         event.srcElement.style.display = "none";
         setTimeout(() => event.srcElement.style.display = odisplay, 5);
      }

      if(image instanceof HTMLElement) {
         let ox: number = null;
         let oy: number = null;

         const dragOverFn = (e: DragEvent) => {
            domService.requestRead(() => {
               const pY = e.pageY;
               const pX = e.pageX;

               if(ox != pX || oy != pY) {
                  domService.requestWrite(() => {
                     ox = pX;
                     oy = pY;
                     (<HTMLElement> image).style.top = (pY + 1) + "px";
                     (<HTMLElement> image).style.left = (pX + 1) + "px";
                  });
               }
            });
         };

         return new Promise((resolve) => {
            zone.runOutsideAngular(() => {
               document.addEventListener("dragover", dragOverFn);
               const dragendFn = () => {
                  document.removeEventListener("dragover", dragOverFn);
                  document.removeEventListener("dragend", dragendFn);

                  if(image.parentElement) {
                     document.body.removeChild(image);
                  }

                  resolve();
               };
               document.addEventListener("dragend", dragendFn);
            });
         });
      }
   }

   // remove drag image (for dnd)
   export function clearDragImage() {
      let elem: Element = document.querySelector(".show-drag-field");

      if(elem) {
         document.body.removeChild(elem);
      }
   }

   // check if any parent contains clsas
   export function parentContainsClass(element: Element, cls: string): boolean {
      if(!element) {
         return false;
      }

      if(element.classList.contains(cls)) {
         return true;
      }

      const parent = element.parentElement;
      return parent && GuiTool.parentContainsClass(parent, cls);
   }

   /**
    * @return {boolean} whether we"re in an iframe
    */
   export function isIFrame(): boolean {
      try {
         return window.frameElement != null;
      }
      catch(e) {
         return window.self !== window.top;
      }
   }

   /**
    * Find node form the tree by the path.
    * @param {string} path node path
    * @param {TreeNodeModel} node treeNode
    * @returns {TreeNodeModel} If found, retunr the node, otherwise return null
    */
   export function getNodeByPath(path: string, node: TreeNodeModel): TreeNodeModel {
      if(path) {
         return findNode(node, (n) => !!n.data && n.data.path === path);
      }

      return null;
   }

   export function findNode(node: TreeNodeModel, match: (n: TreeNodeModel) => boolean): TreeNodeModel {
      if(node) {
         const queue: TreeNodeModel[] = [node];

         while(queue.length > 0) {
            const n = queue.shift();

            if(match(n)) {
               return n;
            }

            if(n.children && n.children.length > 0) {
               queue.push(...n.children);
            }
         }
      }

      return null;
   }

   export function isTouch(event: any) {
      return event.pageX == null;
   }

   export function isButton1(event: any) {
      return event.button == null || event.button == 0;
   }

   export function pageX(event: any) {
      return (event.pageX != null) ? event.pageX : (<TouchEvent>event).touches[0].pageX;
   }

   export function pageY(event: any) {
      return (event.pageY != null) ? event.pageY : (<TouchEvent>event).touches[0].pageY;
   }

   export function clientX(event: any) {
      return (event.clientX != null) ? event.clientX : (<TouchEvent>event).touches[0].clientX;
   }

   export function clientY(event: any) {
      return (event.clientY != null) ? event.clientY : (<TouchEvent>event).touches[0].clientY;
   }

   export function isIE(): boolean {
      const uA = window.navigator.userAgent;
      return /msie\s|trident\//i.test(uA);
   }

   export function isEdge(): boolean {
      const uA = window.navigator.userAgent;
      return /\sEdge\//i.test(uA);
   }

   export function isFF(): boolean {
      const uA = window.navigator.userAgent;
      return uA.toLowerCase().indexOf("firefox") >= 0;
   }

   export function isSafari(): boolean {
      const uA = window.navigator.userAgent;
      return uA.toLowerCase().indexOf("safari") >= 0 && uA.toLowerCase().indexOf("chrome") < 0;
   }

   export function isChrome(): boolean {
      const uA = window.navigator.userAgent;
      return uA.toLowerCase().indexOf("chrome") >= 0 && uA.toLowerCase().indexOf("edge") < 0;
   }

   export function isIOS(): boolean {
      const uA = window.navigator.userAgent;
      return /Safari/i.test(uA) && (/iPad;/i.test(uA) ||
         /iPod;/i.test(uA) || /iPhone;/i.test(uA));
   }

   export function getIOSVersion(): number {
      if(!isIOS()) {
         return -1;
      }

      let result = (window.navigator.appVersion).match(/OS (\d+)_(\d+)_?(\d+)?/);

      if(!!result && result.length > 1) {
         return parseInt(result[1], 10);
      }

      return -1;
   }

   /**
    * Determines if the browser is hosted on a mobile device. The implementation of this
    * function is taken from http://detectmobilebrowsers.com (public domain).
    *
    * @returns {boolean} <tt>true</tt> if a mobile device; <tt>false</tt> otherwise.
    */
   export function isMobileDevice(): boolean {
      let a: string = (window.navigator.userAgent || window.navigator.vendor || window["opera"]);
      return (/(android|bb\d+|meego).+mobile|android|ipad|playbook|silk|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4)));
   }

   export function getQueryParameters(): Map<string, string[]> {
      let query = window.location.search.substring(1);

      const params = query.split("&")
         .map((pair: string) => pair.split("="))
         .reduce((parameterMap: Map<string, string[]>, pair: string[]) => {
            if(pair[0] && pair[1]) {
               let key = decodeURIComponent(pair[0]);
               let value = decodeURIComponent(pair[1]);
               let paramsTemp: string[] = parameterMap.get(key);

               if(!paramsTemp) {
                  paramsTemp = [];
                  parameterMap.set(key, paramsTemp);
               }

               if(value.indexOf("~_") >= 0 && value.indexOf("_~") >= 0) {
                  paramsTemp.push(Tool.byteDecode(value));
               }
               else {
                  paramsTemp.push(value);
               }
            }

            return parameterMap;
         }, new Map<string, string[]>());

      const drillId: string[] = params.get("drillId");

      if(drillId && drillId.length) {
         const storage = window.sessionStorage;
         let drillParamStr = storage.getItem("__drillParameters__" + drillId[0]);

         if(drillParamStr) {
            const drillParams = JSON.parse(drillParamStr);
            setTimeout(() => storage.removeItem("__drillParameters__" + drillId[0]), 2000);

            for(const k of Object.keys(drillParams)) {
               let v = drillParams[k];

               // array will be changed at ie11 (make Array to json failure),
               // so we should create new array
               if(Array.isArray(v)) {
                  let clone = [];

                  for(let value of v) {
                     clone.push(value);
                  }

                  v = clone;
               }

               params.set(k, Array.isArray(v) ? v : [v]);
            }
         }
      }

      return params;
   }

   /**
    * Returns true if the element has an associated CSS layout box, false otherwise.
    * This is useful for detecting whether an element is being displayed in the document.
    *
    * @param element the element to check
    * @returns true if the element has a layout box, false otherwise.
    */
   export function hasLayoutBox(element: Element): boolean {
      // Per the spec, clientRects will be empty if the element has no associated layout box.
      // https://www.w3.org/TR/cssom-view-1/#dom-element-getclientrects
      return element.getClientRects().length !== 0;
   }

   export function formInvalid(control: AbstractControl): boolean {
      return control.invalid && (control.dirty || control.touched);
   }

}
