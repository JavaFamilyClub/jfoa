const gulp = require("gulp");
const HubRegistry = require("gulp-hub");

const hub = new HubRegistry(["gulp-tasks/*.js"]);
gulp.registry(hub);

gulp.task("default", gulp.parallel([ ]));