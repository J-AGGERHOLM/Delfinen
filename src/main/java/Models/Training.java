package Models;

public class Training implements Comparable<Training> {
    private String discipline;
    private int swimmerID;
    private String time;

    public Training(String discipline, int swimmerId, String time) {
        this.discipline = discipline;
        this.swimmerID = swimmerId;
        this.time = time;
    }

    public int getTimeAsInteger() {
        time = time.replaceAll(":", "");
        return Integer.parseInt(time);
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getTime() {
        return time;
    }

    public int getSwimmerID() {
        return swimmerID;
    }


    public String toString() {
        return discipline + "\t\t   " +
                swimmerID + "\t\t   " +
                time;
    }

    @Override
    public int compareTo(Training o) {
        return getTimeAsInteger() - o.getTimeAsInteger();
    }

    public String toStringFile() {
        return discipline + ","
                + swimmerID + ","
                + time;
    }
}

