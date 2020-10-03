/* tslint:disable */

const gulp = require("gulp");
const HubRegistry = require("gulp-hub");

const hub = new HubRegistry(["gulp-tasks/*.js"]);
gulp.registry(hub);

gulp.task("default", gulp.parallel([ "metadata", "i18n" ]));
gulp.task("watch", gulp.parallel([ "metadata:watch" ]));
