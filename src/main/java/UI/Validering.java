package UI;

import java.time.LocalDate;
import java.util.Scanner;

public class Validering {

    //method to check if a string variable is empty.
    //currently only used to make sure that there are no entries without data.
    public static String checkString(Scanner scan) {
        // Trims the data.
        String input = scan.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("Please enter some text");
            input = scan.next();
        }

        return input;
    }

    //checks input to see if it is an integer.
    public static String mustBeString(Scanner scan) {
        while(scan.hasNextInt()){
            System.out.println("You cant type a number");
            scan.next();
        }
        return scan.nextLine();
    }

    // Check for int value.
    public static int checkInt(Scanner scan) {
        while(!scan.hasNextInt()){
            System.out.println("Please enter a number");
            scan.next();
        }
        return scan.nextInt();
    }

    // Check for double value.
    public static double checkDouble(Scanner scan) {
        while(!scan.hasNextDouble()){
            System.out.println("Please enter a number");
            scan.next();
        }
        return scan.nextDouble();
    }

    public static LocalDate checkDate(Scanner scan) {
        return null;
    }

}
