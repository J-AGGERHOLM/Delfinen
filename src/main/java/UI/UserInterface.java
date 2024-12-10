package UI;

import Controllers.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

import java.util.Scanner;

import Enums.SwimmingDisciplines;
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
            System.out.println("Trænere: For at se mulighederne for trænere");
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
                case "TRÆNERE" -> trainersMenu();
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

        //whenever the teams menu is accessed, we update the list of trainers in the trainer controller:
        teamsController.updateTrainers();

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

        if(!teamsController.areThereTrainers()){
            System.out.println("Der er ingen trænere i databasen");
            teamsController.assignTrainer(0);
        }else{
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
        boolean exit = true;
        while(exit) {
            System.out.println("Velkommen til Træningmenu");
            System.out.println("Du har følgende muligheder:");
            System.out.println("TILFØJE : tilføje en træningssession");
            System.out.println("FEM : se top 5 for en disciplin");
            System.out.println("SVØMMER: se tider for en specifik svømmer");
            System.out.println("EXIT : tilbage til hovedmenuen");

            String input = sc.nextLine().toUpperCase();

            switch (input) {
                case "TILFØJE" -> addTrainingSession();
                case "FEM" -> viewTop5ForDiscipline();
                case "SVØMMER" -> searchForSpecificSwimmerData();
                case "EXIT" -> {
                    trainingController.writeToFile();
                    exit = false;
                }
                default -> System.out.println("Du har indtastet forkert");
            }
        }


    }

    public void addTrainingSession() {
        System.out.println("Disciplin");
        String discipline = sc.nextLine().toUpperCase();
        ArrayList<CompetitiveSwimmer> swimmers = trainingController.getCompetitiveSwimmersForDiscipline(discipline);
        for (CompetitiveSwimmer swimmer : swimmers) {
            System.out.println(swimmer.getFullName());
            System.out.println("Tid (XX:XX:XX):");
            System.out.println("Minutter:");
            int minutes = Validering.checkInt(sc);
            System.out.println("Sekunder:");
            int seconds = Validering.checkInt(sc);
            System.out.println("Millisekunder:");
            int miliseconds = Validering.checkInt(sc);
            String time = minutes + ":" + seconds + ":" + miliseconds;
            String date = String.valueOf(LocalDate.now());
            trainingController.addTraining(new Training(discipline, swimmer.getId(), time, date));
        }
    }

    public void viewTop5ForDiscipline() {
        System.out.println("For hvilken disciplin vil du gerne se de top 5 svømmere?");
        String choice = sc.nextLine();
        System.out.println("Junior eller Senior hold?");
        String team = sc.nextLine();
        System.out.println(trainingController.getDisciplineTopFive(team, choice));
    }

    public void searchForSpecificSwimmerData() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Svømmer ID:");
        int id = Validering.checkInt(scan);
        System.out.println(trainingController.specificSwimmerData(id));
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
            System.out.println("Skriv : 'Kontingenter' - For at se alle kontingenter.");
            System.out.println("Skriv : 'Specifik' - For at finde en medlems kontingenter.");
            System.out.println("Skriv : 'Forventet' - For at se forventede indtjening.");
            System.out.println("Skriv : 'Restance' - For at se manglende betalinger.");
            System.out.println("Skriv : 'Exit' - for at komme ud af kasserer menuen.");

            String input = Validering.mustBeString(scan).toUpperCase();

            switch (input) {
                case "OPRET" -> contingentAdd(scan);
                case "SLET" -> contingentDelete(scan);
                case "KONTINGENTER" -> readAll();
                case "SPECIFIK" -> getSpecificContingent(scan);
                case "FORVENTET" -> getExpectedEarnings();
                case "RESTANCE" -> getArrears();
                case "EXIT" -> exit = false;
                default -> System.out.println("Det indtastede passede ikke.\n");
            }
        }
    }

    private void contingentAdd(Scanner scan) {
        displayMembers();

        System.out.println("Skriv et id på en medlem, som du vil oprette kontingent på:");
        int memberId = Validering.checkInt(scan);

        // should be user input in parameter. input doesn't do anything yet
        ContingentController cc = new ContingentController();
        System.out.println(cc.createMemberContingent(memberId));
        System.out.println("-----------------------------------------------------------\n");
    }

    private void contingentDelete(Scanner scan) {
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
            System.out.println(cc.deleteContingent(Validering.checkInt(scan), memberId));
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
        System.out.println("Titel");
        System.out.println("Muligheder");
        System.out.println("'Opret'");
        System.out.println("'Rediger'");
        System.out.println("'Slet'");
        System.out.println("'Vis trænere'");
        System.out.println("'Specifik'");

        String input = sc.nextLine().toUpperCase();

        switch (input) {
            case "OPRET" -> createTrainer();
            case "REDIGER" -> editTrainer();
            case "SLET" -> deleteTrainer();
            case "VIS" -> showTrainers();
            case "SPECIFIK" -> specificTrainer();
            default -> System.out.println("Ikke en mulighed");
        }
    }

    public void createTrainer() {
        TrainerController trainerController = new TrainerController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Indtast fuldt navn: ");
        String name = sc.nextLine().trim();
        System.out.println("Indtast fødselsdag: ");
        System.out.println("Fødselsdagens dag: ");
        int day = sc.nextInt();
        System.out.println("Fødselsmåned: ");
        int month = sc.nextInt();
        System.out.println("Fødselsår: ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.println("Vil du tilføje ham til et eksisterende hold?");
        String input = sc.nextLine().toLowerCase();
        if (input.equalsIgnoreCase("ja")) {
            System.out.println("Kommer snart"); // opret denne metode
        } else {
            System.out.println(trainerController.createTrainerWithoutTeam(name, LocalDate.of(year, month, day)));
        }
    }

    public void editTrainer() {
        Scanner sc = new Scanner(System.in);
        TrainerController trainerController = new TrainerController();
        System.out.println("");
        System.out.println("Vil du bruge navn eller ID?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "navn", "fuldt navn" -> {
                System.out.println("Indtast navn: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerByName(input));
            }
            case "id" -> {
                System.out.println("Indtast ID: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerById(Integer.parseInt(input)));
            }
            default -> System.out.println("Ikke en gyldig mulighed");
        }
        if (trainerController.getCurrentTrainer() != null) {
            System.out.println("Vælg de oplysninger, du vil redigere");
            System.out.println("Navn");
            System.out.println("Fødselsdag");


            input = sc.nextLine().toLowerCase();

            switch (input) {
                case "navn" -> {
                    System.out.println("Indtast nyt navn: ");
                    input = sc.nextLine();
                    trainerController.getCurrentTrainer().setName(input);
                    System.out.println(trainerController.updateInformation());
                }
                case "fødselsdag" -> {
                    System.out.println("Indtast ny fødselsdag: ");
                    System.out.println("Dag: ");
                    int day = sc.nextInt();
                    System.out.println("Måned: ");
                    int month = sc.nextInt();
                    System.out.println("År: ");
                    int year = sc.nextInt();
                    trainerController.getCurrentTrainer().setBirthday(LocalDate.of(year, month, day));
                    System.out.println(trainerController.updateInformation());
                }
                default -> System.out.println("Ikke en mulighed");
            }
        }
    }

    public void deleteTrainer() {
        Scanner sc = new Scanner(System.in);
        TrainerController trainerController = new TrainerController();
        System.out.println("Vil du bruge navn eller ID?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "navn", "fuldt navn" -> {
                System.out.println("Indtast navn: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerByName(input));
            }
            case "id" -> {
                System.out.println("Indtast ID: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerById(Integer.parseInt(input)));
            }
            default -> System.out.println("Ikke en gyldig mulighed");
        }
        if (trainerController.getCurrentTrainer() != null) {
            System.out.println("Er du sikker på, at du vil slette denne træner?");
            input = sc.nextLine().toLowerCase();
            if (input.equalsIgnoreCase("ja")) {
                teamsController.deleteTrainerFromAllTeams(trainerController.getCurrentTrainer());
                System.out.println(trainerController.deleteTrainer());
            }
        }
    }

    public void showTrainers() {
        TrainerController trainerController = new TrainerController();
        System.out.println("Her er en liste over alle trænere fra klubben: \n");
        System.out.println(trainerController.displayTrainers());
    }

    public void specificTrainer() {
        TrainerController trainerController = new TrainerController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Vil du bruge navn eller ID?");
        String input = sc.nextLine();
        switch (input.toLowerCase()) {
            case "navn", "fuldt navn" -> {
                System.out.println("Indtast navn: ");
                input = sc.nextLine();
                System.out.println(trainerController.chooseSpecificTrainerByName(input));
            }
            case "id" -> {
                int idNum = 0;
                System.out.println("Indtast ID: ");
                while (true) {
                    String inputNum = sc.nextLine();
                    try {
                        idNum = Integer.parseInt(inputNum);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(input + " er ikke et gyldigt ID. Prøv igen.");
                    }
                }
                System.out.println(trainerController.chooseSpecificTrainerById(idNum));
            }
            default -> System.out.println("Ikke en mulighed");
        }
        if (trainerController.getCurrentTrainer() != null) {
            System.out.println(trainerController.displayTrainersInformation());
        }
    }


//Member methods----------------------------------------------------------------------------------------------------

    public void memberMenu() {
        String userInput = "";
        while (!userInput.equalsIgnoreCase("exit")) {
            System.out.println("Du er i medlemsmenuen");
            System.out.println("Indtast venligst en kommando");
            System.out.println("Opret: For at registrere et nyt medlem.");
            System.out.println("Rediger: For at redigere oplysninger om et eksisterende medlem.");
            System.out.println("Slet: For at slette et medlem fra systemet.");
            System.out.println("Medlemmer: For at se alle nuværende medlemmer.");
            System.out.println("Specifik: For at søge efter et bestemt medlem.");
            System.out.println("Exit: For at gå tilbage til hovedmenuen");

            Scanner sc = new Scanner(System.in);

            userInput = sc.nextLine().toUpperCase();
            System.out.println("------------------------------------------------\n");

            switch (userInput) {
                case "OPRET" -> createMember();
                case "REDIGER" -> editMember();
                case "SLET" -> deleteMember();
                case "MEDLEMMER" -> displayMembers();
                case "SPECIFIK" -> displayInformationFromSpecificMember();
                case "EXIT" -> System.out.println("Gå tilbage til hovedmenuen");
                default -> System.out.println("Indtast en gyldig kommando");
            }
        }
    }

    private void createMember() {
        System.out.println("------------------------------------------------\n");
        MemberController memberController = new MemberController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Indtast fuldt navn: ");
        String name = sc.nextLine().trim();
        System.out.println("Indtast fødselsdag: ");
        System.out.println("Fødselsdagen: ");
        int day = sc.nextInt();
        System.out.println("Fødselsmåned:");
        int month = sc.nextInt();
        System.out.println("Fødselsår: ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.println("Skal den være aktiv eller passiv? (skriv 1 for aktiv / 2 for passiv)");
        String activity = sc.nextLine();
        System.out.println("Vil det være konkurrencepræget eller almindeligt? (skriv 1 for konkurrence / 2 for almindelig)");
        String competitive = sc.nextLine();
        if (!competitive.equalsIgnoreCase("1")) {
            System.out.println(memberController.createMember(name, LocalDate.of(year, month, day), activity.equalsIgnoreCase("1"), competitive.equalsIgnoreCase("1")));
        } else {
            ArrayList<SwimmingDisciplines> chosenDisciplines = typeMemberDiscipline();
            System.out.println(memberController.createCompetitiveMember(name, LocalDate.of(year, month, day), activity.equalsIgnoreCase("1"), competitive.equalsIgnoreCase("1")));
            memberController.getCurrentMember().setChosenDisciplines(chosenDisciplines);
            memberController.updateInformation();
        }
    }

    // Helper method for creating competitive member
    private ArrayList<SwimmingDisciplines> typeMemberDiscipline() {
        ArrayList<SwimmingDisciplines> swimmingDisciplinesArrayList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String input = "";
        while (!input.equalsIgnoreCase("nej")) {
            System.out.println("Vælg en svømmedisciplin, som du vil tildele medlemmet:");
            System.out.println("Skriv 1: For at tildele Butterfly");
            System.out.println("Skriv 2: For at tildele Crawl");
            System.out.println("Skriv 3: For at tildele Backcrawl");
            System.out.println("Skriv 4: For at tildele Brystsvømning");
            String chosenDiscipline = sc.nextLine();
            swimmingDisciplinesArrayList.add(SwimmingDisciplines.values()[Integer.parseInt(chosenDiscipline) - 1]);
            if (swimmingDisciplinesArrayList.size() < 4) {
                System.out.println("Vil du gerne tilføje en anden disciplin?");
                input = sc.nextLine();
            } else {
                break;
            }
        }
        return swimmingDisciplinesArrayList;
    }


    private void editMember() {
        System.out.println("------------------------------------------------\n");
        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController();
        System.out.println("Vil du bruge et navn eller et id?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "navn", "fuldt navn" -> {
                System.out.println("Indtast navn: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberByName(input));
            }
            case "id" -> {
                System.out.println("Indtast ID: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberById(Integer.parseInt(input)));
            }
            default -> System.out.println("Ugyldig kommando");
        }
        if (memberController.getCurrentMember() != null) {
            System.out.println("Vælg de oplysninger, du vil redigere:");
            System.out.println("Navn");
            System.out.println("Fødselsdag");
            System.out.println("Aktivitet");
            System.out.println("Konkurrence");

            input = sc.nextLine().toLowerCase();

            switch (input) {
                case "navn" -> {
                    System.out.println("Indtast nyt navn: ");
                    input = sc.nextLine();
                    memberController.getCurrentMember().setName(input);
                    System.out.println(memberController.updateInformation());
                }
                case "fødselsdag" -> {
                    System.out.println("Indtast ny fødselsdag:");
                    System.out.println("Fødselsdag: ");
                    int day = sc.nextInt();
                    System.out.println("Fødselsmåned: ");
                    int month = sc.nextInt();
                    System.out.println("Fødselsår: ");
                    int year = sc.nextInt();
                    memberController.getCurrentMember().setBirthday(LocalDate.of(year, month, day));
                    System.out.println(memberController.updateInformation());
                }
                case "aktivitet" -> {
                    System.out.println("Indtast ny aktivitetsstatus ('aktiv' eller 'passiv'): ");
                    input = sc.nextLine();
                    memberController.getCurrentMember().setActivity(input.equalsIgnoreCase("aktiv"));
                    System.out.println(memberController.updateInformation());
                }
                case "konkurrence" -> {
                    System.out.println("Indtast ny konkurrence-status ('konkurrence' eller 'almindelig'): ");
                    input = sc.nextLine();
                    if (input.equalsIgnoreCase("konkurrence")) {
                        ArrayList<SwimmingDisciplines> newChosenDisciplines = typeMemberDiscipline();
                        System.out.println(memberController.changeFromMemberToCompetitiveSwimmer());
                        memberController.getCurrentMember().setChosenDisciplines(newChosenDisciplines);
                    } else {
                        teamsController.deleteMemberFromAllTeams(memberController.getCurrentMember());
                        memberController.changeFromCompetitiveSwimmerToMember();
                    }
                    System.out.println(memberController.updateInformation());
                }
                default -> System.out.println("Ikke en mulighed");
            }
        }
    }

    public void deleteMember() {
        System.out.println("------------------------------------------------\n");
        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController();
        System.out.println("Vil du bruge et navn eller et id?");
        String input = sc.nextLine().toLowerCase();
        switch (input) {
            case "navn", "fuldt navn" -> {
                System.out.println("Indtast navn: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberByName(input));
            }
            case "id" -> {
                System.out.println("Indtast ID: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberById(Integer.parseInt(input)));
            }
            default -> System.out.println("Ugyldig kommando");
        }
        if (memberController.getCurrentMember() != null) {
            System.out.println("Er du sikker på, at du vil slette dette medlem?");
            input = sc.nextLine().toLowerCase();
            if (input.equalsIgnoreCase("ja")) {
                teamsController.deleteMemberFromAllTeams(memberController.getCurrentMember());
                System.out.println(memberController.deleteMember());
            }
        }
    }



    private void displayMembers() {
        System.out.println("------------------------------------------------\n");
        MemberController memberController = new MemberController();
        System.out.println("Her er en liste over alle medlemmer fra klubben:\n");
        System.out.println(memberController.displayMembers());
    }

    private void displayInformationFromSpecificMember() {
        System.out.println("------------------------------------------------\n");
        MemberController memberController = new MemberController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Vil du bruge et navn eller et id?");
        String input = sc.nextLine();
        switch (input.toLowerCase()) {
            case "navn", "fuldt navn" -> {
                System.out.println("Indtast navn: ");
                input = sc.nextLine();
                System.out.println(memberController.chooseSpecificMemberByName(input));
            }
            case "id" -> {
                int idNum = 0;
                System.out.println("Indtast id: ");
                while (true) {
                    String inputNum = sc.nextLine();
                    try {
                        idNum = Integer.parseInt(inputNum);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(input + " er ikke et gyldigt id. Prøv igen.");
                    }
                }
                System.out.println(memberController.chooseSpecificMemberById(idNum));
            }
            default -> System.out.println("Ugyldig kommando");
        }
        if (memberController.getCurrentMember() != null) {
            System.out.println(memberController.displayMemberInformation());
        }
    }

}


