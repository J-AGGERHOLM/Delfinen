package ui;

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
    Controller controller = new Controller();
    Scanner sc = new Scanner(System.in);


    public UserInterface(){

    }


    SuperHandler competitionFileHandler = new CompetitionFileHandler();



    public void mainLoop() {

        boolean exit = false;

        System.out.println("Hello, and welcome to the Doplhin swimclub admin program! please enter a command");

        while(!exit){
            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()){
                case "EXIT" -> exit = true;
                case "CREATE TEAM" -> createTeam();
                case "DISPLAY TEAM" -> displayTeams();
                case "COMPETITION" -> displayCompetion();
                case "DISPLAY MEMBERS" -> displayMembers();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    private void displayTeams(){
        boolean exit = false;
        while(!exit){
            System.out.println("Please select the team you wish to view:");

            String listOfTeams = controller.getListOfTeams();
            System.out.println(listOfTeams);

            System.out.println("Please enter a number of a team to view, or choose to exit");
            String userChoice = sc.nextLine();
            try{
                int parsedChoice = Integer.parseInt(userChoice);
                String teamDisplay = controller.getTeam(parsedChoice);
                System.out.println(teamDisplay);
            }catch (NumberFormatException e){
                switch (userChoice.toUpperCase()){
                    case "EXIT" -> exit = true;
                    default -> System.out.println("Please enter a valid command");
                }
            }

        }

    }

    private void createTeam(){
        System.out.println("Creating a new team.");
        System.out.println("Please enter the team's name:");
        String teamName = sc.nextLine();

        System.out.println("Please select the team's trainer:");

        //TODO: select the teams trainer here from a list of trainers

        System.out.println("Please select members to add to the team:");

        //TODO: select competative swimmers from a list of members and add them to the team.


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

    public void trainerOptions() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Trainer name:");
            String trainerName = scanner.nextLine();
            if(trainerName.isEmpty()) {
                break;
            }
            Trainer trainer = controller.getTrainer(trainerName);
            if(trainer == null) {
                System.out.println("Trainer not found");
                continue;
            }
            System.out.println("Discipline:");
            String discipline = scanner.nextLine();
            while(true) {
                System.out.println("Swimmer:");
                String swimmer = scanner.nextLine();
                if(swimmer.isEmpty()) {
                    break;
                }
                Person swimTemp = controller.getSwimmerByName(swimmer);
                if(swimTemp == null) {
                    System.out.println("Swimmer not found!");
                    continue;
                }
                System.out.println("Time (XX:XX:XX):");
                String time = scanner.nextLine();
                controller.addTraining(new Training(trainer,discipline,swimTemp,time));
            }
        }

        System.out.println(controller.showData());

        System.out.println("For which discipline would you like to see your top 5 swimmers?");
        String choice = scanner.nextLine();
        System.out.println(controller.getDisciplineTopFive(choice));
    }
}
