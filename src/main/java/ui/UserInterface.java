package ui;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import FileHandler.CompetitionFileHandler;
import FileHandler.SuperHandler;
import Models.Competition;


public class UserInterface {

    SuperHandler competitionFileHandler = new CompetitionFileHandler();


    public void mainLoopBenjamin() {

        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to the Doplhin swimclub admin program! please enter a command");

        while (!exit) {
            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()) {
                case "EXIT" -> exit = true;
                case "DISPLAY TEAM" -> displayTeams();
                case "COMPETITION" -> displayCompetion();
                case "DISPLAY MEMBERS" -> displayMembers();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    private void displayTeams() {
        System.out.println("Please select the team you wish to view:");

        //TODO: here we display a list of teams
    }

    private void displayMembers() {
        // show a list of all members
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
}
