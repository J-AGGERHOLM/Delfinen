package Enums;

public enum Fees {
    PASSIVE(500, 0),
    SENIOR(1600,0),
    JUNIOR(1000,0),
    PENSIONER(1600,25);

    private int price;
    private int discount;

    Fees(int price, int discount){
        this.price = price;
        this.discount = discount;
    }

    public double getPrice(){
        return discount == 0
                ? price
                // Calculate discount.
                : (double) price * (1 - (double) discount / 100);
    }
}
