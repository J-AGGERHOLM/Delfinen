package Models;

public class Kontingent {
    private boolean arrears;
    private Enum Fees;
    private boolean paid;

    public Kontingent(boolean arrears, Enum Fees, boolean paid){
        this.arrears = arrears;
        this.Fees = Fees;
        this.paid = paid;
    }

}
