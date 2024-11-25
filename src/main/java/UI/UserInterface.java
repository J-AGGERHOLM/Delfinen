package UI;

import Controllers.ContingentController;
import Controllers.Controller;
import Models.SwimmingClub;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Controllers.Controller;
import FileHandler.CompetitionFileHandler;
import FileHandler.SuperHandler;
import Models.Competition;
import Models.Person;
import Models.Trainer;
import Models.Training;


public class UserInterface {
    Controller controller;
    SuperHandler competitionFileHandler;
    Scanner sc;

    public UserInterface(){
        this.controller = new Controller();
        competitionFileHandler = new CompetitionFileHandler();
        sc = new Scanner(System.in);
    }

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
                case "COMPETITION" -> displayCompetion();
                case "DISPLAY MEMBERS" -> displayMembers();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }
    private void displayMembers() {
        // show a list of all members
    }

    private void displayTeams() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Please select the team you wish to view:");

            String listOfTeams = controller.getListOfTeams();
            System.out.println(listOfTeams);

            System.out.println("Please enter a number of a team to view, or choose to exit");
            String userChoice = sc.nextLine();
            try {
                int parsedChoice = Integer.parseInt(userChoice);
                String teamDisplay = controller.getTeam(parsedChoice);
                System.out.println(teamDisplay);
            } catch (NumberFormatException e) {
                switch (userChoice.toUpperCase()) {
                    case "EXIT" -> exit = true;
                    default -> System.out.println("Please enter a valid command");
                }

            }
        }
    }

    private void displayCompetion() {
        Scanner sc = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        Scanner doubleScanner = new Scanner(System.in);

        System.out.println("You are displaying the competitions");
        System.out.println("You now have the following options:");
        System.out.println("CREATE : To create a competition entry");

        String competitionInput = sc.nextLine();


        switch (competitionInput.toUpperCase()) {
            case "CREATE" -> {
                try {
                    System.out.println("Please enter the name of the event:");
                    String event = sc.nextLine();
                    System.out.println("Please enter te placement achieved:");
                    int placement = intScanner.nextInt();
                    System.out.println("Please enter the swimmers time:");
                    double time = doubleScanner.nextDouble();

                    Competition competition = new Competition(event, placement, time);

                    ((CompetitionFileHandler) competitionFileHandler).setCompetition(competition);
                } catch (InputMismatchException ime) {
                    System.out.println("Error : Something is wrong with these input values");
                }

                try {
                    competitionFileHandler.create();
                } catch (IOException e) {
                    System.out.println("Error: Something went wrong trying to create the file");
                }
            }
            default -> System.out.println("Not an option");
        }
    }

    public void trainerOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Trainer name:");
            String trainerName = scanner.nextLine();
            if (trainerName.isEmpty()) {
                break;
            }
            Trainer trainer = controller.getTrainer(trainerName);
            if (trainer == null) {
                System.out.println("Trainer not found");
                continue;
            }
            System.out.println("Discipline:");
            String discipline = scanner.nextLine();
            while (true) {
                System.out.println("Swimmer:");
                String swimmer = scanner.nextLine();
                if (swimmer.isEmpty()) {
                    break;
                }
                Person swimTemp = controller.getSwimmerByName(swimmer);
                if (swimTemp == null) {
                    System.out.println("Swimmer not found!");
                    continue;
                }
                System.out.println("Time (XX:XX:XX):");
                String time = scanner.nextLine();
                controller.addTraining(new Training(trainer, discipline, swimTemp, time));
            }
        }

        System.out.println(controller.showData());

        System.out.println("For which discipline would you like to see your top 5 swimmers?");
        String choice = scanner.nextLine();
        System.out.println(controller.getDisciplineTopFive(choice));
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

    private void contingentAdd(Scanner scan) {
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

    private void contingentDelete(Scanner scan) {
        // Display members.
        // Type ind which member you want to attach a contingent to

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController(1);
        System.out.println(cc.getMemberContingents());
        System.out.println("Type the contingent you would like to delete");

        System.out.println(cc.deleteContingent(scan.nextInt()));
    }

    private void getSpecificContingent() {
        ContingentController cc = new ContingentController(1);
        System.out.println(cc.getMemberContingents());
    }

    private void readAll() {
        ContingentController cc = new ContingentController(1);
        System.out.println(cc.readAll());
    }
}




