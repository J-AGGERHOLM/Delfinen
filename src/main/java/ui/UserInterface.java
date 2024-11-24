package ui;

import Controllers.Controller;

import java.util.Scanner;

public class UserInterface {
    Controller controller;

    public UserInterface(){
        controller = new Controller();
    }

    public void mainLoop() {

        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to the Doplhin swimclub admin program! please enter a command");


        while (!exit) {
            System.out.println("contingent: see options about contingent");

            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()) {
                case "EXIT" -> exit = true;
                case "DISPLAY TEAM" -> displayTeams();
                case "CONTINGENT" -> displayContingent();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    private void displayTeams() {
        System.out.println("Please select the team you wish to view:");

        //TODO: here we display a list of teams
    }

    private void displayContingent() {
        System.out.println("What would you like to do:");
        System.out.println("1: Add a contingent");
        System.out.println("2: edit a contingent");
        System.out.println("3: delete a contingent");
        System.out.println("4: find a person contingents");
        System.out.println("5: lookup all contingent");
        System.out.println("6: lookup arrears");

        int input = new Scanner(System.in).nextInt();

        String display = switch (input) {
            case 1 -> {
                // Display members.
                // Type ind which member you want to attach a contingent to
                System.out.println("I dont want an error. That's why i got this sout.");
                yield controller.addContingent();
            }
            case 2 -> "";
            case 3 -> "";
            case 4 -> "";
            case 5 -> "";
            case 6 -> "";
            default -> "Wrong input";
        };
        System.out.println(display);
    }
}
