package Controllers;

import Models.Contingent;
import Models.Member;
import Repositories.ContingentRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContingentController {
    private final ContingentRepository repository;
    private final Member member;

    public ContingentController(int memberId) {
        // Find member by id method here
        // member = new Member("Jacob", LocalDate.of(1993, 10, 2), 1, true, true);
        // member = new Member("Simon", LocalDate.of(1963,10,2),2,true,true);
        // member = new Member("Jack", LocalDate.of(1983, 10, 2), 3, false, true);
        member = new Member("Benjamin", LocalDate.of(2010, 10, 2), 4, true, true);

        repository = new ContingentRepository();
    }

    public String checkMemberPrice() {
        // Converts to keep string principle
        return String.valueOf(repository.calculatePrice(member));
    }

    // Creates member
    public String createMemberPaid() {
        try {
            // If it's true
            if (repository.createMemberPaid(member)) {
                return "Record created.";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        // Something went wrong
        return "Something went wrong.";
    }

    // Gets a member contingents
    public String getMemberContingents() {
        // For print
        StringBuilder sb = new StringBuilder();
        // Store all
        ArrayList<Contingent> contingents = repository.getAllContingent();

        // Loops the ArrayList
        for (Contingent c : contingents) {
            if (c.getMemberId() == member.getId()) {
                sb.append(c);
            }
        }

        // Returns a message
        return sb.isEmpty()
                ? "No contingent exist."
                : sb.toString();
    }

    public String deleteContingent(int id) {
        try {
            // If we can delete
            if(repository.deleteSpecificContingent(id)){
                return "You deleted the record";
            }
        } catch (IOException e){
            // Something terrible went wrong
            return e.getMessage();
        }

        // Something went wrong
        return "The record didn't exist";
    }

    public String readAll(){
        // For print
        StringBuilder sb = new StringBuilder();

        // Loops through all
        for(Contingent c : repository.getAllContingent()){
            sb.append(c.toString());
        }

        // Return message
        return sb.isEmpty()
                ? "No record of any contingents"
                : sb.toString();
    }

    // ------------------------------- getter ----------------------------------
    public Member getMember() {
        return member;
    }
}
