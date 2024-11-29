package UI;

import Controllers.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import FileHandler.CompetitionFileHandler;

import Enums.SwimmingDisciplines;
import Models.*;
import Repositories.CompetitionRepository;
import Repositories.MemberRepository;

import Models.Person;
import Models.Trainer;
import Models.Training;


public class UserInterface {
    CompetitionController competitionController;
    TeamsController teamsController = new TeamsController();
    TrainingController trainingController;
    Scanner sc;


    public UserInterface() {
        this.competitionController = new CompetitionController();
        this.trainingController = new TrainingController();
        sc = new Scanner(System.in);
    }

    public void mainLoop() {

        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to the Doplhin swimclub admin program!");


        while (!exit) {

            System.out.println("please enter a command");
            System.out.println("Trainer: see options about trainers");
            System.out.println("Members: see options about members");
            System.out.println("kasserer: see options about contingent");
            System.out.println("Competition: see options about competitions");
            System.out.println("Hold: administrere klubbens svømmehold");
            // System.out.println("Teams: see options about teams");

            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()) {
                case "EXIT" -> exit = true;
                case "TRAINER" -> trainerMenu();
                case "HOLD" -> teamMenu();
                case "MEMBERS" -> memberMenu();
                case "KASSERER" -> contingentMenu();
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

        System.out.println("Du er i konkurrence menuen");
        System.out.println("Du har nu følgende valgmuligheder:");
        System.out.println("'Tilføj' - For at oprette en ny konkurrence.");
        System.out.println("'Se' - For at vise konkurrencerne.");
        System.out.println("'Slet' - For at slette en konkurrence.");

        String competitionInput = sc.nextLine().toUpperCase();

        //depending on the users input, these cases happen:
        switch (competitionInput) {
            case "TILFØJ" -> competitionEntryCreate();
            case "SE" -> {
                System.out.println(competitionController.readCompetition());
            }
            case "SLET" -> deleteCompetitionEntry();
            default -> System.out.println("Ikke en mulighed");
        }
    }


    public void deleteCompetitionEntry() {

        Scanner sc = new Scanner(System.in);
        CompetitionRepository repository = new CompetitionRepository();
        System.out.println("Indtast venligst ID på den konkurrence du har lyst til at slette.");
        int searchWord = sc.nextInt();
        repository.searchForEntry(searchWord);

    }


    public void competitionEntryCreate() {
        Scanner sc = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        Scanner doubleScanner = new Scanner(System.in);
        CompetitionRepository repository = new CompetitionRepository();

        try {
            System.out.println("Indtast venligst navn til stævnet:");
            String event = sc.nextLine();
            System.out.println("Indtast venligst placering opnået til stævnet:");
            int placement = intScanner.nextInt();
            System.out.println("Indtast venligst tid på svømmeren til stævnet:");
            double time = doubleScanner.nextDouble();


            repository.commitCompetitionEntry(event, placement, time);

        } catch (InputMismatchException ime) {
            System.out.println("Error : Something is wrong with these input values");
        }

    }
    //----------------------------------Competition methods END----------------------------------


    //----------------------------------------------TEAMS-------------------------------------------

    private void displayTeams() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Vælg venligst holdet du gerne vil se:");

            String listOfTeams = teamsController.getListOfTeams();
            System.out.println(listOfTeams);

            System.out.println("Indtast tallet på holdet du vil gerne se/redigere, eller indtast AFSLUT for at gå tilbage");
            String userChoice = sc.nextLine();
            try {
                int parsedChoice = Integer.parseInt(userChoice);
                String teamDisplay = teamsController.getTeam(parsedChoice);
                System.out.println(teamDisplay);
                if (teamsController.setCurrentTeam(parsedChoice)) {
                    editTeamMenu();
                } else {
                    System.out.println("Holdet kunne ikke findes.");
                }


            } catch (NumberFormatException e) {
                switch (userChoice.toUpperCase()) {
                    case "AFSLUT" -> exit = true;
                    default -> System.out.println("Indtast venligst en gyldig kommando");
                }
            }

        }

    }

    private void editTeamMenu() {
        boolean exit = false;
        while (!exit) {

            System.out.println(teamsController.getCurrentTeam());
            System.out.println("Indtast venligst handlingen du vil udføre:");
            System.out.println("TILFØJ/FJERN: tilføje eller fjerne medlemmer fra hold");
            System.out.println("ÆNDRE NAVN: ændre holdets navn");
            System.out.println("ÆNDRE TRÆNER: ændre holdets træner");
            System.out.println("AFSLUT: gå tilbage");

            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()) {
                case "TILFØJ", "FJERN", "TILFØJ/FJERN" -> editTeamAddRemove();
                case "ÆNDRE NAVN", "NAVN" -> editTeamChangeName();
                case "ÆNDRE TRÆNER", "TRÆNER" -> editTeamChangeTrainer();
                case "AFSLUT" -> exit = true;
                default -> System.out.println("Indtast en gyldig kommando");
            }
        }
    }

    private void editTeamAddRemove() {
        System.out.println("Vælg IDet af medlemet du vil gerne tilføje/fjerne fra holdet");
        System.out.println("Indtast AFSLUT for at gå tilbage");
        System.out.println(teamsController.getListOfMembers());
        boolean exit = false;
        while (!exit) {
            String userChoice = sc.nextLine();
            try {
                int userChoiceID = Integer.parseInt(userChoice);
                String result = teamsController.addRemoveFromCurrentTeam(userChoiceID);
                System.out.println(result);
            } catch (NumberFormatException e) {
                if (userChoice.toUpperCase().equals("AFSLUT")) {
                    exit = true;
                } else {
                    System.out.println("Indtast venligst en gyldig kommando");
                }
            }
        }

    }

    private void editTeamChangeName() {

        System.out.println("Indtast venligst den ny navn for holdet:");
        String userChoice = sc.nextLine();
        teamsController.setCurrentTeamName(userChoice);
        System.out.println("Navnet er blevet ændret");
    }

    private void editTeamChangeTrainer() {

        System.out.println("Vælg venlist en træner til holdet:");
        System.out.println(teamsController.getListOfTrainers());

        boolean exit = false;
        int trainerChoiceID;
        while (!exit) {
            String userChoice = sc.nextLine();
            try {
                trainerChoiceID = Integer.parseInt(userChoice);
            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst en tal");
                trainerChoiceID = -1;
            }

            if (teamsController.assignTrainerToCurrentTeam(trainerChoiceID)) {
                System.out.println("Denne træner er blevet tildelt til holdet");
                exit = true;
            } else {
                System.out.println("Indtast venligst en gyldig træner");
            }
        }
    }


    private void teamMenu() {

        boolean exit = false;
        while (!exit) {
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
        while (!exit) {
            String userChoice = sc.nextLine();

            try {
                int personChoice = Integer.parseInt(userChoice);
                String result = teamsController.addPersonToNewTeam(personChoice);
                System.out.println(result);
            } catch (NumberFormatException e) {

                if (userChoice.toUpperCase().equals("AFSLUT")) {
                    exit = true;
                } else {
                    System.out.println("Indtast venligst en gyldig kommando");
                }
            }
        }

        //Selecting the teams trainer:
        System.out.println("Indtast venligst trænerens ID");
        System.out.println(teamsController.getListOfTrainers());

        exit = false;
        int trainerChoiceID;
        while (!exit) {
            String userChoice = sc.nextLine();
            try {
                trainerChoiceID = Integer.parseInt(userChoice);
            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst en tal");
                trainerChoiceID = -1;
            }

            if (teamsController.assignTrainer(trainerChoiceID)) {
                System.out.println("Denne træner er blevet tildelt til holdet");
                exit = true;
            } else {
                System.out.println("Indtast venligst en gyldig træner");
            }
        }

        //Finally we tell the controller to make the new team with all the data it has gathered:
        teamsController.finalCreateNewTeam(teamName);
        System.out.println("Team created succesfully");


    }

    private void deleteTeam() {
        System.out.println("Vælg venligst holdet du gerne vil slette ved at indtaste dens ID: " +
                "\n (AFSLUT for at gå tilbage)");
        System.out.println(teamsController.getListOfTeams());
        boolean exit = false;
        while (!exit) {
            String userChoice = sc.nextLine();
            try {
                int idChoice = Integer.parseInt(userChoice);
                System.out.println("Er du sikker på du vil slette holdet? y/n");
                userChoice = sc.nextLine();
                if (userChoice.equals("y")) {
                    boolean deletedSuccesfully = teamsController.deleteTeam(idChoice);
                    if (deletedSuccesfully) {
                        System.out.println("Holdet er blevet slettet");
                        exit = true;
                    } else {
                        System.out.println("Denne hold blev ikke fundet i databasen.");
                        exit = true;
                    }
                } else {
                    exit = true;
                }


            } catch (NumberFormatException e) {
                if (userChoice.toUpperCase().equals("AFSLUT")) {
                    exit = true;
                } else {
                    System.out.println("ugyldigt kommando");
                }
            }
        }

    }

//----------------------------------------------------END OF TEAMS-------------------------------------------


    //----------------------------------Training methods----------------------------------

    private void trainerMenu() {
        System.out.println("Velkommen til Træningmenu");
        System.out.println("Du har følgende muligheder:");
        System.out.println("TILFØJE : tilføje en træningssession.");
        System.out.println("FEM : se de top 5 for en disciplin");
        System.out.println("RETURN: træningmenu");
        System.out.println("EXIT : hovedmenuen");

        String input = sc.nextLine().toUpperCase();

        switch (input) {
            case "TILFØJE" -> addTrainingSession();
            case "FEM" -> viewTop5ForDiscipline();
            case "RETURN" -> trainerMenu();
            case "EXIT" -> {
                trainingController.writeToFile();
                mainLoop();
            }
            default -> System.out.println("Forkert input");
        }

    }

    public void addTrainingSession() {
        System.out.println("Disciplin");
        String discipline = sc.nextLine();
        ArrayList<CompetitiveSwimmer> swimmers = trainingController.getCompetitiveSwimmersForDiscipline(discipline);
        for(CompetitiveSwimmer swimmer : swimmers) {
            System.out.println(swimmer.getFullName());
            CompetitiveSwimmer swimTemp = trainingController.getSwimmerByID(swimmer.getId());
            System.out.println(swimTemp.getFullName());
            System.out.println("Tid (XX:XX:XX):");
            String time = sc.nextLine();
            trainingController.addTraining(new Training(discipline, swimmer.getId(), time));
        }
        System.out.println(trainingController.showData());
    }

    public void viewTop5ForDiscipline() {
        System.out.println("For hvilken disciplin vil du gerne se de top 5 svømmere?");
        String choice = sc.nextLine();
        System.out.println("Junior eller Senior hold?");
        String team = sc.nextLine();
        System.out.println(trainingController.getDisciplineTopFive(team,choice));
    }



    //----------------------------------Training methods----------------------------------


    //----------------------------------Contingents methods----------------------------------


    private void contingentMenu() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Du er i contingent menuen.");
        System.out.println("Du har følgende muligheder:");
        System.out.println("Skriv : 'Opret' - For at oprette en kontingent.");
        System.out.println("Skriv : 'Slet' - For at slette en kontingent.");
        System.out.println("Skriv : 'Medlemmer' - For at se alle kontingenter.");
        System.out.println("Skriv : 'Specifik' - For at finde en medlems kontingenter.");
        System.out.println("Skriv : 'Forventet' - For at se forventede indtjening.");

        String input = scan.nextLine().toUpperCase();

        switch (input) {
            case "OPRET" -> contingentAdd(scan);
            case "SLET" -> contingentDelete(scan);
            case "MEDLEMMER" -> readAll();
            case "SPECIFIK" -> getSpecificContingent(scan);
            case "FORVENTET" -> getExpectedEarnings();
            case "" -> System.out.println();
            default -> System.out.println("Det indtastede passede ikke.");
        }

    }

    private void contingentAdd(Scanner scan) {
        displayMembers();

        System.out.println("Skriv et id på en medlem, som du vil oprette kontingent på.");
        int memberId = scan.nextInt();

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();
        System.out.println(cc.createMemberContingent(memberId));
    }

    private void contingentDelete(Scanner scan) {
        displayMembers();

        System.out.println("Skriv et id på en medlem, som du vil slette kontingent på.");
        int memberId = scan.nextInt();

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();

        String members = cc.getMemberContingents(memberId);
        if (members.isEmpty()) {
            System.out.println("Der er ingen kontingent på ønskede id: " + memberId);
        } else {
            System.out.println("Skriv et kontingent id for at slette en specifik.");
            System.out.println(cc.deleteContingent(scan.nextInt()));
        }

    }

    private void readAll() {
        ContingentController cc = new ContingentController();
        System.out.println(cc.readAll());
    }

    private void getSpecificContingent(Scanner scan) {
        displayMembers();

        System.out.println("Skriv et medlems id for at få kontingenterne");
        int memberId = scan.nextInt();

        ContingentController cc = new ContingentController();
        String members = cc.getMemberContingents(memberId);
        if (members == null) {
            System.out.println("Der er ingen kontingent på ønskede id: " + memberId);
        } else {
            System.out.println("Skriv et kontingent id du vil slå op.");
            System.out.println(cc.deleteContingent(scan.nextInt()));
        }
    }

    private void getExpectedEarnings() {
        ContingentController cc = new ContingentController();

        System.out.println("Forventede indtjening:");
        System.out.println(cc.getExpectedEarnings());

    }
    //----------------------------------Contingents methods----------------------------------


