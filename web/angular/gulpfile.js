/* tslint:disable */

const gulp = require("gulp");
const HubRegistry = require("gulp-hub");

const hub = new HubRegistry(["gulp-tasks/*.js"]);
gulp.registry(hub);

gulp.task("default", gulp.parallel([ "metadata", "i18n", "fonts:generate" ]));
gulp.task("watch", gulp.parallel([ "metadata:watch", "i18n:watch" ]));
