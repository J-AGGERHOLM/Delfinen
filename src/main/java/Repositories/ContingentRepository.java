package Repositories;

import Enums.Fees;
import FileHandler.ContingentHandler;
import Models.Contingent;
import Models.Member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ContingentRepository {
    private final ContingentHandler ch;
    private final MemberRepository mr;

    public ContingentRepository() {
        ch = new ContingentHandler();
        mr = new MemberRepository();
    }

    // Creates a contingent
    public Contingent createMemberContingent(int memberId) throws IOException {
        if(!mr.chooseSpecificMemberById(memberId)){
            return null;
        }

        Member member = updateMember(memberId, true);

        double price = calculatePrice(member);

        // Create contingent
        return ch.create(getId(), member.getId(), price);
    }

    // Delete a contingent
    public boolean deleteSpecificContingent(int id) throws IOException {
        // To store the contingent
        ArrayList<Contingent> contingents = ch.read();
        Contingent contingent = null;

        for (Contingent c : contingents) {
            // If there is a match
            if (c.getId() == id) {
                contingent = c;
            }
        }
        // No match
        if (contingent == null) {
            return false;
        }

        // Remove from list and parse.
        contingents.remove(contingent);

        // If no contingent set false
        if(findContingentByMemberId(contingent.getMemberId(),contingents) == null){
            updateMember(contingent.getMemberId(), false);
        }

        return ch.delete(contingents);
    }

    private Contingent findContingentByMemberId(int memberId, ArrayList<Contingent> contingents){
        // Checks for more contingents
        for(Contingent c : contingents){
            if(c.getMemberId() == memberId){
                return c;
            }
        }
        return null;
    }

    private Member updateMember(int memberId, boolean paid){
        mr.getCurrentMember().setPaid(paid);
        Member member = mr.getCurrentMember();
        mr.getMemberArrayList().set(memberId, member);
        mr.updateInformation();
        return member;
    }

    // Calculate Price
    private double calculatePrice(Member member) {
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

    // Makes id.
    private int getId() throws IOException {
        int id = 0;
        for(Contingent c : ch.read()){
            if(c.getId() > id){
                id = c.getId();
            }
        }

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
                // Loops through contingents
                Contingent found = findContingentByMemberId(m.getId(), contingentCopy);

                // If paid but no contingent, make contingent and add.
                if(found == null){
                    contingents.add(createMemberContingent(m.getId()));
                } else {
                    contingents.add(found);
                }
            }
        }
        // Compare on id
        Comparator<Contingent> comparator = Comparator.comparing(Contingent::getId);
        contingents.sort(comparator);

        return contingents;
    }

    // ------------------------ get -------------------------------

    public ArrayList<Member> getAllMembers(){
        return mr.getMemberArrayList();
    }
}
