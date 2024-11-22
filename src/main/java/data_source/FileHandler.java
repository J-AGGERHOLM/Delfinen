package data_source;


public abstract class FileHandler {

    // I don't know if the constructor have to be empty.
    // We will see how the individual class' are built .
    // Then decide when we merge.
    public FileHandler(){

    }

    // The same goes here.
    // I don't know if it has to be void.
    // We will decide based on the individual class'.
    // We will do on merge
    public abstract void create();
    public abstract void read();
    public abstract void update();
    public abstract void delete();
}
