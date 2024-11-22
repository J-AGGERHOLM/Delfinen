package domain_model;

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



}
