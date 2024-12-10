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
            // Find Member
            Member targetMember = null;
            for (Member m : repository.getAllMembers()) {
                if (m.getId() == memberId) {
                    targetMember = m;
                    break;
                }
            }

            // If member is null
            if (!targetMember.isPaid()) {
                return "Ingen data";
            }

            sb.append("Id, Name (MemberId), Price, Payment Date\n");

            ArrayList<Contingent> contingents = repository.allContingent();
            // Loops through contingent
            for (Contingent c : contingents) {
                if (c.getMemberId() == memberId) {
                    sb.append(c.getId()).append(", ")
                            .append(targetMember.getFullName()).append("(").append(c.getMemberId()).append(")").append(", ")
                            .append(c.getPrice()).append(", ")
                            .append(c.getDate()).append("\n");
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

    public String deleteContingent(int id, int memberId) {
        try {
            // If we can delete
            if (repository.deleteSpecificContingent(id, memberId)) {
                return "Du slettede kontingentet med følgende id: " + id + "\n";
            } else {
                return "Kontingentet med følgende id: " + id + " findes ikke på personen\n";
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
            sb.append("Id, ").append("name + memberId, ").append(" Pris,").append(" Betalings dato\n");
            // Loops the ArrayList
            for (Contingent c : repository.allContingent()) {
                for (Member m : repository.getAllMembers()) {
                    if (c.getMemberId() == m.getId()) {
                        sb.append(c.getId()).append(", ")
                                .append(m.getFullName()).append("(").append(c.getMemberId()).append(")").append(", ")
                                .append(c.getPrice()).append(", ")
                                .append(c.getDate())
                                .append("\n");
                    }
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

        for (double number : earnings) {
            sb.append(sum).append(" + ").append(number).append(" = ");
            sum += number;
            sb.append(sum).append(" kr.\n");
        }

        if (sum != 0) {
            sb.append("\n").append("I alt:\n");
            sb.append(sum).append("kr.\n");
        }


        return sb.isEmpty()
                ? "Der er ingen medlemmer. I tjener ikke noget endnu\n"
                : sb.toString();
    }

    public String checkArrears() {
        StringBuilder sb = new StringBuilder();

        sb.append("Folk som mangler at betale:\n");
        for (Member m : repository.getArrears()) {
            sb.append(m).append("\n");
        }

        return sb.isEmpty()
                ? "Alle har betalt kontingent\n"
                : sb.toString();
    }

}
