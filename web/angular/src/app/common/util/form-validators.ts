import {
   AbstractControl,
   FormControl,
   FormGroup,
   ValidationErrors,
   ValidatorFn
} from "@angular/forms";
import { Tool } from "./tool";
import { Injectable } from "@angular/core";

// copied from Angular's validators
const EMAIL_REGEXP =
   /^(?=.{1,254}$)(?=.{1,64}@)[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+(\.[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+)*@[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\.[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*$/;

export class FormValidators {
   public static DATASOURCE_NAME_REGEXP = /^[^\\\/?"*:<>|. ^]+[^\\\/?"*:<>|.^]*$/;

   public static passwordComplexity(control: AbstractControl): ValidationErrors | null {
      if(control.value) {
         if(control.value.length < 8 || !/[A-Za-z]/g.test(control.value) ||
            !/[0-9]/g.test(control.value))
         {
            return { passwordComplexity: true };
         }
      }

      return null;
   }

   public static required(control: FormControl): ValidationErrors {
      const str = control.value;

      if(!str || !str.trim()) {
         return { required: true };
      }

      return null;
   }

   public static requiredNumber(control: FormControl): ValidationErrors {
      // 0 is valid.
      if(!Tool.isNumber(control.value)) {
         return { requiredNumber: true };
      }

      return null;
   }

   public static containsSpecialChars(control: FormControl): ValidationErrors {
      if(/[~`!#$%^&*+=\-\[\]\\';,./{}|":<>?_()]/g.test(control.value)) {
         return {containsSpecialChars: true};
      }

      return null;
   }

   public static containsDashboardSpecialCharsForName(control: FormControl): ValidationErrors {
      if(/[~`!#%^*=\[\]\\';,./{}|":<>?()]/g.test(control.value)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   /**
    * Same as {@link containsDashboardSpecialCharsForName} except allow periods for email addresses
    */
   public static validUserName(control: FormControl): ValidationErrors {
      if(/[~`!#%^*=\[\]\\';,/{}|":<>?()]/g.test(control.value)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static invalidAssetItemName(control: FormControl): ValidationErrors {
      const str = control.value;
      let validName: boolean = str && /^[^\\\/"<'%^]+$/.test(str);

      return !validName ? {invalidAssetItemName: true} : null;
   }

   public static mustBeValidReportIdentifier(control: FormControl): ValidationErrors {
      const value: string = control.value;

      if(value && value.indexOf("{") == -1 && value.indexOf("}") == -1 &&
         /[~`!#%^*=\[\]\\';,./|":<>?()]/g.test(value)) {
         return {containsInvalidCharacter: true};
      }

      return null;
   }

   public static containsSpecialCharsForName(control: FormControl): ValidationErrors {
      if(/[~`!#%^&*+=\-\[\]\\';,./{}|":<>?()]/g.test(control.value) ||
         control.value.split("").some(c => c.charCodeAt(0) > 127)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static invalidTaskName(control: FormControl): ValidationErrors {
      if(!!control && !!control.value &&
         !/^[A-Za-zÀ-ÿ0-9$ &?#!*`;>|~={}()@+_:.\-\[\]\u4e00-\u9fa5]+$/.test(control.value))
      {
         return {invalidTaskName: true};
      }

      return null;
   }

   public static isValidWindowsFileName(control: FormControl): ValidationErrors {
      let validWindowsFileChars = /^[^\\/:*?"<>|]+$/;
      let startsWithDot = /^\s*\./;

      if(control.value &&
         (!validWindowsFileChars.test(control.value) || startsWithDot.test(control.value))) {
         return {containsInvalidWindowsChars: true};
      }

      return null;
   }

   public static isValidFileNameAndXMLSafe(control: FormControl): ValidationErrors {
      let containsInvalid = /^[^*\[:\\<>?|#'%\/,&\]^"]+$/;

      if(control.value && !containsInvalid.test(control.value)) {
         return {containsInvalidForFileAndXML: true};
      }

      return null;
   }

   public static isValidReportName(control: FormControl): ValidationErrors {
      if(control.value && !/^[a-zA-Z0-9$\-&@+_\u4e00-\u9fa5 ]+$/.test(control.value)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static isInvalidBackupName(control: FormControl): ValidationErrors {
      if(control.value && !/^[^#%^&*\[\]|\\'":,<>\/?]*$/.test(control.value)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static isValidDataSpaceName(control: FormControl): ValidationErrors {
      if(control.value && !/^[a-zA-Z0-9$\-&@_.]+$/.test(control.value)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static isValidDataSpaceFileName(control: FormControl): ValidationErrors {
      if(control.value && (/[*+:"<>?|\\#'/%,]/g.test(control.value) ||
         control.value.lastIndexOf(".") === control.value.length - 1 ||
         (control.value.indexOf(".") === 0 && control.value !== ".stylereport"))) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static isValidPrototypeName(control: FormControl): ValidationErrors {
      if(control.value && /[*$&:+<>?|\\#'/%,."\[\]]/g.test(control.value)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static isValidTemplateName(templateUpload: boolean): ValidatorFn {
      return (control) => {
         return FormValidators.checkTemplateName(templateUpload, control.value);
      };
   }

   public static checkTemplateName(templateUpload: boolean, value: any): ValidationErrors | null {
      if(value && /^[*]+$/.test(value)) {
         return {containsSpecialCharsForName: true};
      }

      const length = value ? value.length : 0;

      if(length < 5 ||
         (value.substring(length - 4, length).toLowerCase() != ".srt" &&
            value.substring(length - 4, length).toLowerCase() != ".xml"))
      {
         return templateUpload ? {illegalUploadTemplateName: true} :
            {illegalTextTemplateName: true};
      }

      if(!templateUpload) {
         if(value.charAt(0) != "/") {
            return {notStartWithBackslashTemplate: true};
         }

         if(value == "/") {
            return {illegalTextTemplateName: true};
         }
      }

      return null;
   }

   public static isValidScreenshotName(screenshotUpload: boolean): ValidatorFn {
      return (control) => {
         const length = control.value.length;

         if(length < 5) {
            return screenshotUpload ? {illegalUploadScreenshotName: true} :
               {illegalTextScreenshotName: true};
         }
         else {
            const ext = control.value.substring(length - 4, length).toLowerCase();

            if(!(ext == ".png" || ext == ".jpg" || ext == ".gif" || ext == ".bmp")) {
               return screenshotUpload ? {illegalUploadScreenshotName: true} :
                  {illegalTextScreenshotName: true};
            }
         }

         if(!screenshotUpload) {
            if(control.value.charAt(0) != "/") {
               return {notStartWithBackslashScreenshot: true};
            }

            if(control.value == "/") {
               return {illegalTextScreenshotName: true};
            }
         }

         return null;
      };
   }

   public static isValidDataSourceName(control: FormControl): ValidationErrors {
      const str = control.value;

      if(str && !FormValidators.DATASOURCE_NAME_REGEXP.test(str)) {
         return {containsSpecialCharsForName: true};
      }

      return null;
   }

   public static positiveNonZeroOrNull(control: FormControl): ValidationErrors {
      if(control.value != null && control.value + "" != "" &&
         (control.value <= 0 || control.value > 2147483647)) {
         return {lessThanEqualToZero: true};
      }

      return null;
   }

   public static positiveNonZeroInRange(control: FormControl): ValidationErrors {
      if(isNaN(Number(control.value)) || control.value <= 0 || control.value > 2147483647) {
         return {positiveNonZeroInRange: true};
      }

      return null;
   }

   public static positiveNonZeroIntegerInRange(control: FormControl): ValidationErrors {
      if(isNaN(Number(control.value)) || control.value <= 0
         || control.value > 2147483647 || /\./g.test(control.value))
      {
         return {lessThanEqualToZero: true};
      }

      return null;
   }

   public static positiveIntegerInRange(control: FormControl): ValidationErrors {
      if(isNaN(Number(control.value)) || control.value < 0 || control.value > 2147483647) {
         return {positiveIntegerInRange: true};
      }

      return null;
   }

   private static numberInRangeTemplate(max: number, min: number, error: ValidationErrors,
                                        split: boolean = false, delimiter: string = ",",
                                        ignoreWhitespace: boolean = true
                                        ): (FormControl) => ValidationErrors | null
   {
      return (control: FormControl) => {
         if (control.value != null) {
            let values: string[] = split ? control.value.toString().split(delimiter) : [control.value];

            if (values.map((value) => +(ignoreWhitespace ? value.toString().trim() : value))
               .some((value) => isNaN(Number(value)) || value < min || value > max)) {
               return error;
            }
         }

         return null;
      };
   }

   public static integerInRange(split: boolean = false, delimiter: string = ",",
                                ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.numberInRangeTemplate(2147483647, -2147483648, {integerInRange: true},
         split, delimiter, ignoreWhitespace);
   }

   public static shortInRange(split: boolean = false, delimiter: string = ",",
                              ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.numberInRangeTemplate(32767, -32768, {shortInRange: true},
         split, delimiter, ignoreWhitespace);
   }

   public static byteInRange(split: boolean = false, delimiter: string = ",",
                             ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.numberInRangeTemplate(127, -128, {byteInRange: true},
         split, delimiter, ignoreWhitespace);
   }

   private static isTypeDataTemplate(pattern: RegExp, error: ValidationErrors, split: boolean = false,
                                     delimiter: string = ",", ignoreWhitespace: boolean = true
                                     ): (FormControl) => ValidationErrors | null
   {
      return (control) => {
         if (control.value != null) {
            let values: string[] = split ? control.value.toString().split(delimiter) : [control.value];

            if(values.map((value) => ignoreWhitespace ? value.toString().trim() : value)
               .some((value) => !pattern.test(value)))
            {
               return error;
            }
         }

         return null;
      };
   }

   public static isInteger(split: boolean = false, delimiter: string = ",",
                           ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.isTypeDataTemplate(/^[-+]?[0-9]+$/, {isInteger: true}, split,
         delimiter, ignoreWhitespace);
   }

   public static isFloat(split: boolean = false, delimiter: string = ",",
                         ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.isTypeDataTemplate(/^\d*(\.\d*)?$/, {isFloat: true},
         split, delimiter, ignoreWhitespace);
   }

   public static isFloatNumber(split: boolean = false, delimiter: string = ",",
                               ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.isTypeDataTemplate(/^[-+]?\d*(\.\d*)?$/, {isFloatNumber: true},
         split, delimiter, ignoreWhitespace);
   }

   public static isDate(split: boolean = false, delimiter: string = ",",
                        ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      // This regular expression validates the format and data, such as 2019-13-1 is illegal
      return FormValidators.isTypeDataTemplate(/^(?:(?!0000)[0-9]{4}(-?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)(-?)0?2\2(?:29))$/,
         {isDate: true}, split, delimiter, ignoreWhitespace);
   }

   public static isTime(split: boolean = false, delimiter: string = ",",
                        ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      // This regular expression validates the format and data, such as 12:00:60 is illegal
      return FormValidators.isTypeDataTemplate(/^([01]?[0-9]|2[0-3]):[0-5]?[0-9]:[0-5]?[0-9]$/,
         {isTime: true}, split, delimiter, ignoreWhitespace);
   }

   public static isDateTime(split: boolean = false, delimiter: string = ",",
                            ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      // This regular expression validates the format and data, such as 2019-13-1 is illegal
      return FormValidators.isTypeDataTemplate(/^(?:(?!0000)[0-9]{4}(-?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)(-?)0?2\2(?:29))([T|\s+])([01]?[0-9]|2[0-3]):[0-5]?[0-9]:[0-5]?[0-9]$/,
         {isDateTime: true}, split, delimiter, ignoreWhitespace);
   }

   public static isBoolean(split: boolean = false, delimiter: string = ",",
                           ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return FormValidators.isTypeDataTemplate(/^(true|false)$/, {isBoolean: true}, split,
         delimiter, ignoreWhitespace);
   }

   public static doesNotStartWithNumber(control: FormControl): ValidationErrors {
      if(!!control.value && /^\d.*$/.test(control.value)) {
         return {doesNotStartWithNumber: true};
      }

      return null;
   }

   public static doesNotStartWithNumberOrLetter(control: FormControl): ValidationErrors {
      if(!!control.value && /^[\da-zA-Z].*$/.test(control.value)) {
         return null;
      }

      if(!control.value) {
         return null;
      }

      return {doesNotStartWithNumberOrLetter: true};
   }

   public static notWhiteSpace(control: FormControl): ValidationErrors {
      if(!!control.value && /^\s+$/.test(control.value)) {
         return {notWhiteSpace: true};
      }

      return null;
   }

   public static variableSpecialCharacters(control: FormControl): ValidationErrors {
      if(!!control.value && !/^[\w\uFF00-\uFFEF\u4e00-\u9fa5@$&+\- ]*$/.test(control.value)) {
         return {variableSpecialCharacters: true};
      }

      return null;
   }

   public static alphanumericalCharacters(control: FormControl): ValidationErrors {
      if(!!control.value && !/^[a-zA-Z0-9]*$/.test(control.value)) {
         return {alphanumericalCharacters: true};
      }

      return null;
   }

   public static bookmarkSpecialCharacters(control: FormControl): ValidationErrors {
      const dtReg: RegExp = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\w*$/;
      const dateReg: RegExp = /^\d{4}-\d{2}-\d{2}\w*$/;
      const timeReg: RegExp = /^\d{2}:\d{2}:\d{2}\w*$/;
      const valid: boolean = dtReg.test(control.value) || dateReg.test(control.value) ||
         timeReg.test(control.value);

      const bookmarkSpecialChars: boolean = !valid ?
         !(/^[a-zA-Z0-9\u4e00-\u9fa5@$& _+\-]*$/.test(control.value)) : false;

      return bookmarkSpecialChars ? {bookmarkSpecialCharacters: true} : null;
   }

   public static emailSpecialCharacters(control: FormControl): ValidationErrors {
      if(!!control.value && !/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(control.value)) {
         return {emailSpecialCharacters: true};
      }

      return null;
   }

   public static firstLetter(control: FormControl): ValidationErrors {
      if(!!control && /^[^a-zA-Z0-9 ].*$/.test(control.value)) {
         return {firstLetter: true};
      }

      return null;
   }

   public static nameSpecialCharacters2(control: FormControl): ValidationErrors {
      if(control && FormValidators.matchNameSpecialCharacters2(control.value)) {
         return {nameSpecialCharacters2: true};
      }

      return null;
   }

   public static calcSpecialCharacters(control: FormControl): ValidationErrors {
      if(control && FormValidators.matchCalcSpecialCharacters(control.value)) {
         return {calcSpecialCharacters: true};
      }

      return null;
   }

   private static matchNameSpecialCharacters2(str: string): boolean {
      if(str) {
         if(!/^[\uFF00-\uFFEF\u4e00-\u9fa5a-zA-Z0-9 $#_%&\-,?!@']*$/.test(str)) {
            return true;
         }

         // we have special handling for name surrounded by ' and it will cause problem
         if(str.startsWith("'") && str.endsWith("'")) {
            return true;
         }
      }

      return false;
   }

   public static matchCalcSpecialCharacters(str: string): boolean {
      if(str) {
         if(!/^[\uFF00-\uFFEF\u4e00-\u9fa5a-zA-Z0-9 #_&\-.!%]*$/.test(str)) {
            return true;
         }

         // we have special handling for name surrounded by ' and it will cause problem
         if(str.startsWith("'") && str.endsWith("'")) {
            return true;
         }
      }

      return false;
   }

   /**
    * check chinese colon and question mark.
    */
   public static nameSpecialCharacters(control: FormControl): ValidationErrors {
      if(!!control && /^.*([\uff1a|[\uff1f])$/.test(control.value)) {
         return {nameSpecialCharacters: true};
      }

      return null;
   }

   public static assetEntryBannedCharacters(control: FormControl): ValidationErrors {
      const name: string = control.value;
      const pattern = /[\\\/"<%^]/;
      const containsSpecialChars: boolean = name ?
         pattern.test(name) : false;

      return containsSpecialChars ? {assetEntryBannedCharacters: pattern.exec(name)} : null;
   }

   /**
    * Returns a ValidatorFn which checks if the control value is found in names.
    *
    * @param names the strings to compare the control value to.
    * @param existsOptions the extra validation options
    */
   public static exists(names: string[], existsOptions: ExistsOptions = {}): ValidatorFn {
      return (control: FormControl): ValidationErrors => {
         const value: string = existsOptions.trimSurroundingWhitespace && control.value != null
            ? control.value.trim() : control.value;
         let exists: boolean;

         if(existsOptions.hasOwnProperty("originalValue") &&
            existsOptions.originalValue === value) {
            exists = false;
         }
         else if(existsOptions.ignoreCase) {
            exists = !!names
               && names.findIndex((name) => FormValidators.equalsIgnoreCase(name, value)) !== -1;
         }
         else {
            exists = names && names.indexOf(value) !== -1;
         }

         return exists ? {exists: true} : null;
      };
   }

   /** Validator for table identifiers adapted from AlertManager.as */
   public static tableIdentifier(control: FormControl): ValidationErrors {
      if(/[\\/:*?"<>|.'&%,`~!@#=\-+(){}\[\]^;]/g.test(control.value)) {
         return {tableIdentifier: true};
      }

      return null;
   }

   public static smallerThan(min: string, max: string, orEqualTo = true): ValidatorFn {
      return (group: FormGroup): ValidationErrors => {
         let minimum = group.controls[min];
         let maximum = group.controls[max];

         if(minimum.value == null || maximum.value == null) {
            return null;
         }

         if(orEqualTo) {
            return minimum.value >= maximum.value ? {greaterThan: true} : null;
         }
         else {
            return minimum.value > maximum.value ? {greaterThan: true} : null;
         }
      };
   }

   public static dateSmallerThan(min: string, max: string): ValidatorFn {
      return (group: FormGroup): ValidationErrors => {
         let minimum: any = group.controls[min].value;
         let maximum: any = group.controls[max].value;

         if(group === null) {
            return null;
         }

         if(minimum && maximum) {
            let minDate: Date = new Date(minimum.year, minimum.month, minimum.day, 0, 0, 0, 0);
            let maxDate: Date = new Date(maximum.year, maximum.month, maximum.day, 0, 0, 0, 0);

            if(minimum.year && maximum.year) {
               minDate = new Date(minimum.year, minimum.month, minimum.day, 0, 0, 0, 0);
               maxDate = new Date(maximum.year, maximum.month, maximum.day, 0, 0, 0, 0);
            }
            else if((minimum instanceof Date) && (maximum instanceof Date)) {
               minDate = minimum;
               maxDate = maximum;
            }
            else {
               return null;
            }

            return minDate >= maxDate ? {dateGreaterThan: true} : null;
         }
         else {
            return null;
         }
      };
   }

   // Support starts with number, character, chinese. Using original of angular's
   // Validator, it can only starts with number and character.
   public static nameStartWithCharDigit(control: FormControl): ValidationErrors {
      let name: string = control.value;
      let firstCharNumber: boolean = name ?
         /[0-9A-Za-z]+/.test(name.charAt(0)) : false;

      return firstCharNumber ? {startsWithNumber: true} : null;
   }

   public static assetNameStartWithCharDigit(control: FormControl): ValidationErrors {
      if(!!control.value && !/^([a-zA-Z0-9\u4e00-\u9fa5])/.test(control.value)) {
         return {assetNameStartWithCharDigit: true};
      }

      return null;
   }

   public static containsNumberAndLetterOrNonAlphanumeric(control: FormControl): ValidationErrors {
      const includesNumeric: boolean = /\d/.test(control.value);
      const includesLetterOrNonAlphanumeric: boolean =
         /[a-z!"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]/i.test(control.value);

      return includesNumeric && includesLetterOrNonAlphanumeric ? null : {missingCharType: true};
   }

   public static emailList(delimiter: string = ",", ignoreWhitespace: boolean = true,
                           splitByColon: boolean = false, users?: string[]
                           ): (FormControl) => ValidationErrors | null
   {
      return (control) => {
         if(control.value != null && control.value !== "") {
            const value = typeof control.value == "object" ? control.value.value : control.value;

            if(!value) {
               return null;
            }

            const emails = value.split(delimiter).map(e => ignoreWhitespace ? e.trim() : e);

            if(!!emails && emails.some((email) => {
               const addr = splitByColon && !!email && email.indexOf(":") > 0
                  ? email.substring(email.indexOf(":") + 1) : email;

               return !!addr
                  ? (!EMAIL_REGEXP.test(addr)
                     && (!users || users.indexOf(addr) < 0)
                     && (splitByColon && !!users && email.indexOf(":") > 0
                        ? users.indexOf(email.substring(0, email.indexOf(":"))) < 0
                        : true))
                  : true;
            }))
            {
               return {"email": true};
            }
         }

         return null;
      };
   }

   public static duplicateTokens(delimiter: string = ",",
      ignoreWhitespace: boolean = true): (FormControl) => ValidationErrors | null
   {
      return (control) => {
         if(control.value != null && control.value !== "") {
            const value = typeof control.value == "object" ? control.value.value : control.value;

            if(!value) {
               return null;
            }

            const tokens = value.split(delimiter).map(e => ignoreWhitespace ? e.trim() : e);

            if(new Set(tokens).size < tokens.length) {
               return {"duplicateTokens": true};
            }
         }

         return null;
      };
   }

   public static passwordsMatch(passwordFieldName: string,
      verifyFieldName: string): (FormGroup) => ValidationErrors | null
   {
      return (group) => {
         if(!group) {
            return null;
         }

         const passwordControl = group.get(passwordFieldName);
         const verifyControl = group.get(verifyFieldName);

         if(!!passwordControl && !!verifyControl && passwordControl.enabled &&
            verifyControl.enabled && passwordControl.value !== verifyControl.value) {
            return {passwordsMatch: true};
         }

         return null;
      };
   }

   public static duplicateName(names: () => string[]): (FormControl) => ValidationErrors | null {
      return (control) => {
         const controlName: string = control.value;
         const found = names().find(name => name === controlName);
         return !!found ? {duplicateName: true} : null;
      };
   }

   // Checks whether the control value ends in the specific suffix, otherwise error
   public static hasSuffix(suffix: string): ValidatorFn {
      return (control: FormControl): ValidationErrors => {
         if(!!control && !!control.value) {
            const val: string = control.value;
            return val.endsWith(suffix) ? null : {incorrectSuffix: true};
         }

         return null;
      };
   }

   // Checks whether the control value contains the specific prefix, otherwise error
   public static hasPrefix(prefix: string): ValidatorFn {
      return (control: FormControl): ValidationErrors => {
         if(!!control && !!control.value) {
            const val: string = control.value;
            return val.startsWith(prefix) ? null : {incorrectPrefix: true};
         }

         return null;
      };
   }

   // Checks whether the control value does not contain the specified strings, error if it is in value
   public static cannotContain(illegalStr: string[], options: ExistsOptions = {}): ValidatorFn {
      return (control: FormControl): ValidationErrors => {
         if(!!control && !!control.value) {
            const val: string = options.ignoreCase ? control.value.toLowerCase() : control.value;
            const error: boolean = illegalStr.some((str) => {
               if(options.ignoreCase) {
                  str = str.toLowerCase();
               }

               return val.indexOf(str) >= 0;
            });

            return error ? {illegalStr: true} : null;
         }

         return null;
      };
   }

   public static matchPwdValidator(group: FormGroup) {
      let pwd: string = !!group.controls["password"]
         ? group.controls["password"].value : "";
      let confirmPassword: string = !!group.controls["confirmPassword"]
         ? group.controls["confirmPassword"].value : "";

      if(pwd === confirmPassword) {
         return null;
      }

      return {
         passwordMismatch: true
      };
   }

   /**
    * Checks whether two strings are equal, ignoring case.
    *
    * @param s1 the first string to check
    * @param s2 the second string to check
    * @returns true if the strings are equal when ignoring case, false otherwise
    */
   private static equalsIgnoreCase(s1: string, s2: string): boolean {
      if(s1 == null || s2 == null) {
         return s1 === s2;
      }

      return s1.toUpperCase() === s2.toUpperCase();
   }
}

interface ExistsOptions {
   trimSurroundingWhitespace?: boolean; // if true, control value will be trimmed before comparison.
   ignoreCase?: boolean; // if true, control value comparison ignores case
   originalValue?: string; // if the control value matches the original value, short-circuit the validation
}
