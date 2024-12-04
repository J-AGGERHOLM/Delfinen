package Controllers;

import Enums.SwimmingDisciplines;
import Models.*;
import Repositories.MemberRepository;
import Repositories.TrainerRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeamsController {

    private SwimmingClub swimmingClub;
    private MemberRepository memberRepository;

    //For creating new teams:
    private ArrayList<Person> newTeamList = new ArrayList<Person>();
    private Trainer newTeamTrainer;

    //for editing teams:
    private Team currentTeam;


    //TEST
    private TrainerRepository trainerRepo;
    private  ArrayList<Trainer> trainerList;

    public TeamsController(){

        try{
            memberRepository = createDummyMemberRepository();

        }catch (IOException e){
            System.out.println("An IO exception occured on create dummy repository");
        }

        trainerRepo = new TrainerRepository();
        swimmingClub = new SwimmingClub();
        swimmingClub.readTeamsFromFile(memberRepository.getMemberArrayList(), trainerList);

    }

    //called by the UI whenever the teams menu is accessed:
    public void updateTrainers(){
        trainerRepo = new TrainerRepository();
        trainerList = trainerRepo.getTrainerArrayList();
    }





    public String getListOfTeams() {
        ArrayList<Team> teams = swimmingClub.getTeams();
        String result = "";
        int counter = 0;
        for (Team t : teams) {
            counter++;
            result += counter + ". " + t + "\n";
        }
        return result;
    }

    public String getTeam(int choice) {
        String result = "";
        ArrayList<Team> teams = swimmingClub.getTeams();
        int counter = 0;
        for (Team t : teams) {
            counter++;
            if (counter == choice) {
                return t.getFullData();
            }
        }

        return "No team Matches that selection";
    }

    //Team creation--------------------------------------

    public void resetTeamCreation(){
        newTeamList = new ArrayList<Person>();
    }

    public String addPersonToNewTeam(int id){
        for(Person p: memberRepository.getMemberArrayList()){
            if(p.getId() == id){
                for(Person q: newTeamList){
                    if(p.equals(q)){
                        return p.getFullName() + "Is already on the team";
                    }
                }
                newTeamList.add(p);
                return p.getFullName() + " Added to team";
            }
        }

        return "That person could not be found.";
    }

    public Boolean assignTrainer(int id){

        for(Trainer t: trainerList){
            if(t.getId() == id){
                newTeamTrainer = t;
                return true;
            }
        }
        return false;
    }

    public void finalCreateNewTeam(String teamName){
        int id = swimmingClub.getNewTeamID();
        Team team = new Team(id, teamName, newTeamTrainer, newTeamList );
        swimmingClub.addTeam(team);

    }

    //-----------------------------------deleting team----------------------------

    public boolean deleteTeam(int id){
        for(Team t: swimmingClub.getTeams()){
            if(t.getId() == id){
                swimmingClub.deleteTeam(t);
                return true;
            }
        }

        return false;
    }

    //-------------------------------------Team editing-----------------------------
    //Team editing sets one team as the current team, which the editing functions then make edits to.
    public boolean setCurrentTeam(int id){
       for(Team t: swimmingClub.getTeams()){
           if(t.getId() == id){
               currentTeam = t;
               return true;
           }
       }
       return false;
    }

    public String getCurrentTeam(){
        return currentTeam.getFullData();
    }

    public String addRemoveFromCurrentTeam(int id){
        Person personToToggle = null;
        boolean personExists = false;
        for(Person p: memberRepository.getMemberArrayList()){
            if(p.getId() == id){
                personToToggle = p;
                personExists = true;
            }
        }

        if(!personExists){
            return "Denne person kunne ikke findes i databasen";
        }

        //if the person is on the team we remove them from it:
        for(Person p: currentTeam.getMembers()){
            if(p.equals(personToToggle)){
                currentTeam.removeMember(p);
                return p.getFullName() + "Er blevet fjernet fra holdet";
            }
        }

        //if we dont find them on the team we add them to it:
        if(personToToggle != null){
            currentTeam.addMember(personToToggle);
        }
        return personToToggle.getFullName() + "er blevet tilføjet til holdet";
    }

    public boolean assignTrainerToCurrentTeam(int id){
        for(Trainer t: trainerList){
            if(t.getId() == id){
                currentTeam.setTrainer(t);
                return true;
            }
        }

        return false;
    }

    public void setCurrentTeamName(String name){
        currentTeam.setName(name);
    }

    //-----------------------DELETING MEMBERS AND TRAINERS------------------------------------------

    //Call this when a member gets deleted from the member database to purge it from the teams
    public void deleteMemberFromAllTeams(Member member){
        swimmingClub.deleteMemberFromAllTeams(member);
    }

    //Call this when a trainer gets deleted from the trainer database to purge it from the teams
    public void deleteTrainerFromAllTeams(Trainer trainer){
        swimmingClub.deleteTrainerFromAllTeams(trainer);
    }





    public String getListOfMembers(){
        String result = "LIST OF MEMBERS:\n";
        for(Member m: memberRepository.getMemberArrayList()){
            result += m + "\n";
        }
        return  result;
    }

    public String getListOfTrainers(){
        String result = "LIST OF TRAINERS:\n";
        for (Trainer t: trainerList){
            result += t + "\n";
        }

        return result;
    }

    //-------------------TEST FUNCTIONS: REMOVE WHEN FEATURES ARE COMPLETE---------------------------------

    private SwimmingClub benjaminTest() {
        SwimmingClub swimmingClub = new SwimmingClub();
        // Creating dummy data for the team read feature:ArrayList<Team> teams = new ArrayList<Team>();
        //Team juniorTeam = new Team("Junior Team", new Person(1, "Hans Hansen"));
        Team juniorTeam = new Team( 1,"Junior Team", new Trainer("Hans Hansen", LocalDate.of(1995, 2, 23), 1));
        CompetitiveSwimmer swimmer1 = new CompetitiveSwimmer("Mikkel Mikkelson", LocalDate.of(1976, 7, 7), 2, true, true, SwimmingDisciplines.BACKCRAWL);
        CompetitiveSwimmer swimmer2 = new CompetitiveSwimmer("Søren Sørensen", LocalDate.of(2003, 4, 12), 3, true, true, SwimmingDisciplines.BREASTSTROKE);
        CompetitiveSwimmer swimmer3 = new CompetitiveSwimmer("Ragnar Lodbrok", LocalDate.of(1976, 7, 7), 4, true, true, SwimmingDisciplines.BUTTERFLY);
        CompetitiveSwimmer swimmer4 = new CompetitiveSwimmer("William Williamson", LocalDate.of(1976, 7, 7), 5, true, true, SwimmingDisciplines.CRAWL);
        juniorTeam.addMember(swimmer1);
        juniorTeam.addMember(swimmer2);
        juniorTeam.addMember(swimmer3);
        juniorTeam.addMember(swimmer4);
        swimmingClub.addTeam(juniorTeam);
        Team seniorTeam = new Team(2, "Senior Team", new Trainer("Mads Mikkelson", LocalDate.of(1982, 11, 11), 6));
        CompetitiveSwimmer swimmer5 = new CompetitiveSwimmer("Harry Harrison", LocalDate.of(2007, 12, 12), 7, true, true, SwimmingDisciplines.CRAWL);
        CompetitiveSwimmer swimmer6 = new CompetitiveSwimmer("Anya Annison", LocalDate.of(2001, 10, 29), 8, true, true, SwimmingDisciplines.BUTTERFLY);
        CompetitiveSwimmer swimmer7 = new CompetitiveSwimmer("Benny Bennison", LocalDate.of(1999, 9, 12), 9, true, true, SwimmingDisciplines.BREASTSTROKE);
        CompetitiveSwimmer swimmer8 = new CompetitiveSwimmer("Carol Carolson", LocalDate.of(2000, 1, 1), 10, true, true, SwimmingDisciplines.BACKCRAWL);
        seniorTeam.addMember(swimmer5);
        seniorTeam.addMember(swimmer6);
        seniorTeam.addMember(swimmer7);
        seniorTeam.addMember(swimmer8);
        swimmingClub.addTeam(seniorTeam);
        return swimmingClub;
    }

    private MemberRepository createDummyMemberRepository() throws IOException {
        MemberRepository mr = new MemberRepository();

        //We dont need to do this as member repository saves the data
        /*
        mr.createMember("Alice Annison", LocalDate.of(1996, 11, 1), true, true);
        mr.createMember("Benjamin Bennison", LocalDate.of(2005,11,1),true, true);
        mr.createMember("Carol Caro", LocalDate.of(1992, 11, 1), true, true);
        mr.createMember("Daniel Danielson", LocalDate.of(1987,11,1),true, true);
        mr.createMember("Edward Eddison", LocalDate.of(1999, 11, 1), true, true);
        mr.createMember("Fred Fochs", LocalDate.of(1966,11,1),true, true);
        mr.createMember("Gemma Garter", LocalDate.of(1997, 11, 1), true, true);
        mr.createMember("Harry Harrison", LocalDate.of(2001,11,1),true, true);
        mr.createMember("Inger Ilton", LocalDate.of(2002, 11, 1), true, true);
        mr.createMember("Janet Jacobs", LocalDate.of(1994,11,1),true, true);
        */
        return mr;

    }


}
