package UI;

import Controllers.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

import java.util.Scanner;

import Models.*;
import Repositories.CompetitionRepository;

import Models.Training;


public class UserInterface {
    TeamsController teamsController = new TeamsController();
    TrainingController trainingController;
    Scanner sc;

    public UserInterface() {
        sc = new Scanner(System.in);
        trainingController = new TrainingController();
    }

    public void mainLoop() {

        boolean exit = false;
        System.out.println("Hej, velkommen til Delfin svømmeklub admin program!");


        while (!exit) {

            System.out.println("Indtast venligst en kommando");
            System.out.println("Træner: For at se mulighederne for trænere");
            System.out.println("Medlemmer: For at se mulighederne for medlemmer");
            System.out.println("Kasserer: For at se mulighederne for kasserer");
            System.out.println("Konkurrrence: see options about competitions");
            System.out.println("Hold: administrere klubbens svømmehold");

            String userChoice = sc.nextLine();
            System.out.println("------------------------------------------------\n");

            switch (userChoice.toUpperCase()) {
                case "EXIT" -> exit = true;
                case "TRÆNER" -> trainerMenu();
                case "HOLD" -> teamMenu();
                case "MEDLEMMER" -> memberMenu();
                case "KASSERER" -> contingentMenu();
                case "KONKURRENCE" -> competionMenu();
                default -> System.out.println("Please enter a valid Command");
            }
            System.out.println("-----------------------------------------------------------\n");

        }
    }


//----------------------------------Competition methods START----------------------------------


    private void competionMenu() {
        //competitionController

        CompetitionController competitionController = new CompetitionController();

        //scanners instanciated:
        Scanner sc = new Scanner(System.in);

        String competitionInput = "";
        
        while(!competitionInput.equals("EXIT")) {
            //menu:
            System.out.println("Du er i konkurrence menuen");
            System.out.println("Du har nu følgende valgmuligheder:");
            System.out.println("'Tilføj' - For at oprette en ny konkurrence.");
            System.out.println("'Se' - For at vise konkurrencerne.");
            System.out.println("'Slet' - For at slette en konkurrence.");
            System.out.println("'Exit' - For at forlade konkurrence menuen.");

            if (!competitionInput.equals("TILFØJ") && !competitionInput.equals("SE") && !competitionInput.equals("SLET") && !competitionInput.equals("EXIT")) {
                System.out.println("Vælg en gyldig valgmulighed");
            }

            competitionInput = sc.nextLine().toUpperCase();

            //depending on the users input, these cases happen:
            switch (competitionInput) {
                case "TILFØJ" -> competitionEntryCreate();
                case "SE" -> {
                    System.out.println(competitionController.readCompetition());
                }
                case "SLET" -> deleteCompetitionEntry();
                case "EXIT" -> {
                    return;
                }
                default -> System.out.println("Ikke en mulighed");
            }

        }
        




    }


    public void deleteCompetitionEntry() {

        Scanner sc = new Scanner(System.in);
        CompetitionRepository repository = new CompetitionRepository();
        System.out.println("Indtast venligst ID på den konkurrence du har lyst til at slette.");
        int searchWord = Validering.checkInt(sc);
        repository.searchForEntry(searchWord);

    }


