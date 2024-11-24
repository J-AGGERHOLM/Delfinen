package FileHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Contingent extends Super {
    private Models.Contingent contingent;
    public Contingent(int id, double price) {
        super("Contingent.txt");

        contingent = new Models.Contingent(id, true, price, LocalDate.now());
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
