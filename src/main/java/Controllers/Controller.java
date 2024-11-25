package Controllers;

import Models.SwimmingClub;
import Models.Team;

import java.util.ArrayList;

public class Controller {
    private SwimmingClub swimmingClub;

    public Controller(SwimmingClub swimmingClub){
        this.swimmingClub = swimmingClub;
    }

    public String getListOfTeams(){
        ArrayList<Team> teams = swimmingClub.getTeams();
        String result = "";
        for(Team t: teams){

        }
        return  "";
    }
}
