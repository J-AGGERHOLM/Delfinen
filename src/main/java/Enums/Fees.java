package Enums;

public enum Fees {
    // The different fees. with price and discount
    PASSIVE(500, 0),
    SENIOR(1600,0),
    JUNIOR(1000,0),
    PENSIONER(1600,25);

    // For price and discount
    private final int price;
    private final int discount;

    // Sets price and discount
    Fees(int price, int discount){
        this.price = price;
        this.discount = discount;
    }

    // Calculate price
    public double getPrice(){
        return discount == 0
                ? price
                // Calculate discount.
                : (double) price * (1 - (double) discount / 100);
    }
}
