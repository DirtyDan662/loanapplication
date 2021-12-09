/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

/**
 *
 * @author unknown
 */
import com.bern.casql.entity.Borrower;
import com.bern.casql.entity.MortgageApplication;
import com.bern.casql.entity.Employment;
import java.math.BigInteger;
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

public class MortgageApplicationUnitTest {

    private static Borrower validBorrower;
    private static Employment employment;
    private static List<Employment> employments;
    private static List<Borrower> borrowers;
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        employment = new Employment(LocalDate.MIN, LocalDate.MAX, "Anavrin", 40494);
        employments = new ArrayList<>();
        employments.add(employment);

        borrowers = new ArrayList<>();
        validBorrower = new Borrower("Joe", "Goldberg", "5397 Villa Drive", "Los Angeles", "CA", "primary", 27, 90247, 555555555, employments);
        borrowers.add(validBorrower);
    }

    @Test
    public void createValidApplication() {
        borrowers.add(validBorrower);

        MortgageApplication testApp = new MortgageApplication("FHA", 30, BigInteger.valueOf(10000), borrowers);

        Set<ConstraintViolation<MortgageApplication>> violations = validator.validate(testApp);
        assertEquals(0, violations.size());
    }

    @Test
    public void createInvalidApplicationWithValidBorrower() {
        borrowers.add(validBorrower);

        MortgageApplication testApp = new MortgageApplication(null, 30, BigInteger.valueOf(10000), borrowers);

        Set<ConstraintViolation<MortgageApplication>> violations = validator.validate(testApp);
        assertEquals(1, violations.size());
    }

    @Test
    public void createInvalidApplicationWithLowYears() {
        MortgageApplication testApp = new MortgageApplication("FHA", 0, BigInteger.valueOf(10000), borrowers);

        Set<ConstraintViolation<MortgageApplication>> violations = validator.validate(testApp);
        assertEquals(1, violations.size());
    }

}
