package domain_model;

import java.time.LocalDate;

public class Trainer extends Person {
    private Team team;


    public Trainer(String name, LocalDate birthday, int id, Team team){
        super(name, birthday, id);
        this.team = team;
    }


    @Override
    public String toString() {
        return "Full name: " + super.getFullName() +
               "Birthday: " + super.getBirthday() +
               "id: " + super.getId() +
               "Team: " ;// + team.getName;
    }
}