    public void competitionEntryCreate() {
        Scanner sc = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        Scanner doubleScanner = new Scanner(System.in);
        CompetitionRepository repository = new CompetitionRepository();

        try {
            System.out.println("Indtast venligst navn til stævnet:");

            //validates a string for the event variable
            String event = Validering.mustBeString(sc);


            System.out.println("Indtast venligst placering opnået til stævnet:");

            //validates an int for the placement variable
            int placement = Validering.checkInt(intScanner);

            System.out.println("Indtast venligst tid på svømmeren til stævnet:");


            //validates a double for the time variable
            double time = Validering.checkDouble(doubleScanner);


            repository.commitCompetitionEntry(event, placement, time);

        } catch (InputMismatchException ime) {
            System.out.println("Error : Der er noget galt med de indtastede værdier");
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
        System.out.println("FEM : se top 5 for en disciplin");
        System.out.println("EXIT : tilbage til hovedmenuen");

        String input = sc.nextLine().toUpperCase();

        switch (input) {
            case "TILFØJE" -> addTrainingSession();
            case "FEM" -> viewTop5ForDiscipline();
            case "EXIT" -> {
                trainingController.writeToFile();
                mainLoop();
            }
            default -> System.out.println("Forkert input");
        }

    }

    public void addTrainingSession() {
        System.out.println("Disciplin");
        String discipline = sc.nextLine().toUpperCase();
        ArrayList<CompetitiveSwimmer> swimmers = trainingController.getCompetitiveSwimmersForDiscipline(discipline);
        for (CompetitiveSwimmer swimmer : swimmers) {
            CompetitiveSwimmer swimTemp = trainingController.getSwimmerByID(swimmer.getId());
            System.out.println(swimTemp.getFullName());
            System.out.println("Tid (XX:XX:XX):");
            String time = sc.nextLine();
            String date = String.valueOf(LocalDate.now());
            trainingController.addTraining(new Training(discipline, swimmer.getId(), time, date));
        }
        trainerMenu();
    }

    public void viewTop5ForDiscipline() {
        System.out.println("For hvilken disciplin vil du gerne se de top 5 svømmere?");
        String choice = sc.nextLine();
        System.out.println("Junior eller Senior hold?");
        String team = sc.nextLine();
        System.out.println(trainingController.getDisciplineTopFive(team, choice));
        trainerMenu();
    }


    //----------------------------------Training methods----------------------------------


    //----------------------------------Contingents methods----------------------------------


    private void contingentMenu() {
        boolean exit = true;
        while (exit) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Du er i contingent menuen.");
            System.out.println("Du har følgende muligheder:");
            System.out.println("Skriv : 'Opret' - For at oprette en kontingent.");
            System.out.println("Skriv : 'Slet' - For at slette en kontingent.");
            System.out.println("Skriv : 'Medlemmer' - For at se alle kontingenter.");
            System.out.println("Skriv : 'Specifik' - For at finde en medlems kontingenter.");
            System.out.println("Skriv : 'Forventet' - For at se forventede indtjening.");
            System.out.println("Skriv : 'Restance' - For at se manglende betalinger.");
            System.out.println("Skriv : 'Exit' - for at komme ud af kasserer menuen.");

            String input = Validering.mustBeString(scan).toUpperCase();

            switch (input) {
                case "OPRET" -> contingentAdd(scan);
                case "SLET" -> contingentDelete(scan);
                case "MEDLEMMER" -> readAll();
                case "SPECIFIK" -> getSpecificContingent(scan);
                case "FORVENTET" -> getExpectedEarnings();
                case "RESTANCE" -> getArrears();
                case "EXIT" -> exit = false;
                default -> System.out.println("Det indtastede passede ikke.\n");
            }
        }
    }

    private void contingentAdd(Scanner scan) {
        System.out.println("\n-----------------------------------------------------------\n");
        displayMembers();

        System.out.println("Skriv et id på en medlem, som du vil oprette kontingent på:");
        int memberId = Validering.checkInt(scan);

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();
        System.out.println(cc.createMemberContingent(memberId));
        System.out.println("-----------------------------------------------------------\n");
    }

    private void contingentDelete(Scanner scan) {
        System.out.println("\n-----------------------------------------------------------\n");
        displayMembers();

        System.out.println("Skriv et id på en medlem, som du vil slette kontingent på:");
        int memberId = Validering.checkInt(scan);

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();

        String members = cc.getMemberContingents(memberId);
        if (members.equals("Ingen data")) {
            System.out.println("Der er ingen kontingenter på ønskede id: " + memberId + "\n");
        } else {
            System.out.println(members);
            System.out.println("Skriv et kontingent id for at slette en specifik:");
            System.out.println(cc.deleteContingent(scan.nextInt()));
        }

        System.out.println("--------------------------------------------------------\n");

    }

    private void readAll() {
        System.out.println("\n-----------------------------------------------------------\n");
        System.out.println("Alle kontingenter i systemet:\n");
        ContingentController cc = new ContingentController();
        System.out.println(cc.readAll());
        System.out.println("-----------------------------------------------------------\n");
    }

    private void getSpecificContingent(Scanner scan) {
        System.out.println("\n-----------------------------------------------------------\n");
        displayMembers();

        System.out.println("Skriv et medlems id for at få kontingenterne: ");
        int memberId = Validering.checkInt(scan);

        ContingentController cc = new ContingentController();
        String members = cc.getMemberContingents(memberId);
        if (members.equals("Ingen data")) {
            System.out.println("Der er ingen kontingenter på ønskede id: " + memberId + "\n");
        } else {
            System.out.println("Her er personens kontingenter:\n");
            System.out.println(members);
        }
        System.out.println("-----------------------------------------------------------\n");
    }

