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
            return "Medlemmets navn blev ændret med succes"; // Translated: "Member's name changed successfully"
        } catch (Exception e){
            return "Der opstod en fejl under ændring af navnet"; // Translated: "An error occurred while changing the name"
        }
    }

    //____________________________________________________________________________________________________-

    public String displayTrainers(){
        StringBuilder sb = new StringBuilder();
        for(Trainer trainer : trainerRepository.getTrainerArrayList()){
            sb.append(trainer).append("\n");
        }
        return sb.isEmpty()
                ? "Ingen trænere fundet" // Translated: "No trainer found"
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
            return "Medlem med ID: " + idToCheck + " blev ikke fundet"; // Translated: "Member with ID: X was not found"
        } else {
            return "Medlem med ID: " + idToCheck + " fundet :)"; // Translated: "Member with ID: X found :)"
        }
    }

    public String chooseSpecificTrainerByName(String name) {
        if (!trainerRepository.chooseSpecificTrainerByName(name)){
            return "Medlem med navn: " + name + " blev ikke fundet"; // Translated: "Member with name: X was not found"
        } else {
            return "Medlem med navn: " + getCurrentTrainer().getFullName() + " fundet :)"; // Translated: "Member with name: X found :)"
        }
    }

    public String createTrainerWithTeam(String name, LocalDate birthday, Team team){
        if(!trainerRepository.createTrainer(name, birthday, team)){
            return "Kunne ikke oprette træner, prøv igen"; // Translated: "Failed to create trainer, please try again"
        }
        return "Træner blev oprettet med succes"; // Translated: "Trainer was successfully created"
    }

    public String createTrainerWithoutTeam(String name, LocalDate birthday){
        if(!trainerRepository.createTrainer(name, birthday)){
            return "Kunne ikke oprette træner, prøv igen"; // Translated: "Failed to create trainer, please try again"
        }
        return "Træner blev oprettet med succes"; // Translated: "Trainer was successfully created"
    }

    public boolean updateInformation(){
        return trainerRepository.updateInformation();
    }

    public String deleteTrainer(){
        if (!trainerRepository.deleteTrainer()){
            return "Kunne ikke slette medlem, prøv igen"; // Translated: "Failed to delete member, please try again"
        }
        return "Medlem slettet med succes"; // Translated: "Member deleted successfully"
    }
}

