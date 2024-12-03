package Repositories;

import Models.Contingent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

class ContingentRepositoryTest {

    @Test
    void calculatePriceOver18() {
        // Arrange
        ContingentRepository cr = new ContingentRepository();
        int memberId = 11;
        Contingent actual = null;
        double expexted = 1600.0;

        // Act
        try {
            actual = cr.createMemberContingent(memberId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(expexted,actual.getPrice());
    }

    @Test
    void calculatePriceUnder18() {
        // Arrange
        ContingentRepository cr = new ContingentRepository();
        int memberId = 12;
        Contingent actual = null;
        double expexted = 1000.0;

        // Act
        try {
            actual = cr.createMemberContingent(memberId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(expexted,actual.getPrice());
    }

    @Test
    void calculatePriceOver60() {
        // Arrange
        ContingentRepository cr = new ContingentRepository();
        int memberId = 13;
        Contingent actual = null;
        double expexted = 1200.0;

        // Act
        try {
            actual = cr.createMemberContingent(memberId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(expexted,actual.getPrice());
    }

    @Test
    void calculatePricePassiv() {
        // Arrange
        ContingentRepository cr = new ContingentRepository();
        int memberId = 14;
        Contingent actual = null;
        double expexted = 500.0;

        // Act
        try {
            actual = cr.createMemberContingent(memberId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(expexted,actual.getPrice());
    }

}