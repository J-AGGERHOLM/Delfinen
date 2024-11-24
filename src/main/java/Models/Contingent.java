package Models;

import java.time.LocalDate;

public class Contingent {
    private int memberId;
    private boolean hasPaid;
    private double price;
    private LocalDate date;

    public Contingent(int memberId, boolean hasPaid, double price, LocalDate date){
        this.memberId = memberId;
        this.hasPaid = hasPaid;
        this.price = price;
        this.date = date;
    }

}
