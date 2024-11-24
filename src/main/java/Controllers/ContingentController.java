package Controllers;

import Models.Contingent;
import Models.Member;
import repositories.ContingentRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContingentController {
    private ContingentRepository repository;
    private Member member;

    public ContingentController(int memberId) {
        // Find member by id method here
        //member = new Member("Jacob", LocalDate.of(1993, 10, 2), 1, true, true);
        //member = new Member("Simon", LocalDate.of(1963,10,2),2,true,true);
        //member = new Member("Jack", LocalDate.of(1983, 10, 2), 3, false, true);
        member = new Member("Benjamin", LocalDate.of(2010, 10, 2), 4, true, true);

        repository = new ContingentRepository();
    }

    // possible parse member
    public String checkMemberPrice() {
        return String.valueOf(repository.checkPrice(member));
    }

    public String createMemberPaid() {
        try {
            if (repository.createMemberPaid(member)) {
                return "Record created.";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return "Something went wrong.";
    }

    public String getSpecifikContingents() {
        ArrayList<Contingent> contingents = repository.getSpecificContingents();
        StringBuilder sb = new StringBuilder();

        for (Contingent c : contingents) {
            if (c.getMemberId() == member.getId()) {
                sb.append(c);
            }
        }

        return sb.isEmpty()
                ? "No contingent exist."
                : sb.toString();
    }

    public String deleteContingent(int id) {
        try {
            if(repository.deleteSpecificContingent(id)){
                return "You deleted the record";
            }
        } catch (IOException e){
            return e.getMessage();
        }

        return "The record didn't exist";
    }

    public String readAll(){
        StringBuilder sb = new StringBuilder();

        for(Contingent c : repository.readAll()){
            sb.append(c.toString());
        }

        return sb.isEmpty()
                ? "No record of any contingents"
                : sb.toString();
    }

    // ------------------------------- getter ----------------------------------
    public Member getMember() {
        return member;
    }
}
