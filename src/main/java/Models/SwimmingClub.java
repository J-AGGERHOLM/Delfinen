package Models;

import java.util.ArrayList;

public class SwimmingClub {

    private ArrayList<Team> teams;


    public SwimmingClub(){
        this.teams = new ArrayList<Team>();
    }


    public ArrayList<Team> getTeams(){
        return teams;
    }


    public void addTeam(Team team){
        this.teams.add(team);
    }

    public int getNewTeamID(){
        int highestID = 0;
        for(Team t: teams){
            if (t.getId() > highestID){
                highestID = t.getId();
            }
        }
        return highestID + 1;
    }




}
