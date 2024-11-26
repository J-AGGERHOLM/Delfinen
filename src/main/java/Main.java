import Enums.SwimmingDisciplines;
import Models.CompetitiveSwimmer;
import Models.SwimmingClub;
import Models.Team;
import Models.Trainer;
import ui.UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main{
   public static void main(String[] args) {
       UserInterface ui = new UserInterface();
       ui.mainLoop();
   }

    private static SwimmingClub benjaminTest(){
        SwimmingClub swimmingClub = new SwimmingClub();
        //Creating dummy data for the team read feature:
        ArrayList<Team> teams = new ArrayList<Team>();
        // Team juniorTeam = new Team("Junior Team", new Person(1, "Hans Hansen"));
        Team juniorTeam = new Team(1, "Junior Team", new Trainer("Hans Hansen", LocalDate.of(1995, 2, 23), 1));
        CompetitiveSwimmer swimmer1 = new CompetitiveSwimmer("Mikkel Mikkelson", LocalDate.of(1976, 7, 7), 2, SwimmingDisciplines.BACKCRAWL);
        CompetitiveSwimmer swimmer2 = new CompetitiveSwimmer("Søren Sørensen", LocalDate.of(2003, 4, 12), 3, SwimmingDisciplines.BREASTSTROKE);
        CompetitiveSwimmer swimmer3 = new CompetitiveSwimmer("Ragnar Lodbrok", LocalDate.of(1976, 7, 7), 4, SwimmingDisciplines.BUTTERFLY);
        CompetitiveSwimmer swimmer4 = new CompetitiveSwimmer("William Williamson", LocalDate.of(1976, 7, 7), 5, SwimmingDisciplines.CRAWL);
        juniorTeam.addMember(swimmer1);
        juniorTeam.addMember(swimmer2);
        juniorTeam.addMember(swimmer3);
        juniorTeam.addMember(swimmer4);
        swimmingClub.addTeam(juniorTeam);

        Team seniorTeam = new Team(2, "Senior Team", new Trainer("Mads Mikkelson", LocalDate.of(1982, 11, 11), 6));
        CompetitiveSwimmer swimmer5 = new CompetitiveSwimmer("Harry Harrison", LocalDate.of(2007, 12, 12), 7, SwimmingDisciplines.CRAWL);
        CompetitiveSwimmer swimmer6 = new CompetitiveSwimmer("Anya Annison", LocalDate.of(2001, 10, 29), 8, SwimmingDisciplines.BUTTERFLY);
        CompetitiveSwimmer swimmer7 = new CompetitiveSwimmer("Benny Bennison", LocalDate.of(1999, 9, 12), 9, SwimmingDisciplines.BREASTSTROKE);
        CompetitiveSwimmer swimmer8 = new CompetitiveSwimmer("Carol Carolson", LocalDate.of(2000, 1,1), 10, SwimmingDisciplines.BACKCRAWL);
        seniorTeam.addMember(swimmer5);
        seniorTeam.addMember(swimmer6);
        seniorTeam.addMember(swimmer7);
        seniorTeam.addMember(swimmer8);
        swimmingClub.addTeam(seniorTeam);

        return swimmingClub;


    }
}