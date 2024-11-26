package Repositories;

import FileHandler.CompetitionFileHandler;
import Models.Competition;

import java.io.IOException;

public class CompetitionRepository {

    CompetitionFileHandler competitionFileHandler = new CompetitionFileHandler();




    public void commitCompetitionEntry(String event, int placement, double time) {
        Competition competition = new Competition(event, placement, time);

        competitionFileHandler.setCompetition(competition);

        //Competition object is comitted to the document
        try {
            competitionFileHandler.create();
        } catch (IOException e) {
            System.out.println("Error: Something went wrong trying to create the file");
        }

    }


}
