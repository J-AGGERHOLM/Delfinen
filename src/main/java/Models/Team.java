package Models;


import java.util.ArrayList;

//used for both team and discipline.
public class Team {

    private Person trainer;
    private ArrayList<Person> members;
    private String name;
    private int id;

    public Team(int id, String name, Person trainer){
        this.id = id;
        this.name = name;
        this.trainer = trainer;
        this.members = new ArrayList<Person>();
    }

    public Team(int id,String name, Person trainer, ArrayList<Person> members){
        this.id = id;
        this.name = name;
        this.trainer = trainer;
        this.members = members;
    }

    //-----------------GETTERS-----------------------
    public Person getTrainer(){
        return trainer;
    }

    public ArrayList<Person> getMembers(){
        return members;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    //------------------------------------OTHER STUFF:----------------------------------

    public void addMember(CompetitiveSwimmer member){
        members.add(member);
    }

    @Override
    public String toString(){
        String result = "Team: " + name + ", Trainer: " + trainer.getFullName() + ", Members: " + members.size();
        return result;
    }

    public String getFullData(){
        String result = "Team: " + name + " \n";
        result += "Trainer " + trainer + "\n";
        result += "-----MEMBERS:-------\n";
        int counter = 0;
        for(Person m : members){
            counter ++;
            result += counter + ". " + m + "\n";
        }
        return result;
    }


}
