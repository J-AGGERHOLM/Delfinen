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
        // Parse specific path
        super("Contingent.txt");
    }

    //Creates a contingent
    @Override
    public void create(int id, int memberId, double price) throws IOException {
        // Append on .txt doesn't override
        // BufferedWriter creates a file if it doesn't exist
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(super.getFilePath(), true));

        // Create the specific contingent
        Contingent contingent = new Contingent(id, memberId, price, LocalDate.now());

        // Write to file
        writer.write(contingent.getId() + "," +
                contingent.getMemberId() + "," +
                contingent.getPrice() + "," +
                contingent.getDate().toString());

        // ny linje and close stream
        writer.newLine();
        writer.flush();
    }

    // Read all data
    @Override
    public ArrayList<Contingent> read() {
        // Array for data.
        ArrayList<Contingent> contingents = new ArrayList<>();

        // With try it closes the stream automatic
        try (Scanner scan = new Scanner(new File(String.valueOf(super.getFilePath())))) {
            // skips header
            scan.nextLine();

            // Read the whole file
            while (scan.hasNextLine()) {
                // Gets the line. Split in array on ","
                String line = scan.nextLine();
                String[] attributes = line.split(",");

                // Insert in object
                Contingent c = new Contingent(
                        Integer.parseInt(attributes[0]),

                        Integer.parseInt(attributes[1]),

                        Double.parseDouble(attributes[2]),
                        LocalDate.parse(attributes[3])
                );
                c.setId(Integer.parseInt(attributes[0]));

                // Add data to ArrayList
                contingents.add(c);
            }

            return contingents;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Something went wrong
        return null;
    }

    // Delete specific Contingent
    @Override
    public void delete(ArrayList<Contingent> contingents) throws IOException {
        // Override the file
        // BufferedWriter creates a file if it doesn't exist
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(super.getFilePath(), false));

        writer.write("Id,MemberId,Price,Birthday");
        // ny linje
        writer.newLine();
        // Write the array in file
        for (Contingent c : contingents) {
            writer.write(c.getId() + "," +
                    c.getMemberId() + "," +
                    c.getPrice() + "," +
                    c.getDate().toString());
            // ny linje
            writer.newLine();
        }

        // Close the stream
        writer.flush();
    }
}
