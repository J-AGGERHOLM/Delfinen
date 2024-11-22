package Models;


import java.util.ArrayList;

//used for both team and discipline.
public class Team {

    private Person trainer;
    private ArrayList<Person> members;
    private String name;

    public Team(String name, Person trainer){
        this.name = name;
        this.trainer = trainer;
        this.members = new ArrayList<Person>();
    }

    public Team(String name, Person trainer, ArrayList<Person> members){
        this.name = name;
        this.trainer = trainer;
        this.members = members;
    }


}
