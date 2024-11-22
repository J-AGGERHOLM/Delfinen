package domain_model;

public class Konkurrence {

    String stævne;
    int placering;
    double tid;

    public Konkurrence(String stævne, int placering, double tid) {
        this.stævne = stævne;
        this.placering = placering;
        this.tid = tid;

    }


    @Override
    public String toString() {
        return "For " + stævne + " the competitor placed " + placering + "with a time of " + tid;
    }


}
