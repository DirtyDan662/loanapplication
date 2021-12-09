/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import com.bern.casql.entity.Employment;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author unknown
 */
public class EmploymentUnitTest {
    
    private Employment employment;    
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void createValidEmployment(){
        employment = new Employment(LocalDate.MIN, LocalDate.MAX, "name", 89440);
        
        Set<ConstraintViolation<Employment>> violations = validator.validate(employment);
        assertEquals(0, violations.size());
    }
    
    @Test
    public void createInvalidEmployment(){
        employment = new Employment(null, null,null, 0);
        
        Set<ConstraintViolation<Employment>> violations = validator.validate(employment);
        assertEquals(3, violations.size());
    }
}
