package FileHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Models.Competition;


public class CompetitionFileHandler extends SuperHandler {

    Competition competition;

    final String filePath = "CompetitionResults.txt";


    public void setCompetition(Competition competition) {
        this.competition = competition;
    }



    @Override
    public void create() throws IOException {

        if (competition == null) {
            throw new IllegalStateException("Competition entry is empty.");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(competition.toString());
        writer.newLine();
        writer.flush();
    }


    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
