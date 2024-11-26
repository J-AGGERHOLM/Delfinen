package FileHandler;

import Models.Person;
import Models.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TeamFileHandler {

    private final String filePath = "Teams.txt";


    //Waiting for persons to be done. I need to be able to read the list of persons to create the
    //person objects in the teams, or a way to get already existing person objects and add them to the team
    //arraylist
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

    public boolean checkIfTeamExists(int teamId){
        File file = new File(filePath);

        try{
            Scanner sc = new Scanner(new File(String.valueOf(file)));
            //Skip the header:
            sc.nextLine();

            while(sc.hasNextLine()){
                //lets try to make a reader for the format we made
                String line = sc.nextLine();
                String[] splitLine = line.split(";");
                String[] memberIDs = splitLine[3].split(":");
                if (splitLine[0].equals(teamId)){
                    return true;
                }

            }
            return false;

        }catch (FileNotFoundException e){
            System.out.println("Team file handler: append new team error: file not found");
            return false;
        }
    }

}
