package main.java.controllers;

import com.google.gson.Gson;
import main.java.models.members.Member;
import main.java.models.members.MemberRepo;
import main.java.models.members.Subscription;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MemberControl {

    private String PATH = PathConfig.MEMBER_REPO_PATH;
    private MemberRepo memberRepoObject;

    public MemberControl() {
        deserialize();
    }

    public void addNewMember(String name, String ICnum, String email, String phone) {
        Member member = new Member(UUID.randomUUID().toString(), name, ICnum, email, phone);
        member.setCreatedDate(new Date());
        member.setModifiedDate(new Date());
        this.memberRepoObject.addMember(member);
        SaveToFile(this.memberRepoObject);
    }

    public void deleteMemberById(String id) throws Exception {
        List<Member> managerList = new ArrayList<>();
        boolean isFound = false;
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(id)) {
                isFound = true;
                continue;
            }
            managerList.add(member);
        }
        if (isFound) {
            this.memberRepoObject.setMemberList(managerList);
            SaveToFile(this.memberRepoObject);
        } else {
            throw new Exception("No ID Found");
        }
    }

    public void editMemberById(String id, String name, String ICnum, String email, String phone) {
        List<Member> memberList = new ArrayList<>();
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(id)) {
                member.setName(name);
                member.setICnum(ICnum);
                member.setEmail(email);
                member.setPhone(phone);
                member.setModifiedDate(new Date());
                memberList.add(member);
            } else {
                memberList.add(member);
            }
        } // Save
        this.memberRepoObject.setMemberList(memberList);
        SaveToFile(this.memberRepoObject);
    }

    public List<Member> filterMemberList(String searchText) {
        List<Member> memberList = new ArrayList<>();
        try {
            for (Member member : this.memberRepoObject.getMemberList()) {
                if (member.toString().contains(searchText)) {
                    memberList.add(member);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return memberList;
    }

    public List<Member> getAllMember() {
        return this.memberRepoObject.getMemberList();
    }

    public Member getMemberById(String id) {
     /*       List<Member> memberList = this.memberRepoObject.getMemberList();
        int row = Collections.binarySearch(memberList, new Member(id), Comparator.comparing(Member::getId));
        if (row>=0){
            return memberList.get(row);
        }else {
            return new Member();
        }*/
        Member result = new Member();
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(id)) {
                result = member;
            }
        }
        return result;
    }

    public void setSubscriptionByMemberId(String memberId, String mpackage, Boolean autoRenew, Date renewDate, Date expiryDate, Double price) {
        List<Member> managerList = new ArrayList<>();
        Subscription subscription = new Subscription(mpackage, autoRenew, renewDate, expiryDate, price);
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(memberId)) {
                member.setSubscription(subscription);
                member.setModifiedDate(new Date());
                managerList.add(member);
            } else {
                managerList.add(member);
            }
        } // Save
        this.memberRepoObject.setMemberList(managerList);
        SaveToFile(this.memberRepoObject);
    }

    public void setSubscriptionByMemberId(String memberId, Subscription subscription) {
        List<Member> managerList = new ArrayList<>();
        subscription.setModifiedDate(new Date());
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(memberId)) {
                member.setSubscription(subscription);
                member.setModifiedDate(new Date());
                managerList.add(member);
            } else {
                managerList.add(member);
            }
        } // Save
        this.memberRepoObject.setMemberList(managerList);
        SaveToFile(this.memberRepoObject);
    }

    public void setRenewalByMemberId(boolean status, String memberId) {
        List<Member> managerList = new ArrayList<>();
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(memberId) && member.getSubscription() != null) {
                Subscription subscription = member.getSubscription();
                subscription.setAutoRenew(status);
                subscription.setModifiedDate(new Date());
                member.setSubscription(subscription);
                managerList.add(member);
            } else {
                managerList.add(member);
            }
        } // Save
        this.memberRepoObject.setMemberList(managerList);
        SaveToFile(this.memberRepoObject);
    }

    public void renewSubscriptionById(String id) {
        List<Member> managerList = new ArrayList<>();
        Subscription subscription;
        for (Member member : this.memberRepoObject.getMemberList()) {
            if (member.getId().equals(id)) {
                subscription = member.getSubscription();
                subscription.setRenewDate(new Date());
                subscription.setModifiedDate(new Date());
                member.setSubscription(subscription);
                member.setModifiedDate(new Date());
            }
            managerList.add(member);
        } // Save
        this.memberRepoObject.setMemberList(managerList);
        SaveToFile(this.memberRepoObject);
    }

    private void SaveToFile(MemberRepo memberRepo) { // write serialized
        Gson gson = new Gson();
        String MemberJson = gson.toJson(memberRepo);
        try {
            FileWriter fileWriter = new FileWriter(PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(MemberJson);
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void deserialize() {
        String json = readAll(PATH);
        Gson gson = new Gson();
        this.memberRepoObject = gson.fromJson(json, MemberRepo.class);  // read deserialize
        if (this.memberRepoObject == null) {    // if file empty or no exist, initial new file
            initNewFile();
        }
    }

    private void initNewFile() {
        List<Member> memberList = new ArrayList<>();
        this.memberRepoObject = new MemberRepo(memberList);
        SaveToFile(this.memberRepoObject);
    }

    private static String readAll(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
