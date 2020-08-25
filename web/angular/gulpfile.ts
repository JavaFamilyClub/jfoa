const gulp = require("gulp");
const HubRegistry = require("gulp-hub");

const hub = new HubRegistry(["gulp-tasks/*.ts"]);
gulp.registry(hub);

exports.buildSearchDoc = buildSearchDoc;

exports.default = gulp.parallel([ buildSearchDoc ]);
