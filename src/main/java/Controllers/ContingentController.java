package Controllers;

import Models.Contingent;
import Repositories.ContingentRepository;

import java.io.IOException;
import java.util.ArrayList;

public class ContingentController {
    private final ContingentRepository repository;

    public ContingentController() {
        repository = new ContingentRepository();
    }

    // Creates member
    public String createMemberContingent(int memberId) {
        try {
            // If it's true
            if (repository.createMemberContingent(memberId)) {
                return "Du oprettede koningent på følgende id: " + memberId;
            }else{
                return "Det indtastede id findes ikke: " + memberId;
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    // Gets a member contingents
    public String getMemberContingents(int memberId)
    {
        // For print
        StringBuilder sb = new StringBuilder();

        try{
        // Store all
        ArrayList<Contingent> contingents = repository.getAllContingent();

        // Loops the ArrayList
        for (Contingent c : contingents) {
            if (c.getMemberId() == memberId) {
                sb.append(c);
            }
        }

        // Returns a message
        return sb.isEmpty()
                ? ""
                : sb.toString();
        } catch (IOException e){
            return e.getMessage();
        }
    }

    public String deleteContingent(int id) {
        try {
            // If we can delete
            if(repository.deleteSpecificContingent(id)){
                return "Du slettede kontingent med følgende id: " + id;
            }else{
                return "Der findes ikke nogen kontingenter med id: " + id;
            }
        } catch (IOException e){
            // Something terrible went wrong
            return e.getMessage();
        }
    }

    public String readAll(){
        // For print
        StringBuilder sb = new StringBuilder();

        try{
            // Loops through all
            for (Contingent c : repository.getAllContingent()) {
                sb.append(c.toString());
            }

            // Return message
            return sb.isEmpty()
                    ? "Der er ingen kontingenter oprettet"
                    : sb.toString();
        } catch (IOException e){
            return e.getMessage();
        }

    }

    public String getExpectedEarnings(){
        double sum = repository.getExpectedEarnings();

        return sum == 0
                ? "Der er ingen medlemmer. I tjener ikke noget endnu"
                : String.valueOf(sum);
    }

    // ------------------------------- getter ----------------------------------


}
