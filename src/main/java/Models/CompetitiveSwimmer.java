package Models;

import Enums.SwimmingDisciplines;

import java.time.LocalDate;
import java.util.Random;

public class CompetitiveSwimmer extends Member {


    SwimmingDisciplines chosenDiscipline;

    public CompetitiveSwimmer(String fullName, LocalDate birthday, int id, boolean activity, boolean competitive, SwimmingDisciplines chosenDiscipline, boolean paid) {
        super(fullName, birthday, id, activity, competitive, paid);
        this.chosenDiscipline = chosenDiscipline;
    }


    public void setChosendiscipline(SwimmingDisciplines chosendiscipline) {
        this.chosenDiscipline = chosendiscipline;
    }

    public SwimmingDisciplines getChosendiscipline() {
        return chosenDiscipline;
    }


    @Override
    public String toStringFile() {
        return super.toStringFile() + "," + (chosenDiscipline != null ? chosenDiscipline : "");
    }


}
