package FileHandler;

import Models.Contingent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ContingentHandler extends SuperHandler {
    private Contingent contingent;
    public ContingentHandler(int id, double price) {
        super("Contingent.txt");

        contingent = new Contingent(id, true, price, LocalDate.now());
    }

    @Override
    public void create() throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(super.getFilePath(), true));

        writer.write(contingent.getMemberId() + "," +
                contingent.getPrice() + "," +
                contingent.getDate().toString());
        // ny linje
        writer.newLine();
        writer.flush();
    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
