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


    //The teams are saved with fields separated with a ; and the list of team member IDs seperated with :
    //If the team has no trainer, the trainer ID is set to 0. Please refrain from giving an actual trainer this ID :)
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

        if(allMembers == null){
            System.out.println("Error from teams filehandler: members list is null");
            return teams;
        }
        if(trainers == null){
            System.out.println("Error from teams filehandler: trainers list is null");
            return teams;
        }

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
            //If the ID is 0, the trainer is set to null.

            boolean foundTrainer = false;

            if(trainerID == 0){
                teamTrainer = null;
            }else{
                for(Trainer t: trainers){
                    if(t.getId() == trainerID){
                        teamTrainer = t;
                        foundTrainer = true;
                        break;
                    }
                }

                if(foundTrainer == false){
                    teamTrainer = null;
                }

            }


            //getting the teams members:
            //accounting for if the member doesn't exist
            ArrayList<Person> teamMembers = new ArrayList<Person>();
            for(int id : memberIDs){
                boolean foundMember = false;
                for(Person m : allMembers){
                    if(m.getId() == id){
                        teamMembers.add(m);
                        foundMember = true;
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
                //if there is no trainer, we put the ID 0 in the save file
                if(team.getTrainer() == null){
                    result += "0" + fieldSeperator;
                }else {
                    result += team.getTrainer().getId() + fieldSeperator;
                }
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
