const gulp = require("gulp");
const through2 = require("through2");
const File = require("vinyl");
const filter = require("gulp-filter");
const utf8Convert = require("gulp-utf8-convert");
const rename = require("gulp-rename");

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

   function encode(str) {
      str = toUnicode(str.replace(/\n/g, "\\n"));
      return str;
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
         content += (encodeKey(k) + "=" + encode(v) + "\n");
      });

      // file.contents = Buffer.from(content, "ascii");
      resultMap.set(fileName, content);

      cb(null, file);
   }

   function endStream(callback) {
      let content = resultMap.get("en");

      if(!!content) {
         this.push(new File({
            path: "messages.txt",
            contents: Buffer.from(content, CharSet_UTF_8)
         }));

         this.push(new File({
            path: "messages_en_US.txt",
            contents: Buffer.from(content, CharSet_UTF_8)
         }));
      }

      content = resultMap.get("zh");

      if(!!content) {
         this.push(new File({
            path: "messages_zh_CN.txt",
            contents: Buffer.from(content, CharSet_UTF_8)
         }));
      }

      callback();
   }

   return through2.obj({objectMode: true}, generateI18nMessage, endStream);
};

gulp.task("i18n", function() {
   const fileFilter = filter('**/*.txt', {restore: true});

   return gulp.src("src/assets/i18n/*.json")
      .pipe(generateI18ns())
      .pipe(utf8Convert())
      .pipe(fileFilter)
      .pipe(rename(function(path) {
         return {
            dirname: path.dirname,
            basename: path.basename,
            extname: ".properties"
         };
      }))
      .pipe(gulp.dest("../../runner/build/resources/main/i18n"));
});

gulp.task("i18n:watch", gulp.series([ "i18n" ], function() {
   return gulp.watch(["src/assets/i18n/*.json"],
      gulp.series(["i18n"]))
}));
