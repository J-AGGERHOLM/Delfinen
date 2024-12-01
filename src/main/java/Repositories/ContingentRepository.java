package Repositories;

import Enums.Fees;
import FileHandler.ContingentHandler;
import Models.Contingent;
import Models.Member;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ContingentRepository {
    private final ContingentHandler ch;
    private final MemberRepository mr;

    public ContingentRepository() {
        ch = new ContingentHandler();
        mr = new MemberRepository();
    }

    // Creates a contingent
    public boolean createMemberContingent(int memberId) throws IOException {
        Member member = null;
        if(mr.chooseSpecificMemberById(memberId)){
            member = mr.getCurrentMember();
        }

        if(member == null){
            return false;
        }

        double price = calculatePrice(member);

        // Create contingent
        ch.create(getId(), member.getId(), price);

        return true;
    }

    // Calculate Price
    private double calculatePrice(Member member) {
        if (member == null) {
            return 0;
        }

        // get age
        int age = member.getAge();

        // Price is based on age or activity
        if (!member.getActivity()) {
            return Fees.PASSIVE.getPrice();
        } else if (age < 18) {
            return Fees.JUNIOR.getPrice();
        } else if (age < 60) {
            return Fees.SENIOR.getPrice();
        } else {
            return Fees.PENSIONER.getPrice();
        }
    }

    // Delete a contingent
    public boolean deleteSpecificContingent(int id) throws IOException {
        ArrayList<Contingent> contingents = ch.read();

        // To store the contingent
        Contingent temp = null;
        for (Contingent c : contingents) {
            // If there is a match
            if (c.getId() == id) {
                temp = c;
            }
        }

        // No match
        if (temp == null) {
            return false;
        }

        // Remove from list and parse.
        contingents.remove(temp);
        ch.delete(contingents);

        return true;
    }

    public int getId() throws IOException {
        int id = ch.read().size();
        return ++id;
    }

    public double getExpectedEarnings(){
        double sum = 0;

        for(Member member: mr.getMemberArrayList()){
            sum += calculatePrice(member);
        }

        return sum;
    }

    // ---------------------- getter -----------------------------
    public ArrayList<Contingent> getAllContingent() throws FileNotFoundException {
        return ch.read();
    }

}
