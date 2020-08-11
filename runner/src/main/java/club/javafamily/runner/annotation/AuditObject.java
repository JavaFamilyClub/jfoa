package club.javafamily.runner.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AuditObject {

   /**
    * object name provider spel expression
    */
   String value();
}
