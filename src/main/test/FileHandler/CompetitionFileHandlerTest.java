package FileHandler;

import FileHandler.CompetitionFileHandler;
import Models.Competition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompetitionFileHandlerTest {

    private final String testFilePath = "CompetitionTest.txt";
    private CompetitionFileHandler fileHandler;

    @BeforeEach
    void setUp() {
        fileHandler = new CompetitionFileHandler();
        fileHandler.setFilePath(testFilePath); // Sets the test file path
        clearFile(); //double-check to empty
        writeHeader(); // Write the expected header for formatting, otherwise it will error with the read()
    }

    @AfterEach
    void tearDown() {
        clearFile(); // Deletes the test file after the test
    }

    @Test
    void create() throws IOException {
        // ARRANGE
        ArrayList<Competition> expectedCompetitions = new ArrayList<>();
        Competition competition1 = new Competition(22, "Nørrebro", 1, 43.2);
        expectedCompetitions.add(competition1);

        fileHandler.setCompetition(competition1);

        // ACT
        System.out.println("bip-bop creating competition entry...");
        fileHandler.create();

        System.out.println("bip-bop reading competition entries...");
        fileHandler.read();

        System.out.println("Competitions read: " + fileHandler.getCompetitions());

        // ASSERT
        assertEquals(1, fileHandler.getCompetitions().size());
        assertEquals(expectedCompetitions.get(0).toString(), fileHandler.getCompetitions().get(0).toString());
    }

    private void clearFile() {
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    private void writeHeader() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFilePath))) {
            writer.write("ID,Event,Placement,Time");
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing header to test file", e);
        }
    }
}
