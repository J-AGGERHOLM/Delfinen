package Models;

import java.util.ArrayList;
import java.util.Comparator;

public class TrainingData {
    private ArrayList<Training> trainings;

    public  TrainingData () {
        trainings = new ArrayList<Training>();
    }

    public ArrayList<Training> getData() {
        return trainings;
    }

    public void addTraining(Training t) {
        trainings.add(t);
    }

    public String showData(ArrayList<Training> training) {
        String data = "TRAINER: \t  DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for(Training t : training) {
            data += t.toString() + "\n";
        }
        return data;
    }

    public String getDisciplineTrainings(String team, String discipline) {
        ArrayList<Training> disciplineData = new ArrayList<>();
        String result = "DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for(Training t : trainings) {
            if(t.getDiscipline().toUpperCase().equals(discipline.toUpperCase())) {
                if(t.swimmerAge() < 18 && team.equals("junior")) {
                    disciplineData.add(t);
                }
                if(t.swimmerAge() >= 18 && team.equals("senior")) {
                    disciplineData.add(t);
                }
            }
        }

        disciplineData.sort(Comparator.comparing(Training::getTime));

        for(int i = 0; i < 5; i++) {
            try {
                result += disciplineData.get(i).toString() + "\n";
            } catch (IndexOutOfBoundsException e) {
                result += "No Data\n";
            }
        }
        return result;
    }
}
