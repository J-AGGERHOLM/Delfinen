package Models;

import Enums.SwimmingDisciplines;

import java.time.LocalDate;

public class competitiveswimmer extends Person {


    SwimmingDisciplines chosenDiscipline;

    public competitiveswimmer(String fullName, LocalDate birthday, int id, SwimmingDisciplines chosenDiscipline) {
        super(fullName, birthday, id);
        this.chosenDiscipline = chosenDiscipline;
    }


    public void setChosendiscipline(SwimmingDisciplines chosendiscipline) {
        this.chosenDiscipline = chosendiscipline;
    }

    public SwimmingDisciplines getChosendiscipline() {
        return chosenDiscipline;
    }


    @Override
    public String toString() {
        return "Name :  " + getFullName() + "       " + "Swimming discipline :  " + getChosendiscipline();
    }

}
