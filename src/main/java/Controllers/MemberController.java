package Controllers;

import Enums.SwimmingDisciplines;
import Models.CompetitiveSwimmer;
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
                ? "Ingen medlemmer fundet" // Translated: "No member found"
                : sb.toString();
    }

    public String displayMemberInformation() {
        if (memberRepository.getCurrentMember() == null) {
            return "Medlem ikke fundet :("; // Translated: "Member not found :("
        } else {
            return memberRepository.displayMemberInformation();
        }
    }

    public String chooseSpecificMemberById(int idToCheck) {
        if (!memberRepository.chooseSpecificMemberById(idToCheck)) {
            return "Medlem med ID: " + idToCheck + " blev ikke fundet"; // Translated: "Member with ID: X was not found"
        } else {
            return "Medlem med ID: " + idToCheck + " fundet :)"; // Translated: "Member with ID: X found :)"
        }
    }

    public String chooseSpecificMemberByName(String name) {
        if (!memberRepository.chooseSpecificMemberByName(name)) {
            return "Medlem med navn: " + name + " blev ikke fundet"; // Translated: "Member with name: X was not found"
        } else {
            return "Medlem med navn: " + name + " fundet :)"; // Translated: "Member with name: X found :)"
        }
    }

    public String createMember(String name, LocalDate birthday, boolean activity, boolean competitive) {
        if (!memberRepository.createMember(name, birthday, activity, competitive)) {
            return "Kunne ikke oprette medlem, prøv igen"; // Translated: "Failed to create member, please try again"
        }
        return "Medlem blev oprettet med succes"; // Translated: "Member was successfully created"
    }

    public String createCompetitiveMember(String name, LocalDate birthday, boolean activity, boolean competitive) {
        if (!memberRepository.createCompetitiveMember(name, birthday, activity, competitive)) {
            return "Kunne ikke oprette medlem, prøv igen"; // Translated: "Failed to create member, please try again"
        }
        return "Medlem blev oprettet med succes"; // Translated: "Member was successfully created"
    }

    // refactor
    public String updateInformation() {
        if (memberRepository.updateInformation()) {
            return "Medlemmets oplysninger blev opdateret med succes"; // Translated: "Member's information updated successfully"
        }
        return "Kunne ikke opdatere medlemmets oplysninger"; // Translated: "Failed to update member's information"
    }

    public String deleteMember() {
        if (!memberRepository.deleteMember()) {
            return "Kunne ikke slette medlem, prøv igen"; // Translated: "Failed to delete member, please try again"
        }
        return "Medlem slettet med succes"; // Translated: "Member deleted successfully"
    }

    public String changeFromMemberToCompetitiveSwimmer() { // refactor better
        try {
            if (getCurrentMember() instanceof CompetitiveSwimmer) {
                Member memberToDelete = getCurrentMember();
                memberRepository.createCompetitiveMemberFromEdit(
                        getCurrentMember().getFullName(),
                        getCurrentMember().getBirthday(),
                        getCurrentMember().getId(),
                        getCurrentMember().getActivity()
                );
                memberRepository.deleteMemberFromEdit(memberToDelete);
                return "Ændringen lykkedes :)"; // Translated: "Worked :)"
            } else {
                return "Var allerede en konkurrencesvømmer"; // Translated: "Was already a competitive swimmer"
            }
        } catch (Exception e) {
            return "Ændringen mislykkedes"; // Translated: "Did not work"
        }
    }

    public String changeFromCompetitiveSwimmerToMember() {
        try {
            if (!(getCurrentMember() instanceof CompetitiveSwimmer)) {
                Member memberToDelete = getCurrentMember();
                memberRepository.createMemberFromEdit(
                        getCurrentMember().getFullName(),
                        getCurrentMember().getBirthday(),
                        getCurrentMember().getId(),
                        getCurrentMember().getActivity()
                );
                memberRepository.deleteMemberFromEdit(memberToDelete);
                return "Medlemmet blev opdateret"; // Translated: "Member updated"
            }
            return "Forkert metode"; // Translated: "Wrong method"
        } catch (Exception e) {
            return "Medlemmet kunne ikke opdateres"; // Translated: "Member could not be updated"
        }
    }
}
