package Controllers;

import FileHandler.CompetitionFileHandler;
import Models.Competition;

import java.util.ArrayList;

public class CompetitionController {



    //-----------------------------competitions methods start-------------------------------------

    public String readCompetition() {

        //Instance of the filehandler
        CompetitionFileHandler competitionFileHandler = new CompetitionFileHandler();

        //document is read, and loaded into a variable to work with.
        competitionFileHandler.read();
        ArrayList<Competition> competitionArrayList = competitionFileHandler.getCompetitions();


        //If the arrayList variable is empty, this message is returned
        if (competitionArrayList.isEmpty()) {
            return "No competitions found.";
        }

        //string builder to format the attributes from the arrayList objects
        StringBuilder result = new StringBuilder("Competitions:\n");
        for (Competition competition : competitionArrayList) {
            result.append("Event: ").append(competition.getEvent())
                    .append(", Placement: ").append(competition.getPlacement())
                    .append(", Time: ").append(competition.getTime()).append("\n");
        }
        return result.toString();
    }


    //-----------------------------competitions methods end---------------------------------------

}
