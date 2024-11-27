package Repositories;

import Enums.Fees;
import FileHandler.ContingentHandler;
import Models.Contingent;
import Models.Member;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ContingentRepository {
    private ArrayList<Contingent> contingents;
    private final ContingentHandler ch;

    public ContingentRepository() {
        contingents = new ArrayList<>();
        ch = new ContingentHandler();
    }

    // Creates a contingent
    public boolean createMemberContingent(Member member) throws IOException {
        // Something went wrong
        if (member == null) {
            return false;
        }

        double price = calculatePrice(member);

        // Create contingent
        ch.create(getId(), member.getId(), price);

        return true;
    }

    // Calculate Price
    private double calculatePrice(Member member) {
        if (member == null) {
            return 0;
        }

        // get age
        int age = calculateYear(member.getBirthday());

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

    // Get the age of a member
    private int calculateYear(LocalDate memberDate) {
        LocalDate currentYear = LocalDate.now();

        // Create the age based on member birthdate and the date today
        return Period.between(memberDate, currentYear).getYears();
    }

    // Get all contingents
    public ArrayList<Contingent> getAllContingent() {
        return ch.read();
    }

    // Delete a contingent
    public boolean deleteSpecificContingent(int id) throws IOException {
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
        ch.delete(contingents);

        return true;
    }

    public int getId() {
        int id = getAllContingent().size();
        return ++id;
    }

}
