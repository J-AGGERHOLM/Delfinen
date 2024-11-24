package ui;

import Controllers.Contigent;

import java.util.Scanner;

public class UserInterface {

    public void mainLoop() {

        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to the Doplhin swimclub admin program!");


        while (!exit) {
            System.out.println("please enter a command");
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
        Scanner scan = new Scanner(System.in);
        System.out.println("What would you like to do:");
        System.out.println("1: Add a contingent");
        System.out.println("2: Edit a contingent");
        System.out.println("3: Delete a contingent");
        System.out.println("4: Find a person contingents");
        System.out.println("5: Lookup all contingent");
        System.out.println("6: Lookup arrears");

        int input = scan.nextInt();

        switch (input) {
            case 1 -> {
                // Maybe Display members.
                // Type ind which member you want to attach a contingent to

                // should be user input in parameter.
                Contigent cc = new Contigent(1);

                if (cc.getMember() == null) {
                    System.out.println("The typed id didn't exist");
                } else {
                    double price = Double.parseDouble(cc.checkMemberPrice());
                    System.out.println("Price: " + price);
                    System.out.println("Type in the amount of money");
                    if (price <= scan.nextInt()) {
                        System.out.println(cc.createMemberPaid());
                    } else {
                        System.out.println("Not enough money");
                    }
                }
            }
            case 2 -> System.out.println();
            case 3 -> System.out.println();
            case 4 -> System.out.println();
            case 5 -> System.out.println();
            case 6 -> System.out.println();
            default -> System.out.println("Wrong input");
        }
        ;

    }
}
