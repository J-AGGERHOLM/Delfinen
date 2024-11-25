package Controllers;

import Models.SwimmingClub;
import Models.Team;

import java.util.ArrayList;

import Models.*;

import java.util.ArrayList;

public class Controller {
    private SwimmingClub swimmingClub;
    private TrainingData data = new TrainingData();
    private ArrayList<Trainer> trainers = new ArrayList<>();
    private ArrayList<Person> swimmers = new ArrayList<>();

    public Controller(SwimmingClub swimmingClub){
        this.swimmingClub = swimmingClub;
    }

    public String getListOfTeams(){
        ArrayList<Team> teams = swimmingClub.getTeams();
        String result = "";
        int counter = 0;
        for(Team t: teams){
            counter++;
            result += counter + ". " + t + "\n";
        }
        return  result;
    }

    public String getTeam(int choice){
        String result = "";
        ArrayList<Team> teams = swimmingClub.getTeams();
        int counter = 0;
        for(Team t: teams){
            counter++;
            if( counter == choice){
                return t.getFullData();
            }
        }

        return "No team Matches that selection";
    }

    public String showData() {
        return data.showData(data.getData());
    }

    public void addTraining(Training t) {
        data.addTraining(t);
    }

    public void addSwimmer(Person s) {
        swimmers.add(s);
    }

    public void addTrainer(Trainer t) {
        trainers.add(t);
    }

    public Person getSwimmerByName(String name) {
        for(Person s : swimmers) {
            if(s.getFullName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public Person getSwimmerByID(int id) {
        for(Person s : swimmers) {
            if(s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public Trainer getTrainer(String name) {
        for(Trainer t : trainers) {
            if(t.getFullName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public String getSwimmerData(int id) {
        String result = "";
        Person temp = getSwimmerByID(id);
        if(data.getData().contains(temp)) {
            result += data.getData();
        }
        return result;
    }


    public String getDisciplineData(String discipline) {
        String result = "TRAINER: \t  DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for(Training t : data.getData()) {
            if(t.getDiscipline().equals(discipline)) {
                result += t.toString() + "\n";
            }
        }
        return result;
    }

    public String getDisciplineTopFive(String discipline) {
        return data.getDisciplineTrainings(discipline);
    }


}
