
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Zahtevek;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uporabnik
 */
public class ValidatorsTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCheckEMSO() {
        Imetnik imetnik = new Imetnik();
        Set<ConstraintViolation<Imetnik>> constraintViolations;
        imetnik.setEmso("1234564576454");
        constraintViolations = validator.validateProperty(imetnik, "emso");
        assertEquals(0, constraintViolations.size());

        imetnik.setEmso("");
        constraintViolations = validator.validateProperty(imetnik, "emso");
        assertEquals(0, constraintViolations.size());

        imetnik.setEmso("12345");
        constraintViolations = validator.validateProperty(imetnik, "emso");
        assertEquals(1, constraintViolations.size());

        imetnik.setEmso("abcdefghijklm");
        constraintViolations = validator.validateProperty(imetnik, "emso");
        assertEquals(1, constraintViolations.size());
    }
    
    
    @Test
    public void testValidacijaMsg() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //Validator validator = factory.getValidator();
        Zahtevek zah = new Zahtevek();
        Set<ConstraintViolation<Zahtevek>> constraintViolations = validator.validateProperty(zah,"imeOrganizacije");
        System.out.println("N: " + constraintViolations.size());
        for (ConstraintViolation<Zahtevek> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}
