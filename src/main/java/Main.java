import Models.SwimmingClub;
import Models.Team;
import ui.UserInterface;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Jack");
        System.out.println("Welcome to the dolphin project");
        System.out.println("My push :D");

        benjaminTest();

        UserInterface userInterface = new UserInterface();
        userInterface.mainLoopBenjamin();

    }

    private static void benjaminTest() {
        SwimmingClub swimmingClub = new SwimmingClub();
        //Creating dummy data for the team read feature:
        ArrayList<Team> teams = new ArrayList<Team>();
        // Team juniorTeam = new Team("Junior Team", new Person(1, "Hans Hansen"));
    }
}