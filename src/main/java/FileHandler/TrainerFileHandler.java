package FileHandler;

import Models.Trainer;
import Repositories.TrainerRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class TrainerFileHandler {
    private final String fileName = "trainersList.txt";
    private TrainerRepository trainerRepository;


    public void setTrainerRepository(TrainerRepository trainerRepository){
        this.trainerRepository = trainerRepository;
    }






    public void create() throws IOException {
        if (trainerRepository.getTrainerArrayList() == null) {
            throw new IllegalStateException("Trainer repository entry is empty.");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(trainerRepository.getCurrentTrainer().toStringFile());
        writer.newLine();
        writer.flush();
    }

    public void read() {
        try (Scanner scan = new Scanner(new File(fileName))){

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] attributes = line.split(",");
                Trainer trainer = new Trainer(
                        attributes[0],
                        LocalDate.parse(attributes[1]),
                        Integer.parseInt(attributes[2])
                        // add one for the teams

                );

                trainerRepository.getTrainerArrayList().add(trainer);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }





    public void update() {

    }


    public void delete() {

    }
}
