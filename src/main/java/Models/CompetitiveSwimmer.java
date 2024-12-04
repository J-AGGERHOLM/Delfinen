package Models;

import Enums.SwimmingDisciplines;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class CompetitiveSwimmer extends Member {

    ArrayList<SwimmingDisciplines> chosenDisciplines;

    public CompetitiveSwimmer(String fullName, LocalDate birthday, int id, boolean activity, boolean competitive, boolean paid) {
        super(fullName, birthday, id, activity, competitive, paid);
        chosenDisciplines = new ArrayList<>();
    }

    public ArrayList<SwimmingDisciplines> getChosenDisciplines() {
        return chosenDisciplines;
    }
    public SwimmingDisciplines getSpecificDiscipline(String discipline){
        for (SwimmingDisciplines s : chosenDisciplines){
            if(String.valueOf(s).equalsIgnoreCase(discipline)){
                return s;
            }

        }
        return null;
    }

    @Override
    public void setChosenDisciplines(ArrayList<SwimmingDisciplines> chosenDisciplines){
        this.chosenDisciplines = chosenDisciplines;
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
