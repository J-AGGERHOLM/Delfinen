package Repositories;

import FileHandler.MemberFileHandler;
import Models.Member;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
        return memberArrayList.size();
    }

    // returns a list of all members
    public String displayMembers() {
        String result = "";
        for (Member m : memberArrayList) {
            result += m.getFullName() + "\n";        }
        return result;
    }


    public String displayMemberInformation() {
        return currentMember.toString();
    }


    // Find a specific member with their id from the list for editing, deleting, payments, etc
    public Member chooseSpecificMemberById(int idToCheck) {
        for (Member m : memberArrayList) {
            if (idToCheck == m.getId()) {
                currentMember = m;
                return currentMember;
            }
        }
        return null;
    }
    // Find a specific member with their Name from the list for editing, deleting, payments, etc
    public Member chooseSpecificMemberByName(String name) {
        for (Member m : memberArrayList) {
            if (m.getFullName().contains(name)) {
                currentMember = m;
            }
        }
        return currentMember;
    }

    public void createMember(String name, LocalDate birthday, int id, boolean activity, boolean competitive) throws IOException {
        Member member = new Member(name, birthday, id, activity, competitive);
        currentMember = member;
        memberArrayList.add(currentMember);
        memberFileHandler.create();

    }


}


