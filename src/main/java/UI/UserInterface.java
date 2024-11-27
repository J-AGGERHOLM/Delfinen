package UI;

import Controllers.CompetitionController;
import Controllers.ContingentController;
import Controllers.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import Controllers.TeamsController;
import FileHandler.CompetitionFileHandler;
import Repositories.MemberRepository;

import Models.Competition;
import Models.Person;
import Models.Trainer;
import Models.Training;


public class UserInterface {
    Controller controller;
    //SuperHandler competitionFileHandler;
    TeamsController teamsController = new TeamsController();
    Scanner sc;
    //CompetitionFileHandler competitionFileHandler;


    public UserInterface() {
        this.controller = new Controller();
        //competitionFileHandler = new CompetitionFileHandler();
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
            System.out.println("Hold: administrere klubbens svømmehold");
            // System.out.println("Teams: see options about teams");

            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()) {
                case "EXIT" -> exit = true;
                case "TRAINER" -> trainerMenu();
                case "HOLD" -> teamMenu();
                case "MEMBERS" -> memberMenu();
                case "CONTINGENT" -> contingentMenu();
                //case "COMPETITION" -> competionMenu();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    /*
    private void competionMenu() {
        //competitionController

        CompetitionController competitionController = new CompetitionController();

        //scanners instanciated:
        Scanner sc = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        Scanner doubleScanner = new Scanner(System.in);


        //menu:

        System.out.println("You are in the Competition menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - To create a new competion entry.");
        System.out.println("Type : 'Display' - To Display all competion entries.");
        //System.out.println("Type : 'Delete' - To delete competition entry.");

        String competitionInput = sc.nextLine().toUpperCase();

        //depending on the users input, these cases happen:
        switch (competitionInput) {
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
    */


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



    private void teamMenu() {

        boolean exit = false;
        while(!exit){
            Scanner scan = new Scanner(System.in);
            System.out.println("Du er i hold menuen");
            System.out.println("Du har nu følgende muligheder:");
            System.out.println("OPRETTE: oprette en ny hold");
            System.out.println("SE: se hold, og opdatere deres medlemmer, trænere og navn");
            System.out.println("SLET: slet en hold ");
            System.out.println("AFSLUT: gå tilbage til hovedmenuen");

            String input = scan.nextLine().toUpperCase();

            switch (input) {
                case "OPRETTE" -> createTeam();
                case "SLET" -> deleteTeam();
                case "SE" -> displayTeams();
                case "AFSLUT" -> exit = true;
                default -> System.out.println("Wrong input");
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

    private void deleteTeam(){
        System.out.println("Vælg venligst holdet du gerne vil slette ved at indtaste dens ID: " +
                "\n (AFSLUT for at gå tilbage)");
        System.out.println(teamsController.getListOfTeams());
        boolean exit = false;
        while(!exit){
            String userChoice = sc.nextLine();
            try{
                int idChoice = Integer.parseInt(userChoice);
                System.out.println("Er du sikker på du vil slette holdet? y/n");
                userChoice = sc.nextLine();
                if(userChoice.equals("y")){
                    boolean deletedSuccesfully = teamsController.deleteTeam(idChoice);
                    if(deletedSuccesfully){
                        System.out.println("Holdet er blevet slettet");
                        exit = true;
                    }else{
                        System.out.println("Denne hold blev ikke fundet i databasen.");
                        exit = true;
                    }
                }else{
                    exit = true;
                }


            }catch (NumberFormatException e){
                if(userChoice.toUpperCase().equals("AFSLUT")){
                    exit = true;
                }else{
                    System.out.println("ugyldigt kommando");
                }
            }
        }

    }

//----------------------------------------------------END OF TEAMS-------------------------------------------


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
            case "CREATE" -> contingentAdd(scan);
            case "DELETE" -> contingentDelete(scan);
            case "MEMBERS" -> readAll();
            case "SPECIFIC" -> getSpecificContingent(scan);
            case "" -> System.out.println();
            default -> System.out.println("Wrong input");
        }

    }

    private void contingentAdd(Scanner scan) {
        displayMembers();

        System.out.println("Type an member id to create a contingent");
        int memberId = scan.nextInt();

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();
        System.out.println(cc.createMemberContingent(memberId));
    }

    private void contingentDelete(Scanner scan) {
        displayMembers();

        System.out.println("Type an member id to get the members contingent");
        int memberId = scan.nextInt();

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();
        System.out.println(cc.getMemberContingents(memberId));
        System.out.println("Type an contingent id you would like to delete");

        System.out.println(cc.deleteContingent(scan.nextInt()));
    }

    private void getSpecificContingent(Scanner scan) {
        displayMembers();

        System.out.println("Type an member id to get specific contingent");
        int memberId = scan.nextInt();

        ContingentController cc = new ContingentController();
        System.out.println(cc.getMemberContingents(memberId));
    }

    private void readAll() {
        ContingentController cc = new ContingentController();
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
            //case "DISPLAY SPECIFIC" -> displayInformationFromSpecificMember();
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

//    private void displayInformationFromSpecificMember() {
//        MemberRepository memberRepository = new MemberRepository();
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Do you want to use a name or an id?");
//        String input = sc.nextLine();
//        switch (input.toLowerCase(Locale.ROOT)) {
//            case "name", "full name" -> {
//                System.out.println("Enter name: ");
//                input = sc.nextLine();
//                memberRepository.chooseSpecificMemberByName(input);
//            }
//            case "id" -> {
//                int idNum = 0;
//                System.out.println("Enter id: ");
//                while (true) {
//                    String inputNum = sc.nextLine();
//                    try {
//                        idNum = Integer.parseInt(inputNum);
//                        break;
//                    } catch (NumberFormatException e) {
//                        System.out.println(input + " is not a valid id. try again");
//                    }
//                }
//                if (memberRepository.chooseSpecificMemberById(idNum)) {
//                    System.out.println("There is no member with ID: " + idNum);
//                } else {
//                    System.out.println("Member information with ID " + idNum);
//                    System.out.println(memberRepository.getCurrentMember().toString());
//                }
//            }
//        }
//
//    }
}

