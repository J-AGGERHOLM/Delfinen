package Models;

import java.time.LocalDate;

public class Contingent {
    private int id;
    private final int memberId;
    private final double price;
    private final LocalDate date;

    public Contingent(int id, int memberId, double price, LocalDate date) {
        // id increases
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + id + " MemberId: " + memberId + " Price: " + price + " Date of payment: " + date + "\n";
    }
}
