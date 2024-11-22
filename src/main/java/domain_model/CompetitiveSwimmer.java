package domain_model;

import java.time.LocalDate;

public class CompetitiveSwimmer extends Person {


SwimmingDisciplines chosenDiscipline;

    public CompetitiveSwimmer(String fullName, LocalDate birthday, int id, SwimmingDisciplines chosenDiscipline) {
        super(fullName, birthday, id);
        this.chosenDiscipline = chosenDiscipline;
    }


    public void setChosendiscipline(SwimmingDisciplines chosendiscipline) {
        this.chosenDiscipline = chosendiscipline;
    }

    public SwimmingDisciplines getChosendiscipline() {
        return chosenDiscipline;
    }
}
