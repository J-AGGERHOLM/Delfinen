package repositories;

import Models.Member;

public class ContingentRepository {
    private Member member;

    public ContingentRepository(){}

    public double calculatePrice(){
        return 0.0;
    }

    public boolean createMemberPaid(){
        return true;
    }
}
