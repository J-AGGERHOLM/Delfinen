package Repositories;

import FileHandler.TrainingsFileHandler;
import Models.CompetitiveSwimmer;
import Models.Member;
import Models.Training;

import java.util.ArrayList;
import java.util.Comparator;

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
    }

    public String showData(ArrayList<Training> training) {
        String data = "DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for(Training t : training) {
            data += t.toString() + "\n";
        }
        return data;
    }

    public String getDisciplineTrainings(String team, String discipline) {
        ArrayList<Training> disciplineData = new ArrayList<>();
        ArrayList<Member> memberData = memberRepo.getMemberArrayList();
        String result = "DISCIPLINE: \t  ID(NAME): \t TIME: \n";
        for(Training t : trainings) {
            if(t.getDiscipline().toUpperCase().equals(discipline.toUpperCase())) {
                for(Member m : memberData) {
                    if(m.getId() == t.getSwimmerID()) {
                        if(m.getAge() < 18 && team.equals("junior")) {
                            disciplineData.add(t);
                        }
                        if(m.getAge() >= 18 && team.equals("senior")) {
                            disciplineData.add(t);
                        }
                    }
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
