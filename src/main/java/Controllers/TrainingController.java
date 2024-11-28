package Controllers;

import Models.*;
import Repositories.TrainingRepository;

import java.util.ArrayList;


public class TrainingController {
    private TrainingRepository data;

    public TrainingController() {
        data = new TrainingRepository();
    }

    public String showData() {
        return data.showData(data.getData());
    }

    public void addTraining(Training t) {
        data.addTraining(t);

    }

    public String getDisciplineTopFive(String team, String discipline) {
        return data.getDisciplineTrainings(team, discipline);
    }

    public ArrayList<CompetitiveSwimmer> getCompetitiveSwimmersForDiscipline(String discipline) {
        return data.getCompetitiveSwimmersForDiscipline(discipline);
    }

    public CompetitiveSwimmer getSwimmerByID(int id) {
        return data.getSwimmerByID(id);
    }

    public void writeToFile() {
        data.writeToFile();
    }
}

