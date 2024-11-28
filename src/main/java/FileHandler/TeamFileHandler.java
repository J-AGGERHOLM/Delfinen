package FileHandler;

import Models.Member;
import Models.Person;
import Models.Team;
import Models.Trainer;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TeamFileHandler {

    private final String filePath = "Teams.txt";
    private final String fieldSeperator = ";";
    private final String listItemSeperator = ":";


    //Waiting for persons to be done. I need to be able to read the list of persons to create the
    //person objects in the teams, or a way to get already existing person objects and add them to the team
    //arraylist
    public ArrayList<Team> readAllTeams(ArrayList<Member> allMembers, ArrayList<Trainer> trainers){

        System.out.println("reading teams");
        File teamsFile = new File(filePath);
        Scanner sc = null;

        try{
            sc = new Scanner(teamsFile);
        }catch (FileNotFoundException e){
            System.out.println("ERROR in teamsfilehandler: file not found in readallteams");
            return null;
        }

        //skip the header:
        sc.nextLine();

        ArrayList<Team> teams = new ArrayList<Team>();

        while(sc.hasNextLine()){
            String fileLine = sc.nextLine();
            String[] fields = fileLine.split(";");

            //getting the member IDS, and saving everything in variables that are more readable:
            int teamID = Integer.parseInt(fields[0]);
            String teamName = fields[1];
            int trainerID = Integer.parseInt(fields[2]);

            String[] membersString = fields[3].split(":");
            ArrayList<Integer> memberIDs = new ArrayList<Integer>();
            for(String s:membersString){
                memberIDs.add(Integer.parseInt(s));
            }

            //getting the team trainer:

            Trainer teamTrainer = null;
            //getting the trainer object:
            for(Trainer t: trainers){
                if(t.getId() == trainerID){
                    teamTrainer = t;
                    break;
                }
            }
            if(teamTrainer == null){
                System.out.println("ERROR: Trainer not found in database");
                return null;
            }


            //getting the teams members:
            ArrayList<Person> teamMembers = new ArrayList<Person>();
            for(int id : memberIDs){
                for(Person m : allMembers){
                    if(m.getId() == id){
                        teamMembers.add(m);
                        break;
                    }
                }
            }



            //Making the team object:
            Team readTeam = new Team(teamID, teamName, teamTrainer, teamMembers);
            teams.add(readTeam);
        }

        return teams;
    }

    public void appendNewTeam(Team team) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
        String result = "";
        result += team.getId() + ";";
        result += team.getName() + ";";
        result += team.getTrainer().getId() + ";";
        for(Person p: team.getMembers()){
            result += p.getId() + ":";
        }
        result += ";";

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


    public boolean saveAllToFile(ArrayList<Team> teams){

        PrintStream teamsFile;

        try{
            teamsFile = new PrintStream(filePath);


            //adds the header:
            teamsFile.println("team ID;Team name;Trainer ID; member ID 1: member ID2: etc: ;");

            for(Team team: teams){
                String result = "";
                result += team.getId() + fieldSeperator;
                result += team.getName() + fieldSeperator;
                result += team.getTrainer().getId() + fieldSeperator;
                for(Person p: team.getMembers()){
                    result += p.getId() + listItemSeperator;
                }
                result += fieldSeperator;
                teamsFile.println(result);
            }

            teamsFile.close();

            return true;



        }catch(FileNotFoundException e){
            return false;
        }


    }


}
