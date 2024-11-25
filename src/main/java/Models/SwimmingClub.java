package Models;

import java.util.ArrayList;

public class SwimmingClub {

    private ArrayList<Team> teams;
    private ArrayList<Team> disciplines;

    public SwimmingClub(){
        this.teams = new ArrayList<Team>();
        this.disciplines = new ArrayList<Team>();
    }


    public ArrayList<Team> getTeams(){
        return teams;
    }

    public ArrayList<Team> getDisciplines(){
        return disciplines;
    }

    public void addTeam(Team team){
        this.teams.add(team);
    }




}
