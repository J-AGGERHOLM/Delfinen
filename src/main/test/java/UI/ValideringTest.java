package UI;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Scanner;


public class ValideringTest {

    @Disabled
    void checkString() {
        // Arrange:
        Scanner scan = new Scanner("Test");
        // Act:
        String actual = Validering.checkString(scan);
        String expected = "Test";
        // Assert:
        assertEquals(expected, actual);
    }

    // You have to debug to se it works.
    // First it will take the empty space, go into while
    // Next it will take the value and return
    @Disabled
    void checkStringNot() {
        // Arrange:
        Scanner scan = new Scanner("\n\nHallo");
        // Act:
        String actual = Validering.checkString(scan);
        String expected = "Hallo";
        // Assert:
        assertEquals(expected, actual);
    }

    @Disabled
    void mustBeString() {
        // Arrange:
        Scanner scan = new Scanner("Hallo");
        // Act:
        String actual = Validering.mustBeString(scan);
        String expected = "Hallo";
        // Assert:
        assertEquals(expected, actual);
    }

    // You have to debug to se it works.
    // First it will take the number, go into while
    // Next it will take the value and return
    @Disabled
    void mustBeStringNot() {
        // Arrange:
        Scanner scan = new Scanner(1 + " Hallo");
        // Act:
        String actual = Validering.mustBeString(scan);
        String expected = "Hallo";
        // Assert:
        assertEquals(expected, actual.trim());
    }

    @Disabled
    void checkInt() {
        // Arrange
        Scanner scan = new Scanner(String.valueOf(1));
        // Act
        int actual = Validering.checkInt(scan);
        int expected = 1;
        // Assert
        assertEquals(expected, actual);
    }

    // You have to debug to se it works.
    // First it will take the value hej, go into while
    // Next it will take the value and return
    @Disabled
    void checkIntNot() {
        // Arrange
        Scanner scan = new Scanner("hej " + 1);
        // Act
        int actual = Validering.checkInt(scan);
        int expected = 1;
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void checkDouble() {
        // Arrange
        Scanner scan = new Scanner(String.valueOf(2.2));
        // Act
        double actual = Validering.checkDouble(scan);
        double expected = 2.2;
        // Assert
        assertEquals(expected, actual);
    }

    // You have to debug to se it works.
    // First it will take the value hej, go into while
    // Next it will take the value and return
    @Disabled
    void checkDoubleNot() {
        // Arrange
        Scanner scan = new Scanner("\n" + 1);
        // Act
        double actual = Validering.checkDouble(scan);
        double expected = 1;
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void checkDate() {
        // Arrange
        Scanner scan = new Scanner("\n\n 1234-12-12");
        // Act
        LocalDate expected = Validering.checkDate(scan);

    }

    @Disabled
    void checkDateNot() {


    }
}