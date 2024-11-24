package FileHandler;


import java.io.IOException;

public abstract class SuperHandler {
    private final String filePath;

    // I don't know if the constructor have to be empty.
    // We will see how the individual class' are built .
    // Then decide when we merge.
    public SuperHandler(String filePath){
        this.filePath = filePath;
    }

    // The same goes here.
    // I don't know if it has to be void.
    // We will decide based on the individual class'.
    // We will do on merge
    public abstract void create() throws IOException;
    public abstract void read();
    public abstract void update();
    public abstract void delete();

    public String getFilePath() {
        return filePath;
    }
}
