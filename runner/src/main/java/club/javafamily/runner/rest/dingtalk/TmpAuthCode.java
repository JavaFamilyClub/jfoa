/*
 * Copyright (c) 2021, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.rest.dingtalk;

import java.io.Serializable;

public class TmpAuthCode implements Serializable {
   private String tmp_auth_code;

   public TmpAuthCode() {
   }

   public TmpAuthCode(String tmp_auth_code) {
      this.tmp_auth_code = tmp_auth_code;
   }

   public String getTmp_auth_code() {
      return tmp_auth_code;
   }

   public void setTmp_auth_code(String tmp_auth_code) {
      this.tmp_auth_code = tmp_auth_code;
   }
}
