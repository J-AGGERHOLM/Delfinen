package Controllers;

import Models.Member;
import repositories.ContingentRepository;

import java.time.LocalDate;

public class Controller {
    private ContingentRepository contingent;

    public Controller(){
        contingent = new ContingentRepository();
    }

    // possible parse member
    public String addContingent(){
        // I need a find person/Member method
        Member memberActive = new Member("Mathias",
                LocalDate.of(1994,10,07),
                1,
                true,
                true);
        //Person memberPassiv = new Member("Jack", LocalDate.now(),2,false,false);

        return "";
    }


}
