package FileHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Models.Competition;


public class CompetitionFileHandler {

    Competition competition;

    final String filePath = "CompetitionResults.txt";


    public void setCompetition(Competition competition) {
        this.competition = competition;
    }


    public void create() throws IOException {

        if (competition == null) {
            throw new IllegalStateException("Competition entry is empty.");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(competition.getEvent() + "," + competition.getPlacement() + "," + competition.getTime());
        writer.newLine();
        writer.flush();
    }


    private ArrayList<Competition> competitions = new ArrayList<>();

    public void read() {
        try {
            File compFile = new File(filePath);
            ArrayList<Competition> tempArray = new ArrayList<>();

            try (Scanner sc = new Scanner(compFile)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] attributes = line.split(",");

                    if (attributes.length == 3) {
                        Competition competition = new Competition(
                                attributes[0],
                                Integer.parseInt(attributes[1]),
                                Double.parseDouble(attributes[2])
                        );
                        tempArray.add(competition);
                    }
                }
            }
            competitions = tempArray;

        } catch (Exception e) {
            throw new RuntimeException("Error reading from file", e);
        }
    }

    //helper method for the read function.
    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    public void update() {

    }

    public void delete(String eventName) {

        read();
        if (competitions == null) {
            return;
        }

        Competition forDeletion = null;

        for (Competition c : competitions) {
            if (c.getEvent().equals(eventName)) {
                forDeletion = c;
            }
        }
        if (forDeletion != null) {
            competitions.remove(forDeletion);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.write("");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            for (Competition c : competitions) {
                writer.write(c.getEvent() + "," + c.getPlacement() + "," + c.getTime());
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

