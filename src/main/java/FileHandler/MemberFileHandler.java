package FileHandler;

import Repositories.MemberRepository;

import java.io.*;
import java.time.LocalDate;

public class MemberFileHandler extends SuperHandler{
    private final String fileName;
    private MemberRepository memberRepository;

    public MemberFileHandler(){
        fileName = "membersList.txt";

    }

    @Override
    public void create() throws IOException {
        if (memberRepository.getMemberArrayList() == null) {
            throw new IllegalStateException("Member repository entry is empty.");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(memberRepository.getCurrentMember().toStringFile());
        writer.newLine();
        writer.flush();
    }




public void getMemberRepository() {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] attributes = line.split(",");
            memberRepository.createMember(attributes[0],
                    LocalDate.parse(attributes[1]),
                    Integer.parseInt(attributes[2]),
                    attributes[3].equalsIgnoreCase("active"),
                    attributes[4].equalsIgnoreCase("competitive")
            );
        }
    } catch (IOException e) {
        System.err.println("Error reading the file: " + e.getMessage());
    }
}

@Override
public void read(){

}

@Override
public void update() {

}

@Override
public void delete() {

}
}
