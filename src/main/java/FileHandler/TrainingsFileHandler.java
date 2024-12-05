package FileHandler;

import Models.Training;
import Repositories.MemberRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingsFileHandler {
    private ArrayList<Training> trainings;
    final String filePath = "TrainingData.csv";

    public void saveTrainingData(ArrayList<Training> trainingData) {
        this.trainings = trainingData;
    }

    public void create() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,false))) {
            for(Training td : trainings) {
                bw.write(td.toStringFile() + "\n");
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("File ikke fundet!");
        }
    }

    public ArrayList<Training> read() {
        ArrayList<Training> trainingData = new ArrayList<>();
        try(Scanner scan = new Scanner(new File(filePath))) {
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] data = line.split(",");
                String discipline = data[0];
                String id  = data[1];
                String time = data[2];
                String date = data[3];
                trainingData.add(new Training(discipline, Integer.parseInt(id), time, date));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fil ikke fundet! ");
        }
        return trainingData;
    }



}
