package ui;

import Controllers.Controller;
import Models.SwimmingClub;

import java.util.Scanner;

public class UserInterface {
    Controller controller;

    public UserInterface(Controller controller){
        this.controller = controller;
    }


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
}
