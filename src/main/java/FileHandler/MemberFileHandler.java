package FileHandler;

import Models.Member;
import Repositories.MemberRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MemberFileHandler extends SuperHandler{
    private final String fileName = "membersList.txt";
    private MemberRepository memberRepository;


    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
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
    public void addToFile(ArrayList<Member> memberArrayList, Member member) throws IOException {
        if ( memberArrayList == null) {
            throw new IllegalStateException("Member repository entry is empty.");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(member.toStringFile());
        writer.newLine();
        writer.flush();
    }

    @Override
    public void read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = reader.readLine()) != null) {
                while ((line = reader.readLine()) != null) {
                    String[] attributes = line.split(",");
                    Member member = new Member(attributes[0],
                            LocalDate.parse(attributes[1]),
                            Integer.parseInt(attributes[2]),
                            attributes[3].equalsIgnoreCase("active"),
                            attributes[4].equalsIgnoreCase("competitive")
                    );
                    memberRepository.getMemberArrayList().add(member);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }




    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}