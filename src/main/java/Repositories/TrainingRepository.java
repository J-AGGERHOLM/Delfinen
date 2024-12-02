package Repositories;

import FileHandler.TrainingsFileHandler;
import Models.CompetitiveSwimmer;
import Models.Member;
import Models.Training;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TrainingRepository {
    private ArrayList<Training> trainings;
    private final TrainingsFileHandler trainingsFileHandler;
    private MemberRepository memberRepo = new MemberRepository();

    public TrainingRepository() {
        trainingsFileHandler = new TrainingsFileHandler();
        trainings = trainingsFileHandler.read();
    }

    public ArrayList<Training> getData() {
        return trainings;
    }

    public void addTraining(Training t) {
        trainings.add(t);
        trainingsFileHandler.saveTrainingData(trainings);
    }

    public String showData(ArrayList<Training> training) {
        String data = "DISCIPLIN: \t  ID(NAVN): \t TID: \n";
        for(Training t : training) {
            data += t.toString() + "\n";
        }
        return data;
    }

    public String getDisciplineTrainings(String team, String discipline) {
        ArrayList<Training> disciplineData = new ArrayList<>();
        ArrayList<Member> memberData = memberRepo.getMemberArrayList();
        String result = "DISCIPLIN: \t  ID(NAVN): \t TID: \n";
        for(Training t : trainings) {
            if(t.getDiscipline().toUpperCase().equals(discipline.toUpperCase())) {
                for(Member m : memberData) {
                    if(m.getId() == t.getSwimmerID()) {
                        if(m.getAge() < 18 && team.equalsIgnoreCase("junior")) {
                            disciplineData.add(t);
                        }
                        if(m.getAge() >= 18 && team.equalsIgnoreCase("senior")) {
                            disciplineData.add(t);
                        }
                    }
                }
            }
        }
        disciplineData.sort(Comparator.comparing(Training::getTime));
        ArrayList<Integer> peopleCheck = new ArrayList<>();
        ArrayList<Training> top5 = new ArrayList();
        for(int i = 0; i < disciplineData.size(); i++) {
            if(!peopleCheck.contains(disciplineData.get(i).getSwimmerID())) {
                peopleCheck.add(disciplineData.get(i).getSwimmerID());
                top5.add(disciplineData.get(i));
            }
        }
        for(int i = 0; i < 5 ; i++) {
            try {
                result += top5.get(i).toString() + "\n";
            } catch (IndexOutOfBoundsException e) {
                result += "No Data\n";
            }
        }
        return result;
    }

    public CompetitiveSwimmer getSwimmerByID(int id) {
        ArrayList <Member> members = memberRepo.getMemberArrayList();
        for (Member s : members) {
            if (s.getId() == id) {
                return (CompetitiveSwimmer) s;
            }
        }
        return null;
    }

    public ArrayList<CompetitiveSwimmer> getCompetitiveSwimmersForDiscipline(String discipline) {
        ArrayList<CompetitiveSwimmer> result = new ArrayList<>();
        for(Member mem : memberRepo.getMemberArrayList()) {
            CompetitiveSwimmer swimmer = getSwimmerByID(mem.getId());
            if(String.valueOf(swimmer.getChosendiscipline()).equals(discipline)) {
                result.add(swimmer);
            }
        }
        return result;
    }

    public void writeToFile() {
        trainingsFileHandler.create();
    }
}
