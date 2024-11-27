package Repositories;

import FileHandler.TrainerFileHandler;
import Models.Team;
import Models.Trainer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrainerRepository {
    private ArrayList<Trainer> trainerArrayList;
    private Trainer currentTrainer;
    private TrainerFileHandler trainerFileHandler;


    public TrainerRepository() {
        trainerFileHandler = new TrainerFileHandler();
        trainerFileHandler.setTrainerRepository(this);
        trainerArrayList = new ArrayList<>();
        trainerFileHandler.read();
    }


    //___________________________________________________Getter Method__________________________________________________

    public ArrayList<Trainer> getTrainerArrayList() {
        return trainerArrayList;
    }

    public Trainer getCurrentTrainer() {
        return currentTrainer;
    }

    //__________________________________________________________________________________________________________________


    public int getNewId(){
        return trainerArrayList.size() + 101; // trainers id come with 100 before to differentiate.
    }

    // returns a list of all members
    public String displayTrainers() {
        String result = "";
        for (Trainer t : trainerArrayList) {
            result += t.getFullName() + "\n";        }
        return result;
    }


    public String displayTrainerInformation() {
        return currentTrainer.toString();
    }


    // Find a specific member with their id from the list for editing, deleting, payments, etc
    public boolean chooseSpecificTrainerById(int idToCheck) {
        for (Trainer t : trainerArrayList) {
            if (idToCheck == t.getId()) {
                currentTrainer= t;
                return true;
            }
        }
        currentTrainer = null;
        return false;
    }
    // Find a specific member with their Name from the list for editing, deleting, payments, etc
    public boolean chooseSpecificTrainerByName(String name) {
        for (Trainer t : trainerArrayList) {
            if (t.getFullName().toLowerCase().contains(name)) {
                currentTrainer = t;
                return true;
            }
        }
        currentTrainer = null;
        return false;
    }

// with team
    public boolean createTrainer(String name, LocalDate birthday, Team team) {
        try {
            // Create a new Member object
            Trainer trainer = new Trainer(name, birthday, 0, team);
            // Assign the current member
            currentTrainer = trainer;
            // Add the member to the ArrayList
            trainerArrayList.add(currentTrainer);
            // Save the member to file
            trainerFileHandler.create();
            return true;
        } catch (IOException e) {
            System.err.println("Error occurred while creating a trainer: " + e.getMessage()); //do this in ui instead
            return false;
        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage()); // do this in ui instead
            return false;
        }
    }
// without team
    public boolean createTrainer(String name, LocalDate birthday) {
        try {
            // Create a new Member object
            Trainer trainer = new Trainer(name, birthday, 0);
            // Assign the current member
            currentTrainer = trainer;
            // Add the member to the ArrayList
            trainerArrayList.add(currentTrainer);
            // Save the member to file
            trainerFileHandler.create();
            return true;
        } catch (IOException e) {
            System.err.println("Error occurred while creating a trainer: " + e.getMessage()); //do this in ui instead
            return false;
        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage()); // do this in ui instead
            return false;
        }
    }

    public boolean deleteTrainer(){
        try {
            trainerArrayList.remove(currentTrainer);
            trainerFileHandler.update();
            return true;
        } catch (Exception e){
            return false;
        }

    }



}
