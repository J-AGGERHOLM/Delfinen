package repositories;

import Enums.SwimmingDisciplines;
import Models.Person;
import java.time.LocalDate;

public class CompetitiveSwimmerRepository extends Person {


    SwimmingDisciplines chosenDiscipline;

    public CompetitiveSwimmerRepository(String fullName, LocalDate birthday, int id, SwimmingDisciplines chosenDiscipline) {
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
