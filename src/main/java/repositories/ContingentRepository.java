package repositories;

import Enums.Fees;
import FileHandler.ContingentHandler;
import Models.Member;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class ContingentRepository {
    private double price;

    // Calculate Price
    public double checkPrice(Member member) {
        if(member == null){
            return 0;
        }

        // get year
        int year = calculateYear(member);

        if (!member.getActivity()) {
            return price = Fees.PASSIVE.getPrice();
        } else if (year < 18) {
            return price = Fees.JUNIOR.getPrice();
        } else if (year < 60) {
            return price = Fees.SENIOR.getPrice();
        } else {
            return price = Fees.PENSIONER.getPrice();
        }
    }
    // Get the Year
    private int calculateYear(Member member) {
        LocalDate currentYear = LocalDate.now();

        return Period.between(member.getBirthday(), currentYear).getYears();
    }

    public boolean createMemberPaid(Member member) throws IOException {
        if (member == null) {
            return false;
        }

        ContingentHandler ch = new ContingentHandler(member.getId(), price);
        ch.create();

        return true;
    }
}
