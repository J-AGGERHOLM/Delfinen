package repositories;

import Enums.Fees;
import FileHandler.ContingentHandler;
import Models.Contingent;
import Models.Member;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ContingentRepository {
    private double price;

    // Calculate Price
    public double checkPrice(Member member) {
        if(member == null){
            return 0;
        }

        // get year
        int year = calculateYear(member.getBirthday());

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
    // Get the year of a member
    private int calculateYear(LocalDate memberDate) {
        LocalDate currentYear = LocalDate.now();

        return Period.between(memberDate, currentYear).getYears();
    }

    public boolean createMemberPaid(Member member) throws IOException {
        if (member == null) {
            return false;
        }

        ContingentHandler ch = new ContingentHandler();
        ch.create(member.getId(), price);

        return true;
    }

    public ArrayList<Contingent> getContingents(){
        ContingentHandler ch = new ContingentHandler();
        ArrayList<Contingent> c = new ArrayList<>(ch.read());

        return c;
    }
}
