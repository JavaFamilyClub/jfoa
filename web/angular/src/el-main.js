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

/* tslint:disable */

const { app, BrowserWindow } = require("electron");

// 保持对window对象的全局引用，如果不这么做的话，当JavaScript对象被
// 垃圾回收的时候，window对象将会自动的关闭
let win;

function createWindow() {
   // 创建浏览器窗口。
   win = new BrowserWindow({
      width: 800,
      height: 600,
      minWidth: 500,
      minHeight: 500,
      webPreferences: {
         nodeIntegration: true
      }
   });

   // 加载index.html文件
   win.loadFile("index.html"); // 这个路径可以根据你实际的项目目录结构进行修正

   // 打开开发者工具
   // win.webContents.openDevTools()
   // logger.info("esraeder main start at ", new Date());

   // 当 window 被关闭，这个事件会被触发。
   win.on("closed", () => {
      // 取消引用 window 对象，如果你的应用支持多窗口的话，
      // 通常会把多个 window 对象存放在一个数组里面，
      // 与此同时，你应该删除相应的元素。
      win = null;
   });
}

// Electron 会在初始化后并准备
// 创建浏览器窗口时，调用这个函数。
// 部分 API 在 ready 事件触发后才能使用。
app.on("ready", createWindow);

// 当全部窗口关闭时退出。
app.on("window-all-closed", () => {
   // 在 macOS 上，除非用户用 Cmd + Q 确定地退出，
   // 否则绝大部分应用及其菜单栏会保持激活。
   if (process.platform !== "darwin") {
      app.quit()
   }
});

app.on("activate", () => {
   // 在macOS上，当单击dock图标并且没有其他窗口打开时，
   // 通常在应用程序中重新创建一个窗口。
   if (win === null) {
      createWindow();
   }
});
