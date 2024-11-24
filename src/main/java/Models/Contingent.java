package Models;

import java.time.LocalDate;

public class Contingent {
    // Gemmes under run time.
    private static int nextId = 0;
    private int id;
    private int memberId;
    private double price;
    private LocalDate date;

    public Contingent(int memberId, double price, LocalDate date) {
        // id increases
        id = nextId++;
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
        return "Id: " + id + " MemberId: " + memberId + " Price: " + price + " Date: " + date + "\n";
    }
}
