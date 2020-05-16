package main.java.views.dashboard;

import main.java.models.members.Member;
import main.java.models.members.Subscription;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class MemberTableModel extends AbstractTableModel {
    private String[] columnNames = {"Name", "IC number", "Email", "Phone", "Created Date"};
    private List<Member> memberList;


    public MemberTableModel(List<Member> memberList) {
        this.memberList = memberList;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        int size;
        if (memberList == null) {
            size = 0;
        } else {
            size = memberList.size();
        }
        return size;
    }

    public Object getValueAt(int row, int col) {
        Object temp = null;

        switch (col) {
            case 0:
                temp = memberList.get(row).getName();
                break;
            case 1:
                temp = memberList.get(row).getICnum();
                break;
            case 2:
                temp = memberList.get(row).getEmail();
                break;
            case 3:
                temp = memberList.get(row).getPhone();
                break;
            case 4:
                temp = memberList.get(row).getCreatedDate();
                break;
        }
        return temp;
    }

    public Member getMember(int row) {
        return memberList.get(row);
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public Subscription getSubcription(int row) {
        Subscription subscription;
        subscription = memberList.get(row).getSubscription();
        return subscription;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        if (col == 4) {
            return Date.class;
        } else {
            return String.class;
        }
    }

}