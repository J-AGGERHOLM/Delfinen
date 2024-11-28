package Controllers;

import Enums.SwimmingDisciplines;
import Models.SwimmingClub;
import Models.Team;

import java.time.LocalDate;
import java.util.ArrayList;

import Models.*;
import FileHandler.*;


public class Controller {
    private SwimmingClub swimmingClub;
    private TrainingData data;
    private ArrayList<Trainer> trainers;
    private ArrayList<Person> swimmers;

    public Controller() {
//        swimmingClub = benjaminTest();
        data = new TrainingData();
        trainers = new ArrayList<>();
        swimmers = new ArrayList<>();
    }

    public String getListOfTeams() {
        ArrayList<Team> teams = swimmingClub.getTeams();
        String result = "";
        int counter = 0;
        for (Team t : teams) {
            counter++;
            result += counter + ". " + t + "\n";
        }
        return result;
    }

    public String getTeam(int choice) {
        String result = "";
        ArrayList<Team> teams = swimmingClub.getTeams();
        int counter = 0;
        for (Team t : teams) {
            counter++;
            if (counter == choice) {
                return t.getFullData();
            }
        }

        return "No team Matches that selection";
    }

    public String showData() {
        return data.showData(data.getData());
    }

//    public void addTraining(Training t) {
//        data.addTraining(t);
//    }

    public void addSwimmer(Person s) {
        swimmers.add(s);
    }

//    public CompetitiveSwimmer getSwimmerByName(String name) {
//        for (Person s : swimmers) {
//            if (s.getFullName().equals(name)) {
//                return (CompetitiveSwimmer) s;
//            }
//        }
//        return null;
//    }

    public Person getSwimmerByID(int id) {
        for (Person s : swimmers) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public Trainer getTrainer(String name) {
        for (Trainer t : trainers) {
            if (t.getFullName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public String getSwimmerData(int id) {
        String result = "";
        Person temp = getSwimmerByID(id);
        if (data.getData().contains(temp)) {
            result += data.getData();
        }
        return result;
    }

//    private static SwimmingClub benjaminTest() {
//        SwimmingClub swimmingClub = new SwimmingClub();
//        // Creating dummy data for the team read feature:ArrayList<Team> teams = new ArrayList<Team>();
//        //Team juniorTeam = new Team("Junior Team", new Person(1, "Hans Hansen"));
//        Team juniorTeam = new Team( 1,"Junior Team", new Trainer("Hans Hansen", LocalDate.of(1995, 2, 23), 1));
//        CompetitiveSwimmer swimmer1 = new CompetitiveSwimmer("Mikkel Mikkelson", LocalDate.of(1976, 7, 7), 2, SwimmingDisciplines.BACKCRAWL);
//        CompetitiveSwimmer swimmer2 = new CompetitiveSwimmer("Søren Sørensen", LocalDate.of(2003, 4, 12), 3, SwimmingDisciplines.BREASTSTROKE);
//        CompetitiveSwimmer swimmer3 = new CompetitiveSwimmer("Ragnar Lodbrok", LocalDate.of(1976, 7, 7), 4, SwimmingDisciplines.BUTTERFLY);
//        CompetitiveSwimmer swimmer4 = new CompetitiveSwimmer("William Williamson", LocalDate.of(1976, 7, 7), 5, SwimmingDisciplines.CRAWL);
//        juniorTeam.addMember(swimmer1);
//        juniorTeam.addMember(swimmer2);
//        juniorTeam.addMember(swimmer3);
//        juniorTeam.addMember(swimmer4);
//        swimmingClub.addTeam(juniorTeam);
//        Team seniorTeam = new Team(2, "Senior Team", new Trainer("Mads Mikkelson", LocalDate.of(1982, 11, 11), 6));
//        CompetitiveSwimmer swimmer5 = new CompetitiveSwimmer("Harry Harrison", LocalDate.of(2007, 12, 12), 7, SwimmingDisciplines.CRAWL);
//        CompetitiveSwimmer swimmer6 = new CompetitiveSwimmer("Anya Annison", LocalDate.of(2001, 10, 29), 8, SwimmingDisciplines.BUTTERFLY);
//        CompetitiveSwimmer swimmer7 = new CompetitiveSwimmer("Benny Bennison", LocalDate.of(1999, 9, 12), 9, SwimmingDisciplines.BREASTSTROKE);
//        CompetitiveSwimmer swimmer8 = new CompetitiveSwimmer("Carol Carolson", LocalDate.of(2000, 1, 1), 10, SwimmingDisciplines.BACKCRAWL);
//        seniorTeam.addMember(swimmer5);
//        seniorTeam.addMember(swimmer6);
//        seniorTeam.addMember(swimmer7);
//        seniorTeam.addMember(swimmer8);
//        swimmingClub.addTeam(seniorTeam);
//        return swimmingClub;
//    }


    public String getDisciplineData(String discipline) {
        String result = "TRAINER: \t  DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for (Training t : data.getData()) {
            if (t.getDiscipline().equals(discipline)) {
                result += t.toString() + "\n";
            }
        }
        return result;
    }

    public String getDisciplineTopFive(String team, String discipline) {
        return data.getDisciplineTrainings(team, discipline);
    }





}
