package UI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Validering {

    //method to check if a string variable is empty.
    //currently only used to make sure that there are no entries without data.
    public static String stringHasValue(Scanner scan) {
        String input;
        // Trims the data.
        while (true) {
            input = scan.nextLine().trim();
            if(input.isEmpty()){
                System.out.println("Please enter some text");
                scan.next();
            }else{
                break;
            }
        }

        return input;
    }

    //checks input to see if it is an integer.
    public static String mustBeString(Scanner scan) {
        String input;
        while (true) {
            if (scan.hasNextInt()) {
                System.out.println("You cant type a number");
                scan.next();
            }else{
                input = scan.nextLine().trim();
                if(input.isEmpty()){
                    System.out.println("Du skal taste noget");
                }else{
                    break;
                }
            }
        }

        return input;
    }

    // Check for int value.
    public static int checkInt(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.println("Indtast venligst et tal");
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

    // Check for date value
    public static LocalDate checkDate(Scanner scan) {
        LocalDate date = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (date == null) {
            System.out.println("Please enter a date in the format yyyy-MM-dd:");
            // trim data
            String input = scan.nextLine().trim();
            try {
                // Try to parse date
                date = LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        // Returner valid date
        return date;
    }

}
