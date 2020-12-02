package club.javafamily.runner.dto;

import club.javafamily.commons.enums.Gender;
import club.javafamily.commons.enums.UserType;

import java.io.Serializable;

public interface RestUser extends Serializable {
   String getName();

   String getAccount();

   String getEmail();

   UserType getUserType();

   Gender getGender();
}
