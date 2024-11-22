package Models;

import java.time.LocalDate;

public class Member extends Person {
    private boolean activity;
    private boolean competitive;


    public Member(String name, LocalDate birthday, int id, boolean activity, boolean competitive) {
        super(name, birthday, id);
        this.activity = activity;
        this.competitive = competitive;
    }

    //_____________________________________________Getter methods___________________________________________________________
    public boolean getActivity() {
        return activity;
    }

    public boolean getCompetitive() {
        return competitive;
    }

    //_______________________________________________Setter Methods_________________________________________________________
    public void setActivity(boolean newActivity) {
        activity = newActivity;
    }

    public void setCompetitive(boolean newCompetitive) {
        competitive = newCompetitive;
    }


}


