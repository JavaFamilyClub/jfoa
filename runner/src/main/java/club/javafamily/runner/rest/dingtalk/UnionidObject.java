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

public class UnionidObject implements Serializable {
   private String unionid;

   public UnionidObject() {
   }

   public UnionidObject(String unionid) {
      this.unionid = unionid;
   }

   public String getUnionid() {
      return unionid;
   }

   public void setUnionid(String unionid) {
      this.unionid = unionid;
   }
}
