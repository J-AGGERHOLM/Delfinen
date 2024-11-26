package UI;

import Controllers.Controller;
import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import FileHandler.CompetitionFileHandler;
import FileHandler.SuperHandler;
import Models.Competition;
import Models.Person;
import Models.Trainer;
import Models.Training;
import Repositories.MemberRepository;


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


    //_____________________________________________________simons ui________________________________________

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
