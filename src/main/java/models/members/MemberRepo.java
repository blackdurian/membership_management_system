package main.java.models.members;

import java.util.List;

public class MemberRepo {

   private List<Member> memberList;

    public MemberRepo(List<Member> memberList) {
        this.memberList = memberList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void addMember(Member member){
        memberList.add(member);
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

}
