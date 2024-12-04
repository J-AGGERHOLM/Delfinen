package Repositories;

import Enums.Fees;
import FileHandler.ContingentHandler;
import Models.Contingent;
import Models.Member;

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
    public Contingent createMemberContingent(int memberId) throws IOException {
        Member member = null;
        if(mr.chooseSpecificMemberById(memberId)){
            member = mr.getCurrentMember();
        }

        mr.getMemberArrayList().remove(member);

        if(member == null){
            return null;
        }

        double price = calculatePrice(member);

        // Create contingent
        Contingent contingent = ch.create(getId(), member.getId(), price);

        if(contingent == null){
            return null;
        }

        member.setPaid(true);
        mr.getMemberArrayList().add(member);
        mr.updateInformation();

        return contingent;
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

        return ch.delete(contingents);
    }

    // Makes id.
    public int getId() throws IOException {
        int id = ch.read().size();
        return ++id;
    }

    // get expected earnings
    public ArrayList<Double> getExpectedEarnings(){
        ArrayList<Double> sum = new ArrayList<>();

        for(Member member: mr.getMemberArrayList()){
            // calculates sum
            sum.add(calculatePrice(member));
        }

        return sum;
    }

    // Finds all member with paid = false
    public ArrayList<Member> getArrears(){
        ArrayList<Member> arrears = new ArrayList<>();
        for(Member m : mr.getMemberArrayList()){
            // If not paid add.
            if(!m.isPaid()){
                arrears.add(m);
            }
        }
        return arrears;
    }

    // Every member who has paid = true but no contingent added.
    // Adds that member to contingent.
    // returns an Array with all contingent
    public ArrayList<Contingent> allContingent() throws IOException {
        ArrayList<Contingent> contingents = new ArrayList<>();
        // Takes a copy of the original list. avoids reference problems.
        ArrayList<Contingent> contingentCopy = new ArrayList<>(ch.read());
        ArrayList<Member> membersCopy = new ArrayList<>(mr.getMemberArrayList());

        for(Member m : membersCopy){
            // If you have paid
            if(m.isPaid()){
                boolean found = false;
                // Loops through contingents
                for (Contingent c : contingentCopy) {
                    if(m.getId() == c.getMemberId()){
                        // Adds
                        found = true;
                        contingents.add(c);
                        break;
                    }
                }
                // If paid but no contingent, make contingent and add.
                if(!found){
                    contingents.add(createMemberContingent(m.getId()));
                }
            }
        }
        return contingents;
    }
}
