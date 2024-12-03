package Controllers;

import Models.Contingent;
import Models.Member;
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
            if (repository.createMemberContingent(memberId) != null) {
                return "Du oprettede koningent på følgende id: " + memberId + "\n";
            } else {
                return "Det indtastede id findes ikke: " + memberId + "\n";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    // Gets a member contingents
    public String getMemberContingents(int memberId) {
        // For print
        StringBuilder sb = new StringBuilder();

        try {
            // Store all
            ArrayList<Contingent> contingents = repository.allContingent();

            // Loops the ArrayList
            for (Contingent c : contingents) {
                if (c.getMemberId() == memberId) {
                    sb.append(c).append("\n");
                }
            }

            // Returns a message
            return sb.isEmpty()
                    ? "Ingen data"
                    : sb.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public String deleteContingent(int id) {
        try {
            // If we can delete
            if (repository.deleteSpecificContingent(id)) {
                return "Du slettede kontingent med følgende id: " + id + "\n";
            } else {
                return "Der findes ikke nogen kontingenter med id: " + id + "\n";
            }
        } catch (IOException e) {
            // Something terrible went wrong
            return e.getMessage();
        }
    }

    public String readAll() {
        // For print
        StringBuilder sb = new StringBuilder();

        try {
            ArrayList<Contingent> contingents = repository.allContingent();

            // Loops through all if array is not empty
            if (contingents != null) {
                for (Contingent c : contingents) {
                    sb.append(c.toString()).append("\n");
                }
            }

            // Return message
            return sb.isEmpty()
                    ? "Der er ingen kontingenter oprettet\n"
                    : sb.toString();
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public String getExpectedEarnings() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Double> earnings = repository.getExpectedEarnings();
        double sum = 0.0;

        for(double number : earnings){
            sb.append(sum).append(" + ").append(number).append(" = ");
            sum += number;
            sb.append(sum).append(" kr.").append("\n");
        }

        if (sum != 0) {
            sb.append("\n").append("I alt:").append("\n");
            sb.append(sum).append("kr.").append("\n");
        }


        return sb.isEmpty()
                ? "Der er ingen medlemmer. I tjener ikke noget endnu\n"
                : sb.toString();
    }

    public String checkArrears() {
        StringBuilder sb = new StringBuilder();

        for (Member m : repository.getArrears()) {
            sb.append(m).append("\n");
        }

        return sb.isEmpty()
                ? "Alle har betalt kontingent\n"
                : sb.toString();
    }

}
