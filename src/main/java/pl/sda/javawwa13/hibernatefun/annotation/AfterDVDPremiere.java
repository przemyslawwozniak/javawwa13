package pl.sda.javawwa13.hibernatefun.annotation;

import pl.sda.javawwa13.hibernatefun.annotation.impl.DefaultAfterDVDPremiereValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DefaultAfterDVDPremiereValidator.class})
@Documented
public @interface AfterDVDPremiere {

    String message() default "This movie was 'released' before DVD premiere!";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};

}
