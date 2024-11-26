package FileHandler;

import Models.Person;
import Models.Team;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TeamFileHandler {

    private final String filePath = "Teams.txt";

    public ArrayList<Team> readAllTeams(){
        return new ArrayList<Team>();
    }

    public void appendNewTeam(Team team) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
        String result = "";
        result += team.getId() + ";";
        result += team.getName() + ";";
        result += team.getTrainer().getId() + ";";
        result += "{";
        for(Person p: team.getMembers()){
            result += p.getId() + ";";
        }
        result += "}";

        bufferedWriter.write(result);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