//Member methods----------------------------------------------------------------------------------------------------

    public void memberMenu() {
        // need to translate to danish
        System.out.println("You are in the Member menu");
        System.out.println("You have following options:");
        System.out.println("Type : 'Create' - To register a new member.");
        System.out.println("Type : 'Edit' - To edit information about an existing member.");
        System.out.println("Typer : 'Delete' - to delete a member from the system.");
        System.out.println("Type : 'Members' - To view all current members.");
        System.out.println("Type : 'Specific' - To search for a specific member.");


        Scanner sc = new Scanner(System.in);

        String userInput = sc.nextLine().toUpperCase();

        switch (userInput) {
            // case "CREATE" -> createMember();
            case "EDIT" -> editMember();
            case "DISPLAY MEMBERS" -> displayMembers();
            case "DISPLAY SPECIFIC" -> displayInformationFromSpecificMember();
            default -> System.out.println("Not an option!");
        }
    }

    private void createMember() throws IOException {
        MemberController memberController = new MemberController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter full name: ");
        String name = sc.nextLine().trim();
        System.out.println("Enter Birthday: ");
        System.out.println("Day of birth: ");
        int day = sc.nextInt();
        System.out.println("Month of birth:");
        int month = sc.nextInt();
        System.out.println("Year of birth: ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.println("Will it be active or passive?");
        String activity = sc.nextLine();
        System.out.println("Will it be competitive or regular?");
        String competitive = sc.nextLine();
        System.out.println(memberController.createMember(name, LocalDate.of(year, month, day), activity.equalsIgnoreCase("active"), competitive.equalsIgnoreCase("competitive")));

        //Hi simon, i made changes here:
        //it's a check to see if we should create a member or the subclass competitiveSwimmer
        int disciplineIndex = -1;
        if (competitive.equalsIgnoreCase("competitive")) {
            disciplineIndex = typeMemberDiscipline();
            memberController.createCompetitiveMember(name, LocalDate.of(year, month, day), activity.equalsIgnoreCase("active"), competitive.equalsIgnoreCase("competitive"), disciplineIndex);
        } else {

            memberController.createMember(name,
                    LocalDate.of(year, month, day),
                    activity.equalsIgnoreCase("active"),
                    competitive.equalsIgnoreCase("competitive"));


        }
        System.out.println("You have created a new Member :D");
    }

    //helper method for creating competitibe member:
    private int typeMemberDiscipline() {
        int userChoice = 0;
        Scanner cmScan = new Scanner(System.in);
        System.out.println("Chose a swimming discipline to assign to the member:");
        System.out.println("Type 1 : To assign Butterfly ");
        System.out.println("Type 2 : To assign Crawl ");
        System.out.println("Type 3 : To assign Backcrawl ");
        System.out.println("Type 4 : To assign Breaststroke ");
        userChoice = cmScan.nextInt();

        return userChoice - 1;
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
        MemberController memberController = new MemberController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine();
        switch (input.toLowerCase()) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                memberController.chooseSpecificMemberByName(input);
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
                System.out.println(memberController.chooseSpecificMemberById(idNum));
                System.out.println(memberController.displayMemberInformation());
            }
        }

    }
}

