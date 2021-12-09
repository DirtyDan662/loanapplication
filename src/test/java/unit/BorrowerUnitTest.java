/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import com.bern.casql.entity.Borrower;
import com.bern.casql.entity.Employment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
public class BorrowerUnitTest {

    private static Employment employment;
    private static List<Employment> employments;
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        employment = new Employment(LocalDate.MIN, LocalDate.MAX, "Anavrin", 40494);
        employments = new ArrayList<>();
        employments.add(employment);
    }

    

    @Test
    public void createValidBorrower() {
        Borrower validBorrower = new Borrower("Joe", "Goldberg", "5397 Villa Drive", "Los Angeles", "CA", "primary", 27, 90247, 555555555, employments);
        Set<ConstraintViolation<Borrower>> violations = validator.validate(validBorrower);
        assertEquals(0, violations.size());
    }

    @Test
    public void createInvalidBorrowerWithNullFields() {
        Borrower inValidBorrower = new Borrower(null, null, "5397 Villa Drive", "Los Angeles", "CA", "primary", 27, 90247, 555555555, employments);
        Set<ConstraintViolation<Borrower>> violations = validator.validate(inValidBorrower);
        assertEquals(2, violations.size());
    }

    @Test
    public void createYoungBorrower() {
        Borrower inValidBorrower = new Borrower("Joe", "Goldberg", "5397 Villa Drive", "Los Angeles", "CA", "primary", 17, 90247, 555555555, employments);
        Set<ConstraintViolation<Borrower>> violations = validator.validate(inValidBorrower);
        assertEquals(1, violations.size());
    }

}
