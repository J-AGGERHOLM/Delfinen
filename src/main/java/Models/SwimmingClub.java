package Models;

import FileHandler.TeamFileHandler;

import java.util.ArrayList;

public class SwimmingClub {

    private ArrayList<Team> teams;
    private TeamFileHandler fileHandler;


    public SwimmingClub(){
        this.teams = new ArrayList<Team>();
        this.fileHandler = new TeamFileHandler();
    }


    public ArrayList<Team> getTeams(){
        return teams;
    }


    public void addTeam(Team team){
        this.teams.add(team);
        saveTeamsToFile();
    }

    public void deleteTeam(Team team){
        this.teams.remove(team);
        saveTeamsToFile();
    }

    public void saveTeamsToFile(){
        fileHandler.saveAllToFile(teams);
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
