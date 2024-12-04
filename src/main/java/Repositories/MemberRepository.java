package Repositories;

import Enums.SwimmingDisciplines;
import FileHandler.MemberFileHandler;
import Models.CompetitiveSwimmer;
import Models.Member;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class MemberRepository {
    private ArrayList<Member> memberArrayList;
    private Member currentMember;
    private MemberFileHandler memberFileHandler;


    public MemberRepository() {
        memberFileHandler = new MemberFileHandler();
        memberFileHandler.setMemberRepository(this);
        memberArrayList = new ArrayList<>();
        memberFileHandler.read();
    }


    //___________________________________________________Getter Method__________________________________________________

    public ArrayList<Member> getMemberArrayList() {
        return memberArrayList;
    }

    public Member getCurrentMember() {
        return currentMember;
    }

    //__________________________________________________________________________________________________________________


    public int getNewId(){
        return memberArrayList.size() + 1;
    }

    // returns a list of all members
    public String displayMembers() {
        String result = "";
        for (Member m : memberArrayList) {
            result += m.toString() + "\n";        }
        return result;
    }


    public String displayMemberInformation() {
        return currentMember.toString();
    }


    // Find a specific member with their id from the list for editing, deleting, payments, etc
    public boolean chooseSpecificMemberById(int idToCheck) {
        for (Member m : memberArrayList) {
            if (idToCheck == m.getId()) {
                currentMember = m;
                return true;
            }
        }
        currentMember = null;
        return false;
    }
    // Find a specific member with their Name from the list for editing, deleting, payments, etc
    public boolean chooseSpecificMemberByName(String name) {
        for (Member m : memberArrayList) {
            if (m.getFullName().toLowerCase().contains(name)) {
                currentMember = m;
                return true;
            }
        }
        currentMember = null;
        return false;
    }


    public boolean createMember(String name, LocalDate birthday, boolean activity, boolean competitive) {
        try {
            // Create a new Member object
            Member member = new Member(name, birthday, getNewId(), activity, competitive, new Random().nextBoolean());
            // Assign the current member
            currentMember = member;
            // Add the member to the ArrayList
            memberArrayList.add(currentMember);
            // Save the member to file
            memberFileHandler.create();
            return true;
        } catch (IOException e) {
            System.err.println("Error occurred while creating a member: " + e.getMessage());

            return false;
        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }


    public boolean createCompetitiveMember(String name, LocalDate birthday, boolean activity, boolean competitive, ArrayList<SwimmingDisciplines> chosenDisciplines) {
        try {
            // Create a new Member object
            Member member = new CompetitiveSwimmer(name, birthday, getNewId(), activity, competitive, chosenDisciplines, new Random().nextBoolean());
            // Assign the current member
            currentMember = member;
            // Add the member to the ArrayList
            memberArrayList.add(currentMember);
            // Save the member to file
            memberFileHandler.create();
            return true;
        } catch (IOException e) {
            System.err.println("Error occurred while creating a member: " + e.getMessage());

            return false;
        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }

    public boolean updateInformation() {
        try {
            memberFileHandler.update();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteMember(){
        try {
            memberArrayList.remove(currentMember);
            memberFileHandler.update();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


