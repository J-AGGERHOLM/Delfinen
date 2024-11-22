package repositories;

import Models.Member;


import java.util.ArrayList;

public class MemberRepository {
    private ArrayList<Member> memberArrayList;
    private Member currentMember;


    public MemberRepository() {
        memberArrayList = new ArrayList<>();
    }


    //___________________________________________________Getter Method__________________________________________________

    public ArrayList<Member> getMemberArrayList() {
        return memberArrayList;
    }

    public Member getCurrentMember() {
        return currentMember;
    }

    //__________________________________________________________________________________________________________________


    // returns a list of all members
    public String displayMembers() {
        String result = "";
        for (Member m : memberArrayList) {
            result += m.getFullName() + "\n";
        }
        return result;
    }


    public String displayMemberInformation() {
        return currentMember.toString();
    }

    public Member chooseSpecificMember(int idToCheck) {
        for (Member m : memberArrayList) {
            if (idToCheck == m.getId()) {
                currentMember = m;
            }
        }
        return currentMember;
    }


}
