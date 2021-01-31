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

package club.javafamily.runner.rest.dto;

import club.javafamily.commons.enums.Gender;
import club.javafamily.commons.enums.UserType;

import java.io.Serializable;

public interface RestUser extends Serializable {
   String getName();

   String getAccount();

   String getEmail();

   UserType getUserType();

   default Gender getGender() {
      return Gender.Unknown;
   }
}
