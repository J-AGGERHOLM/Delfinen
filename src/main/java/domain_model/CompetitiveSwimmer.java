package domain_model;

import java.time.LocalDate;

public class CompetitiveSwimmer extends Person {


SwimmingDisciplines chosendiscipline;

    public CompetitiveSwimmer(String fullName, LocalDate birthday, int id) {
        super(fullName, birthday, id);
    }


    public void setChosendiscipline(SwimmingDisciplines chosendiscipline) {
        this.chosendiscipline = chosendiscipline;
    }

    public SwimmingDisciplines getChosendiscipline() {
        return chosendiscipline;
    }
}
