package FileHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Models.Competition;


public class CompetitionFileHandler extends SuperHandler {

    Competition competition;

    final String filePath = "CompetitionResults.txt";

    @Override
    public void create() throws IOException {
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
