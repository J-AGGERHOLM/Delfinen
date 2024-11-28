package FileHandler;

import Controllers.TrainingController;
import Models.CompetitiveSwimmer;
import Models.Training;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingsFileHandler {
    private ArrayList<Training> trainings;
    final String filePath = "TrainingData.txt";
    private TrainingController tc = new TrainingController();

    public void saveTrainingData(ArrayList<Training> trainingData) {
        this.trainings = trainingData;
    }

    public void create() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for(Training td : trainings) {
                bw.write(td.toStringFile() + "\n");
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }

    public ArrayList<Training> read() {
        ArrayList<Training> trainingData = new ArrayList<>();
        try(Scanner scan = new Scanner(new File(filePath))) {
            while(scan.hasNextLine()) {

                String line = scan.nextLine();
                String[] data = line.split(",");
                String discipline = data[0];
                CompetitiveSwimmer swimmer = tc.getSwimmerByID(Integer.parseInt(data[1]));
                String time = data[2];

                trainingData.add(new Training(discipline, swimmer, time));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found! ");
        }
        return trainingData;
    }



}
