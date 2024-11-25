package UI;

import Controllers.ContingentController;
import Controllers.Controller;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import FileHandler.CompetitionFileHandler;
import FileHandler.SuperHandler;
import Models.Competition;
import Repositories.MemberRepository;
import Models.Person;
import Models.Trainer;
import Models.Training;


public class UserInterface {
    Controller controller;
    SuperHandler competitionFileHandler;
    Scanner sc;

    public UserInterface() {
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
                case "CREATE TEAM" -> createTeam();
               // case "DISPLAY TEAM" -> displayTeams();
                case "CONTINGENT" -> displayContingent();
                case "COMPETITION" -> displayCompetion();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    private void displayCompetion() {
        //scanners instanciated:
        Scanner sc = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        Scanner doubleScanner = new Scanner(System.in);


        //menu:
        System.out.println("You are displaying the competitions");
        System.out.println("You now have the following options:");
        System.out.println("CREATE : To create a competition entry");
        System.out.println("DISPLAY: To view previous entries");

        String competitionInput = sc.nextLine();

        //depending on the users input, these cases happen:
        switch (competitionInput.toUpperCase()) {
            case "CREATE" -> {
                try {
                    System.out.println("Please enter the name of the event:");
                    String event = sc.nextLine();
                    System.out.println("Please enter te placement achieved:");
                    int placement = intScanner.nextInt();
                    System.out.println("Please enter the swimmers time:");
                    double time = doubleScanner.nextDouble();


                    //Competition object is created with the users input
                    Competition competition = new Competition(event, placement, time);

                    ((CompetitionFileHandler) competitionFileHandler).setCompetition(competition);
                } catch (InputMismatchException ime) {
                    System.out.println("Error : Something is wrong with these input values");
                }

                //Competition object is comitted to the document
                try {
                    competitionFileHandler.create();
                } catch (IOException e) {
                    System.out.println("Error: Something went wrong trying to create the file");
                }
            }
            case "DISPLAY" -> {
                System.out.println(controller.readCompetition());

            }
            default -> System.out.println("Not an option");
        }
    }

    private void createTeam() {
        System.out.println("Creating a new team.");
        System.out.println("Please enter the team's name:");
        String teamName = sc.nextLine();

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


//Simons methods----------------------------------------------------------------------------------------------------


    private void displayMembers() {
        System.out.println("Here you have a list of all the members from the club: \n");
        MemberRepository memberRepository = new MemberRepository();
        System.out.println(memberRepository.displayMembers()); // should be with controller, will do it later
    }

    private void displayInformationFromSpecificMember() {
        MemberRepository memberRepository = new MemberRepository();
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine();
        switch (input.toLowerCase(Locale.ROOT)) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                memberRepository.chooseSpecificMemberByName(input);
            }
            case "id" -> {
                int idNum = 0;
                System.out.println("Enter id: ");
                while (true) {
                    String inputNum = sc.nextLine();
                    try {
                        idNum = Integer.parseInt(inputNum);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(input + " is not a valid id. try again");
                    }
                }
                if (memberRepository.chooseSpecificMemberById(idNum) == null) {
                    System.out.println("There is no member with ID: " + idNum);
                } else {
                    System.out.println("Member information with ID " + idNum);
                    System.out.println(memberRepository.getCurrentMember().toString());
                }
            }
        }
    }
}

