package UI;

import Controllers.ContingentController;

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
        System.out.println("2: Delete a contingent");
        System.out.println("3: Find a person contingents");
        System.out.println("4: Lookup all contingent");
        System.out.println("5: Lookup arrears");

        int input = scan.nextInt();

        switch (input) {
            case 1 -> contingentAdd(scan);
            case 2 -> contingentDelete(scan);
            case 3 -> getSpecificContingent();
            case 4 -> readAll();
            case 5 -> System.out.println();
            default -> System.out.println("Wrong input");
        }
    }

    private void contingentAdd(Scanner scan){
        // Display members.
        // Type ind which member you want to attach a contingent to

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController(1);

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

    private void contingentDelete(Scanner scan){
        // Display members.
        // Type ind which member you want to attach a contingent to

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController(1);
        System.out.println(cc.getMemberContingents());
        System.out.println("Type the contingent you would like to delete");

        System.out.println(cc.deleteContingent(scan.nextInt()));
    }

    private void getSpecificContingent(){
        ContingentController cc = new ContingentController(1);
        System.out.println(cc.getMemberContingents());
    }

    private void readAll(){
        ContingentController cc = new ContingentController(1);
        System.out.println(cc.readAll());
    }
}
