package Controllers;

import Enums.SwimmingDisciplines;
import Models.SwimmingClub;
import Models.Team;

import java.time.LocalDate;
import java.util.ArrayList;

import Models.*;
import FileHandler.*;
import Repositories.MemberRepository;


public class TrainingController {
    private TrainingData data;
    private ArrayList<Trainer> trainers;
    private ArrayList<Person> swimmers;
    private MemberRepository memberRepo;

    public TrainingController() {
        data = new TrainingData();
        trainers = new ArrayList<>();
        swimmers = new ArrayList<>();
        this.memberRepo = new MemberRepository();
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

    public CompetitiveSwimmer getSwimmerByID(int id) {
        ArrayList <Member> members = memberRepo.getMemberArrayList();
        for (Member s : members) {
            if (s.getId() == id) {
                return (CompetitiveSwimmer) s;
            }
        }
        return null;
    }

    public CompetitiveSwimmer getSwimmerByName(String name) {
        for (Person s : swimmers) {
            if (s.getFullName().equals(name)) {
                return (CompetitiveSwimmer) s;
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

    public String getDisciplineTopFive(String team, String discipline) {
        return data.getDisciplineTrainings(team, discipline);
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
}

