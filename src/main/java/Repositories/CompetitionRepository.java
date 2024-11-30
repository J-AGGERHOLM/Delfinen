package Repositories;

import FileHandler.CompetitionFileHandler;
import Models.Competition;
import Models.Contingent;

import java.io.IOException;
import java.util.ArrayList;

public class CompetitionRepository {

    CompetitionFileHandler competitionFileHandler = new CompetitionFileHandler();




    public void commitCompetitionEntry(String event, int placement, double time) {
        Competition competition = new Competition(getId(),event, placement, time);

        competitionFileHandler.setCompetition(competition);

        //Competition object is comitted to the document
        try {
            competitionFileHandler.create();
        } catch (IOException e) {
            System.out.println("Error: Something went wrong trying to create the file");
        }

    }


    public void searchForEntry(int competitionId){
        competitionFileHandler.delete(competitionId);
    }

    public ArrayList<Competition> getAllEntries() {
        competitionFileHandler.read();
        ArrayList<Competition> competitions =  competitionFileHandler.getCompetitions();
        return competitions;
    }


    public int getId() {
        int id = getAllEntries().size();
        return ++id;
    }

}
