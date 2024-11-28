package Controllers;

import Models.Team;
import Models.Trainer;
import Repositories.TrainerRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class TrainerController {
    private TrainerRepository trainerRepository;



    public TrainerController(){
        trainerRepository = new TrainerRepository();
    }

//_______________________________getter and setter methods___________________________________

    public ArrayList<Trainer> getTrainerArrayList(){
        return trainerRepository.getTrainerArrayList();
    }

    public Trainer getCurrentTrainer(){
        return trainerRepository.getCurrentTrainer();
    }

    public String setFullName(String newName){
        try {
            trainerRepository.getCurrentTrainer().setName(newName);
            return "Members name changed successfully";
        } catch (Exception e){
            return "An error ocurred while changing the name";
        }
    }
//____________________________________________________________________________________________________-

    public String displayTrainers(){
        StringBuilder sb = new StringBuilder();
        for(Trainer trainer : trainerRepository.getTrainerArrayList()){
            sb.append(trainer).append("\n");
        }
        return sb.isEmpty()
                ? "No trainer found"
                : sb.toString();
    }

    public String displayTrainersInformation(){
        if(trainerRepository.getCurrentTrainer() == null){
            return "";
        } else {
            return trainerRepository.displayTrainerInformation();
        }
    }
    public String chooseSpecificTrainerById(int idToCheck) {
        if (!trainerRepository.chooseSpecificTrainerById(idToCheck)){
            return "Member with ID: " + idToCheck + " Was not found";
        } else {
            return "Member with ID: " + idToCheck + " found :)";
        }
    }

    public String chooseSpecificTrainerByName(String name) {
        if (!trainerRepository.chooseSpecificTrainerByName(name)){
            return "Member with name: " + name + " was not found";
        } else {
            return "Member with name: " + getCurrentTrainer().getFullName() + " found :)";
        }
    }

    public String createTrainerWithTeam(String name, LocalDate birthday, Team team){
        if(!trainerRepository.createTrainer(name, birthday, team)){
            return "Failed to create trainer, please try again";
        }
        return "Trainer was successfully created";
    }

    public String createTrainerWithoutTeam(String name, LocalDate birthday){
        if(!trainerRepository.createTrainer(name, birthday)){
            return "Failed to create trainer, please try again";
        }
        return "Trainer was successfully created";
    }

    public String deleteTrainer(){
        if (!trainerRepository.deleteTrainer()){
            return "Failed to create member, please try again";
        }
        return "Member deleted successfully";
    }

}
