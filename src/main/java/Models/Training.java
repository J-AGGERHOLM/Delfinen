package Models;

import java.time.LocalDate;
import java.time.Period;

public class Training implements Comparable<Training> {
    private String discipline;
    private CompetitiveSwimmer swimmer;
    private String time;

    public Training(String discipline, CompetitiveSwimmer swimmer, String time) {
        this.discipline = discipline;
        this.swimmer = swimmer;
        this.time = time;
    }

    public int swimmerAge() {
        return Period.between(swimmer.getBirthday(), LocalDate.now()).getYears();
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
        return discipline + "\t\t   " +
                swimmer.getId() + "(" +
                swimmer.getFullName() + ")\t\t" +
                time;
    }

    @Override
    public int compareTo(Training o) {
        return getTimeAsInteger() - o.getTimeAsInteger();
    }

    public String toStringFile() {
        return discipline + ","
                + swimmer.getId() + ","
                + time;
    }
}

