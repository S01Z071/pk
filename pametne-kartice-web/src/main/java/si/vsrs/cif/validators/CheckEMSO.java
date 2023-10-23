/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.validators;

/**
 *
 * @author uporabnik
 */
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import si.vsrs.cif.helper.MetodeHelper;

public class CheckEMSO implements ConstraintValidator<EMSO, String> {

    private CaseMode caseMode;
   
    @Override
    public void initialize(EMSO constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (caseMode == CaseMode.EMPTY) {
            if (object == null || object.isEmpty()) {
                return true;
            }   
            
           return (object.toString().length() == 13 && object.toString().matches("[0-9]+"));
           
        } else {
            return (object.toString().length() == 13 && object.toString().matches("[0-9]+"));
        }


    }
}
