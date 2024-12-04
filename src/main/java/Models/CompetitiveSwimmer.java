package Models;

import Enums.SwimmingDisciplines;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class CompetitiveSwimmer extends Member {

    ArrayList<SwimmingDisciplines> chosenDisciplines;

    public CompetitiveSwimmer(String fullName, LocalDate birthday, int id, boolean activity, boolean competitive, ArrayList<SwimmingDisciplines> chosenDisciplines, boolean paid) {
        super(fullName, birthday, id, activity, competitive, paid);
        this.chosenDisciplines = chosenDisciplines;
    }


    public void setChosendiscipline(ArrayList<SwimmingDisciplines> chosenDisciplines) {
        this.chosenDisciplines = chosenDisciplines;
    }

    public ArrayList<SwimmingDisciplines> getChosendisciplines() {
        return chosenDisciplines;
    }


    @Override
    public String toStringFile() {
        String result = super.toStringFile() + ",";
        for (SwimmingDisciplines s : chosenDisciplines){
            result += s.ordinal() + ",";
        }
        return result;
    }


}
