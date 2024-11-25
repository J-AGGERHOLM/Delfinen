package Controllers;

import Models.*;
import FileHandler.*;

import java.util.ArrayList;

public class Controller {

    private TrainingData data = new TrainingData();
    private ArrayList<Trainer> trainers = new ArrayList<>();
    private ArrayList<Person> swimmers = new ArrayList<>();

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
        for (Person s : swimmers) {
            if (s.getFullName().equals(name)) {
                return s;
            }
        }
        return null;
    }

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


    public String getDisciplineData(String discipline) {
        String result = "TRAINER: \t  DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for (Training t : data.getData()) {
            if (t.getDiscipline().equals(discipline)) {
                result += t.toString() + "\n";
            }
        }
        return result;
    }

    public String getDisciplineTopFive(String discipline) {
        return data.getDisciplineTrainings(discipline);
    }



    //-----------------------------competitions methods start-------------------------------------

    public String readCompetition() {

        //Instance of the filehandler
        CompetitionFileHandler competitionFileHandler = new CompetitionFileHandler();

        //document is read, and loaded into a variable to work with.
        competitionFileHandler.read();
        ArrayList<Competition> competitionArrayList = competitionFileHandler.getCompetitions();


        //If the arrayList variable is empty, this message is returned
        if (competitionArrayList.isEmpty()) {
            return "No competitions found.";
        }

        //string builder to format the attributes from the arrayList objects
        StringBuilder result = new StringBuilder("Competitions:\n");
        for (Competition competition : competitionArrayList) {
            result.append("Event: ").append(competition.getEvent())
                    .append(", Placement: ").append(competition.getPlacement())
                    .append(", Time: ").append(competition.getTime()).append("\n");
        }
        return result.toString();
    }


    //-----------------------------competitions methods end---------------------------------------



}
