package FileHandler;


import Models.Contingent;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Super {
    private final String filePath;

    // I don't know if the constructor have to be empty.
    // We will see how the individual class' are built .
    // Then decide when we merge.
    public Super(String filePath){
        this.filePath = filePath;
    }

    // The same goes here.
    // I don't know if it has to be void.
    // We will decide based on the individual class'.
    // We will do on merge
    public abstract void create(int id, double price) throws IOException;
    public abstract ArrayList<Contingent> read();
    public abstract void update();
    public abstract void delete();

    public String getFilePath() {
        return filePath;
    }
}
