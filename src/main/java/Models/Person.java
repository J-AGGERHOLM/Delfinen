package Models;


import java.time.LocalDate;
import java.time.Period;

public class Person {

    private String fullName;
    private LocalDate birthday;
    private int id;

    // Constructor
    public Person(String fullName, LocalDate birthday, int id) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.id = id;
    }

    //_______________________Getter Methods____________________________________
    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        LocalDate currentYear = LocalDate.now();
        return Period.between(birthday, currentYear).getYears();
    }


    //_______________________________Setter Methods_____________________________________________

    public void setName(String newFullName) {
        fullName = newFullName;
    }

    public void setBirthday(LocalDate newBirthday) {
        birthday = newBirthday;
    }

    public void setId(int newId) {
        id = newId;
    }

    //____________________________________________ToString_______________________________________________________________

    public String toString() {
        return "ID: " + id + " Name: " + fullName + " Birthday: " + birthday + " ";
    }


}
