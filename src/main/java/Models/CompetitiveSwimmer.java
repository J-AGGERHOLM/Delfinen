package Models;

import Enums.SwimmingDisciplines;

import java.time.LocalDate;

public class CompetitiveSwimmer extends Member {


    SwimmingDisciplines chosenDiscipline;

    public CompetitiveSwimmer(String fullName, LocalDate birthday, int id, boolean activity, boolean competitive, SwimmingDisciplines chosenDiscipline) {
        super(fullName, birthday, id, activity, competitive);
        this.chosenDiscipline = chosenDiscipline;
    }


    public void setChosendiscipline(SwimmingDisciplines chosendiscipline) {
        this.chosenDiscipline = chosendiscipline;
    }

    public void setChosenDisciplineByInt(int chosenDisciplineByInt) {
         switch (chosenDisciplineByInt) {
            case 1:
                this.chosenDiscipline = SwimmingDisciplines.BACKCRAWL;
            case 2:
                this.chosenDiscipline = SwimmingDisciplines.BREASTSTROKE;
            case 3:
                this.chosenDiscipline = SwimmingDisciplines.BUTTERFLY;
            case 4:
                this.chosenDiscipline = SwimmingDisciplines.CRAWL;
            default: this.chosenDiscipline = SwimmingDisciplines.BACKCRAWL;
        };
    }

    public SwimmingDisciplines getChosendiscipline() {
        return chosenDiscipline;
    }


    @Override
    public String toStringFile() {
        return super.toStringFile() + "," + (chosenDiscipline != null ? chosenDiscipline.ordinal() : "");
    }


}
