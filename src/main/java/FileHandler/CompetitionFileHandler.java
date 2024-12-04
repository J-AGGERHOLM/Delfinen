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

    private String filePath = "CompetitionResults.csv"; // Default file

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }


    public void create() throws IOException {
        if (competition == null) {
            throw new IllegalStateException("Competition entry is empty.");
        }

        // Get the last ID from the file
        int lastId = competitions.isEmpty() ? 0 : competitions.get(competitions.size() - 1).getId();

        // Increment the ID
        competition.setId(lastId + 1);

        // Save the new competition
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(competition.toString());
        writer.newLine();
        writer.flush();
        writer.close();
    }


    private ArrayList<Competition> competitions = new ArrayList<>();

    public void read() {
        try {
            File compFile = new File(filePath);
            ArrayList<Competition> tempArray = new ArrayList<>();

            try (Scanner sc = new Scanner(compFile)) {
                sc.nextLine();

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();

                    String[] attributes = line.split(",");

                    if (attributes.length == 4) {
                        Competition competition = new Competition(
                                Integer.parseInt(attributes[0]), // ID
                                attributes[1], // Event
                                Integer.parseInt(attributes[2]), // Placement
                                Double.parseDouble(attributes[3]) // Time
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

    public void delete(int id) {

        read();
        if (competitions == null) {
            return;
        }

        Competition forDeletion = null;

        for (Competition c : competitions) {
            if (c.getId() == id) {
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
                writer.newLine();

            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String testFilePath) {
        this.filePath = testFilePath;
    }
}

