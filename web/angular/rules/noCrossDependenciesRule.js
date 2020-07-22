"use strict";
/*
 * Copyright (c) 2019, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to AngBoot Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var Lint = require("tslint");
var Rule = /** @class */ (function (_super) {
    __extends(Rule, _super);
    function Rule() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    Rule.prototype.apply = function (sourceFile) {
        return this.applyWithWalker(new NoCrossDependenciesWalker(sourceFile, this.getOptions()));
    };
    Rule.FAILURE_MSG = "cross-app dependency forbidden";
    Rule.PROHIBITED_DEPENDENCIES = {
        "common/": [],
        "widget/": []
    };
    return Rule;
}(Lint.Rules.AbstractRule));
exports.Rule = Rule;
var NoCrossDependenciesWalker = /** @class */ (function (_super) {
    __extends(NoCrossDependenciesWalker, _super);
    function NoCrossDependenciesWalker() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    NoCrossDependenciesWalker.prototype.visitImportDeclaration = function (node) {
        // let sourceFile: ts.SourceFile = <ts.SourceFile> node.parent;
        // let importFrom: string = <string> node.moduleSpecifier["text"];
        //
        // for(let prefix in Rule.PROHIBITED_DEPENDENCIES) {
        //    if(sourceFile.fileName.indexOf(prefix) === 0) {
        //       for(let prohibited of Rule.PROHIBITED_DEPENDENCIES[prefix]) {
        //          if(importFrom.indexOf(prohibited) >= 0) {
        //             this.addFailure(this.createFailure(node.getStart(), node.getWidth(), Rule.FAILURE_MSG));
        //          }
        //       }
        //
        //       break;
        //    }
        // }
        _super.prototype.visitImportDeclaration.call(this, node);
    };
    return NoCrossDependenciesWalker;
}(Lint.RuleWalker));
//# sourceMappingURL=noCrossDependenciesRule.js.map