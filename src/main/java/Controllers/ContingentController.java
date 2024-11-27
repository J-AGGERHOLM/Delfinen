package Controllers;

import Models.Contingent;
import Models.Member;
import Repositories.ContingentRepository;
import Repositories.MemberRepository;

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
            MemberRepository mr = new MemberRepository();
            Member member = mr.chooseSpecificMemberById(memberId);

            if(member == null){
                return "Member not found";
            }

            // If it's true
            if (repository.createMemberContingent(member)) {
                return "Record created.";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        // Something went wrong
        return "Something went wrong.";
    }

    // Gets a member contingents
    public String getMemberContingents(int memberId) {
        // For print
        StringBuilder sb = new StringBuilder();
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


}
