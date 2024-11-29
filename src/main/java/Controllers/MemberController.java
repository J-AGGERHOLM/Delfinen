package Controllers;

import Enums.SwimmingDisciplines;
import Models.Member;
import Repositories.MemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class MemberController {
    private MemberRepository memberRepository;


    public MemberController() {
        memberRepository = new MemberRepository();
    }

//_______________________________getter methods___________________________________

    public ArrayList<Member> getMemberArrayList() {
        return memberRepository.getMemberArrayList();
    }

    public Member getCurrentMember() {
        return memberRepository.getCurrentMember();
    }

    public int getNewId() {
        return memberRepository.getNewId();
    }

    public String displayMembers() {
        StringBuilder sb = new StringBuilder();
        for (Member member : memberRepository.getMemberArrayList()) {
            sb.append(member).append("\n");
        }
        return sb.isEmpty()
                ? "No member found"
                : sb.toString();
    }

    public String displayMemberInformation() {
        if (memberRepository.getCurrentMember() == null) {
            return "Member not found :(";
        } else {
            return memberRepository.displayMemberInformation();
        }
    }

    public String chooseSpecificMemberById(int idToCheck) {
        if (!memberRepository.chooseSpecificMemberById(idToCheck)) {
            return "Member with ID: " + idToCheck + "Was not found";
        } else {
            return "Member with ID: " + idToCheck + "found :)";
        }
    }

    public String chooseSpecificMemberByName(String name) {
        if (!memberRepository.chooseSpecificMemberByName(name)) {
            return "Member with name: " + name + "Was not found";
        } else {
            return "Member with name: " + name + "found :)";
        }
    }



    public String createMember(String name, LocalDate birthday, boolean activity, boolean competitive) {
        if (!memberRepository.createMember(name, birthday, activity, competitive)) {
            return "Failed to create member, please try again";
        }
        return "Member was successfully created";
    }

    public String createCompetitiveMember(String name, LocalDate birthday, boolean activity, boolean competitive, int swimmingDisciplineIndex) {
        if(!memberRepository.createCompetitiveMember(name, birthday, activity, competitive, swimmingDisciplineIndex)){
            return "Failed to create member, please try again";
        }
        return "Member was successfully created";
    }

    // refactor
    public String updateInformation(){
        if (memberRepository.updateInformation()){
            return "Members information updated successfully";
        }
        return "Failed to update members information";
    }

    public String deleteMember() {
        if (!memberRepository.deleteMember()) {
            return "Failed to create member, please try again";
        }
        return "Member deleted successfully";
    }


}
