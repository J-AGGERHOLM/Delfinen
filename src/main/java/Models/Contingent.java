package Models;

import java.time.LocalDate;

public class Contingent {
    private int memberId;
    private double price;
    private LocalDate date;

    public Contingent(int memberId, double price, LocalDate date){
        this.memberId = memberId;
        this.price = price;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString(){
    return "MemberId: " + memberId + " Price: " + price + " Date: " + date + "\n";
    }
}
