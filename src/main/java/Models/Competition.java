package Models;

public class Competition {
    String event;
    int placement;
    double time;

    public Competition(String event, int placement, double time) {
        this.event = event;
        this.placement = placement;
        this.time = time;

    }

    // ------------------------ START: get og setter ------------------------


    public String getEvent() {
        return event;
    }

    public int getPlacement() {
        return placement;
    }

    public double getTime() {
        return time;
    }


    public void setEvent(String event) {
        this.event = event;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public void setTime(double time) {
        this.time = time;
    }

    // ------------------------ SLUT: get og setter ------------------------


    @Override
    public String toString() {
        return "For " + event + " the competitor placed " + placement + "with a time of " + time;
    }


}
