/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.validators;

/**
 *
 * @author uporabnik
 */
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckEMSO.class)
@Documented
public @interface EMSO {

    String message() default "*EMŠO ni pravilne oblike"; 

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    CaseMode value();

}
