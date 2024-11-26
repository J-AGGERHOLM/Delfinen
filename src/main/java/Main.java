import Enums.SwimmingDisciplines;
import Models.CompetitiveSwimmer;
import Models.SwimmingClub;
import Models.Team;
import Models.Trainer;
import UI.UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main{
   public static void main(String[] args) {
       UserInterface ui = new UserInterface();
       ui.mainLoop();
   }

}