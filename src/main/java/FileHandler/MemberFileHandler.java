package FileHandler;

import Models.Member;
import Repositories.MemberRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberFileHandler {
    private final String fileName = "membersList.txt";
    private MemberRepository memberRepository;


    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public void create() throws IOException {
        if (memberRepository.getMemberArrayList() == null) {
            throw new IllegalStateException("Member repository entry is empty.");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(memberRepository.getCurrentMember().toStringFile());
        writer.newLine();
        writer.flush();
    }


    public void read() {
        ArrayList<Member> members;
        try (Scanner scan = new Scanner(new File(fileName))){

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] attributes = line.split(",");
                Member member = new Member(attributes[0],
                        LocalDate.parse(attributes[1]),
                        Integer.parseInt(attributes[2]),
                        attributes[3].equalsIgnoreCase("active"),
                        attributes[4].equalsIgnoreCase("competitive")
                );
                memberRepository.getMemberArrayList().add(member);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }


    public void update(String membersName) throws IOException {
        for (Member m : memberRepository.getMemberArrayList()){
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(memberRepository.getCurrentMember().toStringFile());
            writer.newLine();
            writer.flush();
        }

    }



}