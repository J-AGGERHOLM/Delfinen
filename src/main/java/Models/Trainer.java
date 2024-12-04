package Models;

import java.time.LocalDate;

public class Trainer extends Person {
    private Team team;


    public Trainer(String name, LocalDate birthday, int id, Team team) {
        super(name, birthday, id);
        this.team = team;
    }

    public Trainer(String name, LocalDate birthday, int id){
        super(name, birthday, id);
    }

    //______________________________________getter and setter methods____________________________________

    public Team getTeam(){
        return team;
    }

    public void setTeam(Team team){
        this.team = team;
    }

    //___________________________________________________________________________________________________________________
    @Override
    public String toString() {
        return "Full name: " + super.getFullName() +
                "Birthday: " + super.getBirthday() +
                "id: " + super.getId() +
                "Team: ";// + team.getName;
    }

    public String toStringFile(){
        if (team == null){
            return super.getFullName() + "," +
                    super.getBirthday() + "," + super.getId();
        }
        return super.getFullName() + "," +
                super.getBirthday() + "," + super.getId() + "," +
                team.getId();
    }
}
