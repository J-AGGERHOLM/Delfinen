package FileHandler;

import Models.Competition;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionFileHandlerTest {

    @Test
    void create() {

        //ARRANGE
        ArrayList<Competition> compareArray = new ArrayList<>();
        Competition competition1 = new Competition(22, "Nørrebro", 1, 43.2);
        compareArray.add(competition1);

        CompetitionFileHandler fileHandler = new CompetitionFileHandler();
        fileHandler.setCompetition(competition1);

         String filePath = "CompetitionTest.txt";


        //ACT

        try {
            fileHandler.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileHandler.read();


        //ASSERT

        assertEquals(compareArray, fileHandler.getCompetitions());


    }
}
