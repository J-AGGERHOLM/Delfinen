package UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validering {
    Scanner scan;

    public Validering(){
        scan = new Scanner(System.in);
    }
    //method to check if a string variable is empty.
    //currently only used to make sure that there are no movie entries without titles.
    private String checkString(String value) {
        while (value.isEmpty()) {
            System.out.println("Please enter some text");
            value = scan.nextLine();
        }
        return value;
    }

    // controls that inputted value is a string.
    // if it is an integer it promts you to change it to a string.
    // used to make sure we don't get any unwanted inputs while creating a movie entry.
    private String isAString(String value) {
        if (isAnInteger(value)) {
            System.out.println("Please enter a valid Name");
            value = scan.nextLine();
            isAString(value);
        }
        return value;
    }


    //checks input to see if it is an integer.
    //helper method to use with isAString() method.
    private boolean isAnInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // check for isInColor
    private String stringIsYesNo(String value) {
        // if user input != YES or NO, then the method prompts you to write a valid input.
        while (!value.equals("YES") && !value.equals("NO")) {
            System.out.println("Try again. Type: yes or no.");
            value = scan.nextLine().toUpperCase().trim();
        }

        return value;
    }

    // Check for min value.
    private int checkInt() {
        try {
            //makes sure it's an integer:
            return Integer.parseInt(scan.nextLine());
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return checkInt();
        }
    }
}
