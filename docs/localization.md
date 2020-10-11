# Localization
![file](https://graph.baidu.com/resource/222022bcb94a5e7a1197b01602405410.png)
![file](https://graph.baidu.com/resource/2221eff1a0f81f50509c801602405361.png)

> [jfoa](https://github.com/JavaFamilyClub/jfoa) 项目是一个 SpringBoot + Angular 整合的项目, 
> 因此本地化并不仅仅牵扯 Java 本地化, 还有 Angular 本地化.

## 1. 问题简介

### 1.1 Java 本地化

> Java 本地化技术已经很成熟了, 有多种方式, 可以借助 Spring 帮我们封装好的一些
> `MessageSource` 实现类实现, 也可以自行使用 `ResourceBundle` 来实现.
> 我们这里使用 `ResourceBundle` 自行封装一个 Util 来实现, 这样不会依赖于 Spring IOC 容器.

### 1.2 Thymeleaf 本地化

> 由于项目中也使用到了 Thymeleaf ---- 登录, 注册, 错误页面, 邮件模板等, 因此也需要对
> Thymeleaf 页面进行本地化支持, Thymeleaf 提供了依赖 Spring 的本地化支持, 
> 大家可参考[官方文档](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html#listing-seed-starter-data)

### 1.3 Angular 本地化

> 本地化最重要的其实是前端页面的本地化, 在 `jfoa` 中, 其实就是 Angular 的本地化, 
> Angular 官方提供了本地化支持, 大家可以参考[Angular 官方文档](https://angular.cn/guide/i18n)
> 
> 另外, 也有一些优秀的开源库对 Angular 本地化进行支持, 比如: [ngx-translate](https://github.com/ngx-translate/core)

#### 1.3.1 Angular 官方本地化解决方案

Angular 官方给出的本地化方案支持三种文件格式:

* XLIFF 1.2(默认) --- `.xlf`
* XLIFF 2 --- `xlf2`
* XML 消息包 --- `.xmb`

默认的文件格式`xlf`基本格式为

```xml
<trans-unit id="introductionHeader" datatype="html">
  <source>Hello i18n!</source>
  <note priority="1" from="description">An introduction header for this sample</note>
  <note priority="1" from="meaning">User welcome</note>
</trans-unit>
```

而页面中本地化格式为:

```angular2html
<h1 i18n="User welcome|An introduction header for this sample@@introductionHeader">
  Hello i18n!
</h1>
```

> 看着这里是不是头都大了, 的确, Angular 官方给出的本地化方案非常灵活, 但是文件结构也相对复杂,
> 因此, 帅帅寻找了另一种 Angular 本地化库, 也就是上边提到的 --- `ngx-translate`.

#### 1.3.2 ngx-translate --- Angular 本地化

> 相比 Angular 官方的本地化方案, `ngx-translate` 使用 json 作为本地化文件格式, 使用也很简单

* 本地化文件格式
```json
{
   "Support": "Support",
   "Home": "Home",
   "user": {
      "profile": {
         "Profile": "Profile",
         "Gender": "Gender",
         "ph": {
            "name": "Please enter your user name",
            "account": "Please enter your account"
         }
      }
   }
}
```

> 类似 yml 文件格式, 支持多层嵌套. 引用时每层以 `.` 拼接. e.g. 本地化 `Gender`
> 就可以通过引用 `user.profile.Gender`

* 页面本地化格式
```angular2html
<label>{{'user.profile.Gender' | translate}}:</label>
```

* ts 本地化格式

```typescript
this.translateService.instant("em.audit.errorDetail")
```

> `ngx-translate` 为我们提供了一个 `TranslateService` 实例, 该实例可以直接
> 注入, 提供了在 ts 中本地化的支持, 以及一些常用方法, 比如获取浏览器默认的 `locale`
> 环境: 
> `this.translateService.getBrowserLang()`, 
> 切换 `locale`: 
> `this.translateService.use(lang);`

### 1.4 面临的问题

> 每个部分的本地化其实都比较容易解决, 但是当多个部分组合都一起时就需要寻找一个
> 合理, 低冗余, 易维护的解决方案. 因此, 一个基本的原则就是:
>
> `为了以后好维护, 和没有冗余数据, 我们希望整个项目仅仅存在一份被维护的本地化文件`

> 因此, 我们不能简单的 Server 端存一份 `properties` 本地化, 前端存在一份本地化文件.

## 2 解决思路

> 为了以后好维护, 和没有冗余数据, 我们希望整个项目仅仅存在一份被维护的本地化文件

### 2.1 Server 端优先

* 如果本地化文件存在 Server 端, 那我们可以直接创建 `.properties` 文件存在 Server, 
剩下的问题也就是如何用 Server 端的本地化文件本地化 Angular.

* 而项目运行中实际上使用的是 Angular 遍历后的 `js` 等文件. 因此问题也就转化为使用 
Server 端 `properties` 文件本地化 Angular 编译输出文件.

* 我们可以以一种约定好的方式在 Angular 项目中引用 Server 端的本地化 key(比如: 
Angular 中使用 `_#(js: xxxx)` 来代表引用本地化文件的key---`xxx`, 当然, 
Angular 并不会自动的本地化), 然后在 Server 启动时对 Angular 编译后的文件进行扫描,
发现我们的标记 `_#(js: xxxx)` 时就去相应 `locale` 的本地化文件中寻找 key, 
从而替换为 value.

> 这是一种解决方案, 这样就只需要维护 Server 端的本地化文件, 但是, 为此相当于我们
> 自己实现了一套 Angular 本地化的框架, 不仅需要我们对 Angular 编译后的文件格式特
> 别了解, 而且还可能面临 Angular 升级格式不兼容的可能性.

### 2.2 云配置

> 这是帅帅的一个想法, 并没有亲自尝试, 想法源于 `Spring Cloud Configuration` 的思想,
> 也就是在云端保存本地化文件(比如: GitHub 上), Server 和 Web 都从云端获取本地化文件. 

> 这应该也是一种解决方案, 但是如果项目现在并没有 Spring Cloud 环境, 那么也就需要我们
> 自行手动去写一套逻辑, 最简单的可以使用 HTTPClient get 一份, 但是当本地化文件更新后
> 项目何时重新去获取也就是一个问题了, 最简单的也就是每次启动 Server 都舍弃旧的, 重新获
> 取. 至于 Angular 如果使用 `ngx-translate` 本身就是支持 http 获取本地化文件的.

> 这样的话还需要对获取到的文件进行一次处理, 因为 Java 需要 `properties` 文件
> 格式, Angular 需要 `json` 文件格式.

### 2.3 [gulp](https://www.gulpjs.com.cn/) 穿针引线

> 最后帅帅还想到一种解决方案, 也就是现在 `jfoa` 使用的解决方案, Angular 使用 
> `ngx-translate` 本地化文件存在 Angular 中(这也很合理, 因为本地化大部分发生在
> 前端页面), 通过 `gulp` 将本地化 `.json` 文件转化为 `.properties` 挂载到 Angular
> 的编译生命周期上, 一同写给Server.
> 基本流程是:

![file](https://graph.baidu.com/resource/2228dde3e21a4f12a85bc01602404729.png)

## 3 实施

> 至于如何实施, 大家可以参考帅帅的项目 [jfoa](https://github.com/JavaFamilyClub/jfoa),
> 这里只给出 gulp 转化的逻辑 `i18n.js`:

```js
const gulp = require("gulp");
const through2 = require("through2");
const File = require("vinyl");
const filter = require("gulp-filter");

const CharSet_UTF_8 = "utf-8";

const generateI18ns = function() {
   const enI18nMap = new Map();
   const zhI18nMap = new Map();
   const resultMap = new Map();

   function getMap(fileName) {
      if(fileName === "zh") {
         return zhI18nMap;
      }

      return enI18nMap;
   }

   function parseAddObj(obj, base, fileName) {
      const i18nMap = getMap(fileName);

      for(let k in obj) {
         let value = obj[k];
         let key = !!base ? base + "." + k : k;

         if(typeof value === "string") {
            i18nMap.set(key, value);
         }
         else if(typeof value === "object") {
            parseAddObj(value, key, fileName);
         }
      }
   }

   function toUnicode(s) {
      return s.replace(/([\u4E00-\u9FA5]|[\uFE30-\uFFA0])/g, function(s) {
         return "\\u" + s.charCodeAt(0).toString(16);
      });
   }

   function encodeValue(str) {
      return toUnicode(str.replace(/\n/g, "\\n"));
   }

   function encodeKey(str) {
      return str.replace(/ /g, "\\ ");
   }

   function generateI18nMessage(file, _, cb) {
      // let fileName = file.basename; // has suffix(en.json)
      let fileName = file.stem; // no suffix(en)
      let content = file.contents.toString(CharSet_UTF_8);
      const obj = JSON.parse(content);

      parseAddObj(obj, "", fileName);
      const i18nMap = getMap(fileName);
      content = "";

      i18nMap.forEach((v, k) => {
         content += (encodeKey(k) + "=" + encodeValue(v) + "\n");
      });

      // file.contents = Buffer.from(content, "ascii");
      resultMap.set(fileName, content);

      cb(null, file);
   }

   function endStream(callback) {
      let content = resultMap.get("en");

      if(!!content) {
         this.push(new File({
            path: "messages.properties",
            contents: Buffer.from(content, CharSet_UTF_8)
         }));

         this.push(new File({
            path: "messages_en_US.properties",
            contents: Buffer.from(content, CharSet_UTF_8)
         }));
      }

      content = resultMap.get("zh");

      if(!!content) {
         this.push(new File({
            path: "messages_zh_CN.properties",
            contents: Buffer.from(content, CharSet_UTF_8)
         }));
      }

      callback();
   }

   return through2.obj({objectMode: true}, generateI18nMessage, endStream);
};

gulp.task("i18n", function() {
   const fileFilter = filter('**/*.properties', {restore: true});

   return gulp.src("src/assets/i18n/*.json")
      .pipe(generateI18ns())
      .pipe(fileFilter)
      .pipe(gulp.dest("../../runner/build/resources/main/i18n"));
});

gulp.task("i18n:watch", gulp.series([ "i18n" ], function() {
   return gulp.watch(["src/assets/i18n/*.json"],
      gulp.series(["i18n"]))
}));
```

​## 每文一骚

> It is not our abilities that show what web truly are, it is our choices.
> 决定我们成为什么样人的, 不是我们的能力, 而是我们的抉择.

> PS: <<秦时明月之沧海横流>> 更新至第四集, 那么, 谁才是`惊鲵`?

![file](https://graph.baidu.com/resource/2225938f06159cccb280101602405439.png)
