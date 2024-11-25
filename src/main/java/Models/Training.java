package Models;

public class Training implements Comparable<Training> {
    private Trainer trainer;
    private String discipline;
    private Person swimmer;
    private String time;

    public Training(Trainer trainer, String discipline, Person swimmer, String time) {
        this.trainer = trainer;
        this.discipline = discipline;
        this.swimmer = swimmer;
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


    public String toString() {
        return trainer.getFullName() + "\t\t  " +
                discipline + "\t\t   " +
                swimmer.getId() + "(" +
                swimmer.getFullName() + ")\t\t" +
                time;
    }

    @Override
    public int compareTo(Training o) {
        return getTimeAsInteger() - o.getTimeAsInteger();
    }
}

