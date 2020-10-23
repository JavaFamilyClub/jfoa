const gulp = require("gulp");
const through = require("through2");
const fs = require("fs");
const path = require("path");
const File = require("vinyl");

const CharSet_UTF_8 = "utf-8";

const generateMetadata = function() {
   const searchEntries = [];
   const helpLinkEntries = [];

   function generateSearchMetadata(file) {
      const content = file.contents.toString(CharSet_UTF_8);
      const expr = /@Searchable\s*\(\s*({[^}]+})\s*\)/;
      const match = expr.exec(content);

      if(match != null) {
         const metadata = eval("(" + match[1] + ")");
         const route = metadata.route;
         const title = metadata.title;
         const keywords = metadata.keywords;

         searchEntries.push({route, title, keywords});
      }
   }

   function generateHelpLinkMetadata(file) {
      const content = file.contents.toString(CharSet_UTF_8);
      const expr = /@ContextHelp\s*\(\s*({[^}]+})\s*\)/;
      const match = expr.exec(content);

      if(match != null) {
         const metadata = eval("(" + match[1] + ")");
         const route = metadata.route;
         const link = metadata.link;

         helpLinkEntries.push({route, link});
      }
   }

   function generateFileMetadata(file, encoding, callback) {
      if(file.isNull()) {
         callback();
         return;
      }

      if(file.isStream()) {
         this.emit("error", new Error("metadata: Streaming not supported"));
         callback();
         return;
      }

      generateSearchMetadata(file);
      generateHelpLinkMetadata(file);

      return callback();
   }

   function endStream(callback) {

      this.push(new File({
         path: "admin/search-index.json",
         contents: Buffer.from(JSON.stringify({entries: searchEntries}), CharSet_UTF_8)
      }));

      this.push(new File({
         path: "admin/help-links.json",
         contents: Buffer.from(JSON.stringify({entries: helpLinkEntries}), CharSet_UTF_8)
      }));

      callback();
   }

   return through({objectMode: true}, generateFileMetadata, endStream);
};

gulp.task("metadata", function() {
   return gulp.src("src/app/**/*.component.ts")
      .pipe(generateMetadata())
      .pipe(gulp.dest("../../runner/build/resources/main/config"));
});

gulp.task("metadata:watch", gulp.series([ "metadata" ], function() {
   return gulp.watch([
         "src/app/**/*.component.ts"],
      gulp.series(["metadata"]))
}));
