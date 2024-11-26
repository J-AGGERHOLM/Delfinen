package UI;

import Controllers.CompetitionController;
import Controllers.ContingentController;
import Controllers.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import Repositories.CompetitionRepository;
import Repositories.MemberRepository;
import Models.Person;
import Models.Trainer;
import Models.Training;


public class UserInterface {
    Controller controller;
    Scanner sc;

    public UserInterface() {
        this.controller = new Controller();
        sc = new Scanner(System.in);
    }

    public void mainLoop() {

        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to the Doplhin swimclub admin program!");


        while (!exit) {

            System.out.println("please enter a command");
            //System.out.println("Trainer: see options about trainers");
            System.out.println("Members: see options about members");
            System.out.println("Contingent: see options about contingent");
            System.out.println("Competition: see options about competitions");
            // System.out.println("Teams: see options about teams");

            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()) {
                case "EXIT" -> exit = true;
                case "TRAINER" -> trainerMenu();
                case "CREATE TEAM" -> teamMenu();
                case "MEMBERS" -> memberMenu();
                case "CONTINGENT" -> contingentMenu();
                case "COMPETITION" -> competionMenu();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    //----------------------------------Competition methods START----------------------------------


    private void competionMenu() {
        //competitionController

        CompetitionController competitionController = new CompetitionController();

        //scanners instanciated:
        Scanner sc = new Scanner(System.in);
        //menu:

        System.out.println("You are in the Competition menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - To create a new competion entry.");
        System.out.println("Type : 'Display' - To Display all competion entries.");
        System.out.println("Type : 'Delete' - To delete competition entry.");

        String competitionInput = sc.nextLine().toUpperCase();

        //depending on the users input, these cases happen:
        switch (competitionInput) {
            case "CREATE" -> competitionEntryCreate();
            case "DISPLAY" -> {
                System.out.println(competitionController.readCompetition());
            }
            case "DELETE" -> deleteCompetitionEntry();
            default -> System.out.println("Not an option");
        }
    }


    public void deleteCompetitionEntry() {
        Scanner sc = new Scanner(System.in);
        CompetitionRepository repository = new CompetitionRepository();
        System.out.println("Please enter the name of the event you'd like to delete.");
        String searchWord = sc.nextLine();
        repository.searchForEntry(searchWord);


    }


    public void competitionEntryCreate() {
        Scanner intScanner = new Scanner(System.in);
        Scanner doubleScanner = new Scanner(System.in);
        CompetitionRepository repository = new CompetitionRepository();

        try {
            System.out.println("Please enter the name of the event:");
            String event = sc.nextLine();
            System.out.println("Please enter te placement achieved:");
            int placement = intScanner.nextInt();
            System.out.println("Please enter the swimmers time:");
            double time = doubleScanner.nextDouble();


            repository.commitCompetitionEntry(event, placement, time);

        } catch (InputMismatchException ime) {
            System.out.println("Error : Something is wrong with these input values");
        }

    }
    //----------------------------------Competition methods END----------------------------------


    //----------------------------------TEAM methods----------------------------------
    private void teamMenu() {

        Scanner scan = new Scanner(System.in);
        System.out.println("You are in the Team menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - .");
        System.out.println("Type : 'Delete' - .");
        System.out.println("Type : 'Members' - .");
        System.out.println("Type : 'Specific' - .");

        String input = scan.nextLine().toUpperCase();

        switch (input) {
            case "CREATE" -> createTeam();
            case "DELETE" -> System.out.println();
            case "MEMBERS" -> System.out.println();
            case "SPECIFIC" -> System.out.println();
            case "" -> System.out.println();
            default -> System.out.println("Wrong input");
        }

    }


    private void createTeam() {
        System.out.println("Creating a new team.");
        System.out.println("Please enter the team's name:");
        String teamName = sc.nextLine();

    }
    //----------------------------------TEAM methods----------------------------------


    //----------------------------------Training methods----------------------------------

    private void trainerMenu() {

        Scanner scan = new Scanner(System.in);
        System.out.println("You are in the Trainer menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - .");
        System.out.println("Type : 'Delete' - .");
        System.out.println("Type : 'Members' - .");
        System.out.println("Type : 'Specific' - .");

        String input = scan.nextLine().toUpperCase();

        switch (input) {
            case "CREATE" -> System.out.println();
            case "DELETE" -> System.out.println();
            case "MEMBERS" -> System.out.println();
            case "SPECIFIC" -> System.out.println();
            case "" -> System.out.println();
            default -> System.out.println("Wrong input");
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
    //----------------------------------Training methods----------------------------------


    //----------------------------------Contingents methods----------------------------------


    private void contingentMenu() {

        Scanner scan = new Scanner(System.in);
        System.out.println("You are in the Contingent menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - To register a new contingent entry.");
        System.out.println("Type : 'Delete' - To Delete an existing contingent entry.");
        System.out.println("Type : 'Members' - To view contingents of all members.");
        System.out.println("Type : 'Specific' - To search for a specific members contingents.");

        String input = scan.nextLine().toUpperCase();

        switch (input) {
            case "CREATE" -> contingentAdd();
            case "DELETE" -> contingentDelete();
            case "MEMBERS" -> readAll();
            case "SPECIFIC" -> getSpecificContingent();
            case "" -> System.out.println();
            default -> System.out.println("Wrong input");
        }

    }


    private void contingentAdd() {
        // Display members.
        // Type ind which member you want to attach a contingent to

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController(1);
        if (cc.getMember() == null) {
            System.out.println("The typed id didn't exist");
        } else {
            System.out.println(cc.createMemberContingent());
        }
    }

    private void contingentDelete() {
        Scanner scan = new Scanner(System.in);
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
    //----------------------------------Contingents methods----------------------------------


//Member methods----------------------------------------------------------------------------------------------------

    public void memberMenu() {

        System.out.println("You are in the Member menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - To register a new member.");
        System.out.println("Type : 'Edit' - To edit information about an existing member.");
        System.out.println("Type : 'Members' - To view all current members.");
        System.out.println("Type : 'Specific' - To search for a specific member.");


        Scanner sc = new Scanner(System.in);

        String userInput = sc.nextLine().toUpperCase();

        switch (userInput) {
            case "CREATE" -> createMember();
            case "EDIT" -> editMember();
            case "DISPLAY MEMBERS" -> displayMembers();
            case "DISPLAY SPECIFIC" -> displayInformationFromSpecificMember();
            default -> System.out.println("Not an option!");
        }
    }


    private void createMember() {
        MemberRepository memberRepository = new MemberRepository();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter full name: ");
        String name = sc.nextLine();
        System.out.println("Enter Birthday: ");
        System.out.println("Day of birth: ");
        int day = sc.nextInt();
        System.out.println("Month of birth:");
        int month = sc.nextInt();
        System.out.println("Year of birth: ");
        int year = sc.nextInt();
        System.out.println("Will it be active or passive?");
        sc.nextLine();
        String activity = sc.nextLine();
        System.out.println("Will it be competitive or regular?");
        String competitive = sc.nextLine();
        memberRepository.createMember(name,
                LocalDate.of(year, month, day),
                activity.equalsIgnoreCase("active"),
                competitive.equalsIgnoreCase("competitive"));
        System.out.println("You have created a new Member :D");
    }

    private void editMember() {
        Scanner sc = new Scanner(System.in);
        MemberRepository memberRepository = new MemberRepository();
        System.out.println("Which members information do you want to edit? ");
        System.out.println("Enter Full name: ");
        String name = sc.nextLine();
        memberRepository.chooseSpecificMemberByName(name);
        if (memberRepository.getCurrentMember() == null) {
            System.out.println("couldnt find a member with that name.");
        } else {
            System.out.println("Member " + memberRepository.getCurrentMember().getFullName() + "found");
            System.out.println("what information do you want to edit? \nName \nBirthday \nActivity \nCompetitive");
            String input = sc.nextLine();
            switch (input) {
                case "Name" -> {
                    System.out.println("Enter new name: ");
                    String newName = sc.nextLine();
                    memberRepository.getCurrentMember().setName(newName);
                    System.out.println("Members name changed :)");
                }
            }
            String newName = sc.nextLine();
            memberRepository.getCurrentMember().setName(newName);
        }

    }


    private void displayMembers() {
        MemberRepository memberRepository = new MemberRepository();
        System.out.println("Here you have a list of all the members from the club: \n");
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
                if (memberRepository.chooseSpecificMemberById(idNum)) {
                    System.out.println("There is no member with ID: " + idNum);
                } else {
                    System.out.println("Member information with ID " + idNum);
                    System.out.println(memberRepository.getCurrentMember().toString());
                }
            }
        }

    }
}

