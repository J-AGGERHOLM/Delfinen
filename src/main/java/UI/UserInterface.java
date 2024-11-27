package UI;

import Controllers.CompetitionController;
import Controllers.ContingentController;
import Controllers.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import Controllers.TeamsController;
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
    TeamsController teamsController = new TeamsController();
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
                 case "DISPLAY TEAM" -> displayTeams();
                case "CONTINGENT" -> displayContingent();
                case "COMPETITION" -> displayCompetion();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    private void displayCompetion() {
        //competitionController

        CompetitionController competitionController = new CompetitionController();

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
                System.out.println(competitionController.readCompetition());

            }
            default -> System.out.println("Not an option");
        }
    }

    //----------------------------------------------TEAMS-------------------------------------------

    private void displayTeams(){
        boolean exit = false;
        while(!exit){
            System.out.println("Vælg venligst holdet du gerne vil se:");

            String listOfTeams = teamsController.getListOfTeams();
            System.out.println(listOfTeams);

            System.out.println("Indtast tallet på holdet du vil gerne se/redigere, eller indtast AFSLUT for at gå tilbage");
            String userChoice = sc.nextLine();
            try{
                int parsedChoice = Integer.parseInt(userChoice);
                String teamDisplay = teamsController.getTeam(parsedChoice);
                System.out.println(teamDisplay);
                if(teamsController.setCurrentTeam(parsedChoice)){
                    editTeamMenu();
                }else{
                    System.out.println("Holdet kunne ikke findes.");
                }


            }catch (NumberFormatException e){
                switch (userChoice.toUpperCase()){
                    case "AFSLUT" -> exit = true;
                    default -> System.out.println("Indtast venligst en gyldig kommando");
                }
            }

        }

    }

    private void editTeamMenu(){
        boolean exit = false;
        while(!exit){

            System.out.println(teamsController.getCurrentTeam());
            System.out.println("Indtast venligst handlingen du vil udføre:");
            System.out.println("TILFØJ/FJERN: tilføje eller fjerne medlemmer fra hold");
            System.out.println("ÆNDRE NAVN: ændre holdets navn");
            System.out.println("ÆNDRE TRÆNER: ændre holdets træner");
            System.out.println("AFSLUT: gå tilbage");

            String userChoice = sc.nextLine();
            switch(userChoice.toUpperCase()){
                case "TILFØJ", "FJERN", "TILFØJ/FJERN" -> editTeamAddRemove();
                case "ÆNDRE NAVN", "NAVN" -> editTeamChangeName();
                case "ÆNDRE TRÆNER", "TRÆNER" -> editTeamChangeTrainer();
                case "AFSLUT" -> exit = true;
                default -> System.out.println("Indtast en gyldig kommando");
            }
        }
    }

    private void editTeamAddRemove(){
        System.out.println("Vælg IDet af medlemet du vil gerne tilføje/fjerne fra holdet");
        System.out.println("Indtast AFSLUT for at gå tilbage");
        System.out.println(teamsController.getListOfMembers());
        boolean exit = false;
        while(!exit){
            String userChoice = sc.nextLine();
            try{
                int userChoiceID = Integer.parseInt(userChoice);
                String result = teamsController.addRemoveFromCurrentTeam(userChoiceID);
                System.out.println(result);
            }catch (NumberFormatException e){
                if(userChoice.toUpperCase().equals("AFSLUT")){
                    exit = true;
                }else {
                    System.out.println("Indtast venligst en gyldig kommando");
                }
            }
        }

    }

    private void editTeamChangeName(){

        System.out.println("Indtast venligst den ny navn for holdet:");
        String userChoice = sc.nextLine();
        teamsController.setCurrentTeamName(userChoice);
        System.out.println("Navnet er blevet ændret");
    }

    private void editTeamChangeTrainer(){

        System.out.println("Vælg venlist en træner til holdet:");
        System.out.println(teamsController.getListOfTrainers());

        boolean exit = false;
        int trainerChoiceID;
        while (!exit){
            String userChoice = sc.nextLine();
            try{
                trainerChoiceID= Integer.parseInt(userChoice);
            }catch (NumberFormatException e){
                System.out.println("Indtast venligst en tal");
                trainerChoiceID = -1;
            }

            if( teamsController.assignTrainerToCurrentTeam(trainerChoiceID)){
                System.out.println("Denne træner er blevet tildelt til holdet");
                exit=true;
            }else{
                System.out.println("Indtast venligst en gyldig træner");
            }
        }
    }

    private void createTeam() {
        //must do this when creating a team:
        teamsController.resetTeamCreation();

        //Selecting the teams name:
        System.out.println("Skaber en ny hold.");
        System.out.println("Indtast venligst holdets navn:");
        String teamName = sc.nextLine();

        //Selecting the team's members from the clubs list of members:
        System.out.println("Vælg venligst holdets medlemmer ved at indstaste deres ID (AFSLUT for at slutte)");
        System.out.println(teamsController.getListOfMembers());
        boolean exit = false;
        while(!exit){
            String userChoice = sc.nextLine();

            try{
                int personChoice = Integer.parseInt(userChoice);
                String result = teamsController.addPersonToNewTeam(personChoice);
                System.out.println(result);
            }catch (NumberFormatException e){

                if(userChoice.toUpperCase().equals("AFSLUT")){
                    exit = true;
                }else{
                    System.out.println("Indtast venligst en gyldig kommando");
                }
            }
        }

        //Selecting the teams trainer:
        System.out.println("Indtast venligst trænerens ID");
        System.out.println(teamsController.getListOfTrainers());

        exit = false;
        int trainerChoiceID;
        while (!exit){
            String userChoice = sc.nextLine();
            try{
                trainerChoiceID= Integer.parseInt(userChoice);
            }catch (NumberFormatException e){
                System.out.println("Indtast venligst en tal");
                trainerChoiceID = -1;
            }

            if( teamsController.assignTrainer(trainerChoiceID)){
                System.out.println("Denne træner er blevet tildelt til holdet");
                exit=true;
            }else{
                System.out.println("Indtast venligst en gyldig træner");
            }
        }

        //Finally we tell the controller to make the new team with all the data it has gathered:
        teamsController.finalCreateNewTeam(teamName);
        System.out.println("Team created succesfully");



    }

//----------------------------------------------------END OF TEAMS-------------------------------------------


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
            System.out.println(cc.createMemberContingent());
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


    private void createMember() throws IOException {
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
                LocalDate.of(year,month,day),
                memberRepository.getNewId(),
                activity.equalsIgnoreCase("active"),
                competitive.equalsIgnoreCase("competitive"));
        System.out.println("You have created a new Member :D");
    }

    private void editMember(){
        Scanner sc = new Scanner(System.in);
        MemberRepository memberRepository = new MemberRepository();
        System.out.println("Which members information do you want to edit? ");
        System.out.println("Enter Full name: ");
        String name = sc.nextLine();
        memberRepository.chooseSpecificMemberByName(name);
        if (memberRepository.getCurrentMember() == null){
            System.out.println("couldnt find a member with that name.");
        } else {
            System.out.println("Member " + memberRepository.getCurrentMember().getFullName() + "found");
            System.out.println("what information do you want to edit? \nName \nBirthday \nActivity \nCompetitive");
            String input = sc.nextLine();
            switch (input){
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

