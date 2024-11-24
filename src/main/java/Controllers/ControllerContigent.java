package Controllers;

import Models.Member;
import repositories.ContingentRepository;

import java.io.IOException;
import java.time.LocalDate;

public class ControllerContigent {
    private ContingentRepository contingent;
    private Member member;

    public ControllerContigent(int memberId) {
        // Find member by id here
        //member = new Member("Jacob", LocalDate.of(1993,10,2),1,true,true);
        //member = new Member("Simon", LocalDate.of(1963,10,2),2,true,true);
        member = new Member("Jack", LocalDate.of(1983, 10, 2), 3, false, true);
        //member = new Member("Benjamin", LocalDate.of(2010, 10, 2), 4, true, true);

        if (member != null) {
            contingent = new ContingentRepository();
        }
    }

    // possible parse member
    public String checkMemberPrice() {
        return String.valueOf(contingent.checkPrice(member));
    }

    public String createMemberPaid() {
        try {
            if (contingent.createMemberPaid(member)) {
                return "Record created";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return "Something went wrong";
    }

    public Member getMember() {
        return member;
    }
}
