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

import {
   Component,
   ElementRef,
   EventEmitter,
   OnDestroy,
   OnInit,
   Output,
   ViewChild
} from "@angular/core";
import { Subscription } from "rxjs";
import { DownloadService } from "./download.service";
import { HttpClient } from "@angular/common/http";

@Component({
   selector: "dl-download-target",
   templateUrl: "download-target.component.html",
   styleUrls: ["download-target.component.scss"]
})
export class DownloadTargetComponent implements OnInit, OnDestroy {
   @Output() downloadStarted = new EventEmitter<string>();
   @ViewChild("frame") frame: ElementRef;
   private subscription: Subscription;
   private contentSource: string;
   private reloadCounter = 1;

   constructor(private downloadService: DownloadService, private http: HttpClient) {
   }

   ngOnInit(): void {
      this.subscription = this.downloadService.url.subscribe((url) => {
         if(url) {
            const match = new RegExp("checkForResponse=([^&]+)").exec(url);
            const checkForResponse = match ? match[1] === "true" : false;

            if(this.contentSource === url) {
               let reloadUrl = url;

               if(url.indexOf("?") >= 0) {
                  // assume that there's an existing query string
                  reloadUrl += "&";
               }
               else {
                  reloadUrl += "?";
               }

               reloadUrl += "downloadServiceReloadCounter=" + (this.reloadCounter++);
               this.download(reloadUrl, checkForResponse);
            }
            else {
               this.contentSource = url;
               this.reloadCounter = 1;
               this.download(url, checkForResponse);
            }

            this.downloadStarted.emit(url);
         }
      });
   }

   download(url: string, checkForResponse: boolean) {
      if(checkForResponse) {
         this.http.get(url, {observe: "response"}).subscribe(
            (response) => {
               if(response) {
                  this.frame.nativeElement.setAttribute("src", url);
               }
            },
            (error) => {
               if(error.status == 200) {
                  this.frame.nativeElement.setAttribute("src", url);
               }
               else {
                  alert(error.error.message);
               }
            }
         );
      }
      else {
         this.frame.nativeElement.setAttribute("src", url);
      }
   }

   ngOnDestroy(): void {
      if(this.subscription) {
         this.subscription.unsubscribe();
         this.subscription = null;
      }
   }
}
