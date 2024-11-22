package domain_model;

public class Competition {

    String event;
    int placement;
    double time;

    public Competition(String event, int placement, double time) {
        this.event = event;
        this.placement = placement;
        this.time = time;

    }


    @Override
    public String toString() {
        return "For " + event + " the competitor placed " + placement + "with a time of " + time;
    }


}
