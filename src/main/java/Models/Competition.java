package Models;

public class Competition {
    String event;
    int placement;
    double time;
    private int id;

    public Competition(int id, String event, int placement, double time) {
        this.event = event;
        this.placement = placement;
        this.time = time;
        this.id = id;

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // ------------------------ END: get og setter ------------------------


    @Override
    public String toString() {
        return id + "," + event + "," + placement + "," + time;
    }


}
