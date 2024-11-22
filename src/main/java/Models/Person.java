package Models;


import java.time.LocalDate;

public class Person {

    private String fullName;
    private LocalDate birthday;
    private int id;

    // Constructor
    public Person(String fullName, LocalDate birthday, int id){
        this.fullName = fullName;
        this.birthday = birthday;
        this.id = id;
    }

    //_______________________Getter Methods____________________________________
    public String getFullName(){
        return fullName;
    }
    public LocalDate getBirthday(){
        return birthday;
    }
    public int getId(){
        return id;
    }


    //_______________________________Setter Methods_____________________________________________

   public void setName(String newFullName){
        fullName = newFullName;
   }
   public void setBirthday(LocalDate newBirthday){
        birthday = newBirthday;
   }

    public void setId(int newId) {
        id = newId;
    }
   //____________________________________________ToString_______________________________________________________________

   public String toString(){
        return "";
   }

}