    private void getExpectedEarnings() {
        System.out.println("\n-----------------------------------------------------------\n");
        ContingentController cc = new ContingentController();

        System.out.println("Forventede indtjening:");
        System.out.println(cc.getExpectedEarnings());
        System.out.println("-----------------------------------------------------------\n");
    }

    private void getArrears() {
        System.out.println("\n-----------------------------------------------------------\n");
        ContingentController cc = new ContingentController();
        System.out.println("Manglende betalinger:\n");
        System.out.println(cc.checkArrears());
        System.out.println("-----------------------------------------------------------\n");
    }

    //----------------------------------Contingents methods----------------------------------
// Trainer methods______________________________________________________________________________________________________
    // translate to danish
    public void trainersMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("title");
        System.out.println("options");
        System.out.println("'create'");
        System.out.println("'edit'");
        System.out.println("'delete'");
        System.out.println("'Display trainers'");
        System.out.println("'specific");

        String input = sc.nextLine().toUpperCase();

        switch (input) {
            case "CREATE" -> createTrainer();
            case "EDIT" -> editTrainer();
            case "DELETE" -> deleteTrainer();
            case "DISPLAY" -> showTrainers();
            case "SPECIFIC" -> specificTrainer();
            default -> System.out.println("Not option");
        }


    }

    public void createTrainer() {
        TrainerController trainerController = new TrainerController();
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
        System.out.println("Do you want to add him to an existing team?");
        String input = sc.nextLine().toLowerCase();
        if (input.equalsIgnoreCase("yes")) {
            System.out.println("It will come soon"); // create this method
        } else {
            System.out.println(trainerController.createTrainerWithoutTeam(name, LocalDate.of(year, month, day)));
        }
    }

    public void editTrainer() {                            // should finish the methods with team
        Scanner sc = new Scanner(System.in);
        TrainerController trainerController = new TrainerController();
        System.out.println("");
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerByName(input));
            }
            case "id" -> {
                System.out.println("Enter ID: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerById(Integer.parseInt(input)));
            }
        }
        if (trainerController.getCurrentTrainer() != null) {
            System.out.println("Choose the information you want to edit");
            System.out.println("Name");
            System.out.println("Birthday");
            System.out.println("Team");

            input = sc.nextLine().toLowerCase();

            switch (input) {
                case "name" -> {
                    System.out.println("Enter new name: ");
                    input = sc.nextLine();
                    trainerController.getCurrentTrainer().setName(input); // maybe refactor with an edit method in membersController
                    System.out.println(trainerController.updateInformation());
                }
                case "birthday" -> {
                    System.out.println("Enter new birthday: ");
                    System.out.println("Day of birth: ");
                    int day = sc.nextInt();
                    System.out.println("Month of birth: ");
                    int month = sc.nextInt();
                    System.out.println("Year of birth: ");
                    int year = sc.nextInt();
                    trainerController.getCurrentTrainer().setBirthday(LocalDate.of(year, month, day)); // refactor with an edit method in membersController
                    System.out.println(trainerController.updateInformation());

                }
                case "team" -> {
                    System.out.println("Do you want to take the trainer out of the team or change the team the trainer is in charge of?");
                    System.out.println("1 for delete 2 for change");
                    input = sc.nextLine();
                    switch (input) {
                        case "1" -> {
                            System.out.println("should delete him from the team he was in ");
                        }
                        case "2" -> {
                            System.out.println("should change the team he is in");
                        }
                    }
                }

                default -> System.out.println("not option");
            }
        }
    }

    public void deleteTrainer() {
        Scanner sc = new Scanner(System.in);
        TrainerController trainerController = new TrainerController();
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerByName(input));
            }
            case "id" -> {
                System.out.println("Enter ID: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerById(Integer.parseInt(input)));
            }
        }
        if (trainerController.getCurrentTrainer() != null) {
            System.out.println("Are you sure you want to delete this member?");
            input = sc.nextLine().toLowerCase();
            if (input.equalsIgnoreCase("yes")) {
                System.out.println(trainerController.deleteTrainer());
                // delete the trainer from existing teams
            }
        }
    }

    public void showTrainers() {
        TrainerController trainerController = new TrainerController();
        System.out.println("Here you have a list of all the trainers from the club: \n");
        System.out.println(trainerController.displayTrainers());
    }

    public void specificTrainer() {
        TrainerController trainerController = new TrainerController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine();
        switch (input.toLowerCase()) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerByName(input));
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
                System.out.println(trainerController.chooseSpecificTrainerById(idNum));
            }
        }
        if (trainerController.getCurrentTrainer() != null) {
            System.out.println(trainerController.displayTrainersInformation());
        }
    }

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
            case "CREATE" -> createMember();
            case "EDIT" -> editMember();
            case "DELETE" -> deleteMember();
            case "MEMBERS" -> displayMembers();
            case "SPECIFIC" -> displayInformationFromSpecificMember();
            default -> System.out.println("Not an option!");
        }
    }

    private void createMember() {
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
        if (!competitive.equalsIgnoreCase("competitive")) {
            System.out.println(memberController.createMember(name, LocalDate.of(year, month, day), activity.equalsIgnoreCase("active"), competitive.equalsIgnoreCase("competitive")));
        } else {
            int disciplineIndex = -1;
            disciplineIndex = typeMemberDiscipline();
            System.out.println(memberController.createCompetitiveMember(name, LocalDate.of(year, month, day), activity.equalsIgnoreCase("active"), competitive.equalsIgnoreCase("competitive"), disciplineIndex));
        }
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
        MemberController memberController = new MemberController();
        System.out.println("");
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberByName(input));
            }
            case "id" -> {
                System.out.println("Enter ID: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberById(Integer.parseInt(input)));
            }
        }
        if (memberController.getCurrentMember() != null) {
            System.out.println("Choose the information you want to edit");
            System.out.println("name");
            System.out.println("birthday");
            System.out.println("Activity");
            System.out.println("Competitive");

            input = sc.nextLine().toLowerCase();

            switch (input) {
                case "name" -> {
                    System.out.println("Enter new name: ");
                    input = sc.nextLine();
                    memberController.getCurrentMember().setName(input); // refactor with an edit method in membersController
                    System.out.println(memberController.updateInformation());
                }
                case "birthday" -> {
                    System.out.println("Enter new birthday: ");
                    System.out.println("Day of birth: ");
                    int day = sc.nextInt();
                    System.out.println("Month of birth: ");
                    int month = sc.nextInt();
                    System.out.println("Year of birth: ");
                    int year = sc.nextInt();
                    memberController.getCurrentMember().setBirthday(LocalDate.of(year, month, day)); // refactor with an edit method in membersController
                    System.out.println(memberController.updateInformation());

                }
                case "activity" -> {
                    System.out.println("Enter new activity status ('active' ; 'passive': ");
                    input = sc.nextLine();
                    memberController.getCurrentMember().setActivity(input.equalsIgnoreCase("active")); // refactor with an edit method in membersController
                    System.out.println(memberController.updateInformation());

                }
                case "competitive" -> {
                    System.out.println("Enter new competitive status ('competitive' ; 'regular': ");
                    input = sc.nextLine();
                    memberController.getCurrentMember().setCompetitive(input.equalsIgnoreCase("competitive")); // refactor with an edit method in membersController
                    System.out.println(memberController.updateInformation());

                }
                default -> System.out.println("not option");
            }
        }

    }

    public void deleteMember() {
        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController();
        System.out.println("Do you want to use a name or an id?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "name", "full name" -> {
                System.out.println("Enter name: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberByName(input));
            }
            case "id" -> {
                System.out.println("Enter ID: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberById(Integer.parseInt(input)));
            }
        }
        if (memberController.getCurrentMember() != null) {
            System.out.println("Are you sure you want to delete this member?");
            input = sc.nextLine().toLowerCase();
            if (input.equalsIgnoreCase("yes")) {
                System.out.println(memberController.deleteMember());
            }
        }
    }


    private void displayMembers() {
        MemberController memberController = new MemberController();
        System.out.println("Here you have a list of all the members from the club: \n");
        System.out.println(memberController.displayMembers());
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
                System.out.println(memberController.chooseSpecificMemberByName(input));
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
            }
        }
        if (memberController.getCurrentMember() != null) {
            System.out.println(memberController.displayMemberInformation());
        }
    }

}


