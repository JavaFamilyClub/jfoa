const gulp = require("gulp");
const svgicons2svgfont = require("gulp-svgicons2svgfont");
const svg2ttf = require("gulp-svg2ttf");
const ttf2eot = require("gulp-ttf2eot");
const ttf2woff = require("gulp-ttf2woff");
const template = require("gulp-template");
const rename = require("gulp-rename");
const sort = require("gulp-sort");
const through = require("through2");
const md5 = require("md5");
const sprintf = require("sprintf-js").sprintf;
const xml2js = require("xml2js");
const base32 = require("base32");
const del = require("del");

const fixFontFile = function() {
   return through({objectMode: true}, function(file, encoding, callback) {
      const newFile = file.clone();
      newFile.path = "jfoa.svg";
      this.push(newFile);
      callback();
   });
};

const generateSassVariables = function() {
   function generate(file, encoding, callback) {
      const hash = base32.encode(md5(file.contents, {asBytes: true}));
      xml2js.parseString(file.contents, function(err, result) {
         const glyphs = result.svg.defs[0].font[0].glyph.map(element => {
            return {
               name: "icon-" + element["$"]["glyph-name"],
               code: element["$"].unicode.charCodeAt(0),
               codeString: sprintf("%04x", element["$"].unicode.charCodeAt(0))
            };
         }).sort((g1, g2) => g1.code - g2.code);
         const data = { hash, glyphs };
         gulp.src("gulp-tasks/templates/variables.scss.template")
            .pipe(template(data))
            .pipe(rename("variables.scss"))
            .pipe(gulp.dest("src/assets/icons"));
         callback();
      });
   }

   return through({objectMode: true}, generate);
};

gulp.task("fonts:clean-icons", function() {
   return del("build/temp/icons/*")
});

gulp.task("fonts:stage-icons", function() {
   let codePoint = 0xea01;
   return gulp.src("src/assets/icons/icons_svg/*.svg")
      .pipe(sort({
         comparator: (file1, file2) => file1.path.toLowerCase().localeCompare(file2.path.toLowerCase())
      }))
      .pipe(rename(path => {
         path.basename = sprintf("u%04X-", codePoint++) + path.basename
      }))
      .pipe(gulp.dest("build/temp/icons"));
});

gulp.task("fonts:generate:svg", function() {
   return svgicons2svgfont(["build/temp/icons/*.svg"], {
      fontName: "jfoa",
      startUnicode: 0xea01,
      fontHeight: 5000,
      normalize: true
   })
      .pipe(fixFontFile())
      .pipe(gulp.dest("src/assets/icons/fonts"));
});

gulp.task("fonts:generate:variables", function() {
   return gulp.src("src/assets/icons/fonts/jfoa.svg")
      .pipe(generateSassVariables());
});

gulp.task("fonts:generate:ttf", function() {
   return gulp.src("src/assets/icons/fonts/*.svg")
      .pipe(svg2ttf())
      .pipe(gulp.dest("src/assets/icons/fonts"));
});

gulp.task("fonts:generate:eot", function() {
   return gulp.src("src/assets/icons/fonts/*.ttf")
      .pipe(ttf2eot())
      .pipe(gulp.dest("src/assets/icons/fonts"))
});

gulp.task("fonts:generate:woff", function() {
   return gulp.src("src/assets/icons/fonts/*.ttf")
      .pipe(ttf2woff())
      .pipe(gulp.dest("src/assets/icons/fonts"))
});

gulp.task("fonts:generate", gulp.series([
   "fonts:clean-icons", "fonts:stage-icons", "fonts:generate:svg", "fonts:generate:variables",
   "fonts:generate:ttf", "fonts:generate:woff", "fonts:generate:eot"
]));
