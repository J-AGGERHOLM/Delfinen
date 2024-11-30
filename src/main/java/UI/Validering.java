package UI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validering {

    //method to check if a string variable is empty.
    //currently only used to make sure that there are no entries without data.
    public static String checkString(Scanner scan) {
        // Trims the data.
        while (scan.nextLine().trim().isEmpty()) {
            System.out.println("Please enter some text");
            scan.next();
        }

        return scan.nextLine();
    }

    //checks input to see if it is an integer.
    public static String mustBeString(Scanner scan) {
        while (scan.hasNextInt()) {
            System.out.println("You cant type a number");
            scan.next();
        }
        return scan.nextLine();
    }

    // Check for int value.
    public static int checkInt(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.println("Please enter a number");
            scan.next();
        }
        return scan.nextInt();
    }

    // Check for double value.
    public static double checkDouble(Scanner scan) {
        while (!scan.hasNextDouble()) {
            System.out.println("Please enter a number");
            scan.next();
        }
        return scan.nextDouble();
    }

    // check for date value
    public static LocalDate checkDate(Scanner scan) {
        LocalDate date = null;
        // String can be 1234-23-23
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (date == null) {
            try {
                // sets date if string is parsed
                date = LocalDate.parse(scan.nextLine(), dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Datoens format: yyyy-mm-dd");
                scan.nextLine();
            }
        }
        return date;
    }

}
