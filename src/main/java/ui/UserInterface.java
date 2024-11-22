package ui;

import java.util.Scanner;

public class UserInterface {

    public void mainLoopBenjamin(){

        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, and welcome to the Doplhin swimclub admin program! please enter a command");

        while(!exit){
            String userChoice = sc.nextLine();
            switch (userChoice.toUpperCase()){
                case "EXIT" -> exit = true;
                case "DISPLAY TEAM" -> displayTeams();
                default -> System.out.println("Please enter a valid Command");
            }
        }
    }

    private void displayTeams(){
        System.out.println("Please select the team you wish to view:");

        //TODO: here we display a list of teams
    }

    private void displayMembers(){
        // show a list of all members
    }
}
