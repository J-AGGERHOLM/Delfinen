package FileHandler;

import Enums.SwimmingDisciplines;
import Models.CompetitiveSwimmer;
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
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");

                if (attributes.length < 5) {
                    System.err.println("Invalid entry: " + line);
                    continue; // Skip malformed entries
                }

                boolean isCompetitive = attributes[4].equalsIgnoreCase("competitive");

                if (isCompetitive) {
                    if (attributes.length < 6) {
                        System.err.println("Competitive member missing discipline: " + line);
                        continue; // Skip entries missing the discipline
                    }
                    try {
                        // Check how many disciplines the swimmers has and adds them to an arrayList

                        ArrayList<SwimmingDisciplines> chosenDisciplines = new ArrayList<>();
                        chosenDisciplines.add(SwimmingDisciplines.values()[Integer.parseInt(attributes[6]) - 1]);
                        if (attributes.length > 7){
                            chosenDisciplines.add(SwimmingDisciplines.values()[Integer.parseInt(attributes[7]) - 1]);
                        } if (attributes.length > 8){
                            chosenDisciplines.add(SwimmingDisciplines.values()[Integer.parseInt(attributes[8]) - 1]);
                        } if (attributes.length > 9) {
                            chosenDisciplines.add(SwimmingDisciplines.values()[Integer.parseInt(attributes[9]) - 1]);
                        }
                        Member competitiveSwimmer = new CompetitiveSwimmer(
                                attributes[0], // Name
                                LocalDate.parse(attributes[1]), // Birthday
                                Integer.parseInt(attributes[2]), // ID
                                attributes[3].equalsIgnoreCase("active"), // Activity
                                true, // Competitive
                                Boolean.parseBoolean(attributes[5])
                        );

                        competitiveSwimmer.setChosenDisciplines(chosenDisciplines);
                        //here it adds a competitive member to the member arraylist
                        memberRepository.getMemberArrayList().add(competitiveSwimmer);


                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid discipline in entry: " + line);
                    }


                } else {
                    Member member = new Member(
                            attributes[0], // Name
                            LocalDate.parse(attributes[1]), // Birthday
                            Integer.parseInt(attributes[2]), // ID
                            attributes[3].equalsIgnoreCase("active"), // Activity
                            false, // Competitive
                            Boolean.parseBoolean(attributes[5])
                    );


                    //here it adds a con-competitive member to the member arraylist
                    memberRepository.getMemberArrayList().add(member);

                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }




    public void update() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        for (Member m : memberRepository.getMemberArrayList()){
            writer.write(m.toStringFile());
            writer.newLine();
            writer.flush();
        }

    }

    }


