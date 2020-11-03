package club.javafamily.runner.dto;

import club.javafamily.runner.enums.Gender;
import club.javafamily.runner.enums.UserType;

import java.io.Serializable;

public interface RestUser extends Serializable {
   String getName();

   String getAccount();

   String getEmail();

   UserType getUserType();

   Gender getGender();
}
