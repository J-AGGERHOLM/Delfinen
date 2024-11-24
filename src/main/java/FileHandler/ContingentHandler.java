package FileHandler;

import Models.Contingent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ContingentHandler extends Super {


    public ContingentHandler() {
        super("Contingent.txt");
    }

    @Override
    public void create(int id, double price) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(super.getFilePath(), true));

        Contingent contingent = new Contingent(id, price, LocalDate.now());

        writer.write(contingent.getId() + "," +
                contingent.getMemberId() + "," +
                contingent.getPrice() + "," +
                contingent.getDate().toString());

        // ny linje
        writer.newLine();
        writer.flush();
    }

    @Override
    public ArrayList<Contingent> read() {
        File file = new File(super.getFilePath());
        ArrayList<Contingent> contingents = new ArrayList<>();

        try (Scanner scan = new Scanner(new File(String.valueOf(file)))) {
            // skips header
            scan.nextLine();

            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                String[] attributes = line.split(",");


                Contingent c = new Contingent(
                        Integer.parseInt(attributes[1]),
                        Double.parseDouble(attributes[2]),
                        LocalDate.parse(attributes[3])
                );
                c.setId(Integer.parseInt(attributes[0]));

                contingents.add(c);
            }
            return contingents;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(ArrayList<Contingent> contingents) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(super.getFilePath(), false));

        for (Contingent c : contingents) {
            writer.write(c.getId() + "," +
                    c.getMemberId() + "," +
                    c.getPrice() + "," +
                    c.getDate().toString());
            // ny linje
            writer.newLine();
        }

        writer.flush();
    }
}
