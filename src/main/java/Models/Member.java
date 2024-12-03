package Models;

import java.time.LocalDate;

public class Member extends Person {
    private boolean activity;
    private boolean competitive;
    private boolean paid;


    public Member(String fullName, LocalDate birthday, int id, boolean activity, boolean competitive, boolean paid) {
        super(fullName, birthday, id);
        this.activity = activity;
        this.competitive = competitive;
        this.paid = paid;
    }

    //_____________________________________________Getter methods___________________________________________________________
    public boolean getActivity() {
        return activity;
    }

    public boolean getCompetitive() {
        return competitive;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    //_______________________________________________Setter Methods_____________________________________________________
    public void setActivity(boolean newActivity) {
        activity = newActivity;
    }

    public void setCompetitive(boolean newCompetitive) {
        competitive = newCompetitive;
    }


    //______________________to String___________________________________________________________________________________

    @Override
    public String toString() {
        return "id: " + super.getId() +
                " Full name: " + super.getFullName() +
                " Birthday: " + super.getBirthday() +
                " Activity status: " + (activity ? "Active" : "Passive") +
                " Competitive status: " + (competitive ? "Competitive" : "Non-competitive"); // need better names
    }

    public String toStringFile() {
        return super.getFullName() + "," +
                super.getBirthday() + "," + super.getId() + "," +
                (activity ? "active" : "passive") + "," +
                (competitive ? "competitive" : "non-competitive") + "," +
                isPaid();
    }


}


