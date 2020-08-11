package club.javafamily.runner.annotation;

import club.javafamily.runner.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Audit {

   /**
    * resource
    */
   ResourceEnum value();

   /**
    * op object name
    */
   String objectName() default "";

   /**
    * op
    */
   ActionType actionType() default ActionType.ADD;
}
