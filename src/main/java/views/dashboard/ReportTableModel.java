package main.java.views.dashboard;

import main.java.controllers.EmployeeControl;
import main.java.controllers.PaymentControl;
import main.java.models.members.Member;
import main.java.models.members.Subscription;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class ReportTableModel extends AbstractTableModel {
    private String[] columnNames = {"Name", "IC number", "Email", "Phone", "Created Date", "Plan", "Price", "Renew Date", "Expired Date", "Available", "IssueBy"};
    private List<Member> memberList;

    public ReportTableModel(List<Member> memberList) {
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

        if (memberList.get(row).getSubscription() != null) {
            switch (col) {
                case 5:
                    temp = memberList.get(row).getSubscription().getPlan();
                    break;
                case 6:
                    temp = memberList.get(row).getSubscription().getPrice();
                    break;
                case 7:
                    temp = memberList.get(row).getSubscription().getRenewDate();
                    break;
                case 8:
                    temp = memberList.get(row).getSubscription().getExpiredDate();
                    break;
                case 9:
                    Date expiredDate = memberList.get(row).getSubscription().getExpiredDate();
                    if (expiredDate.compareTo(new Date())>=0) {
                        temp = "Available";
                    } else {
                        temp = "Expired";
                    }
                    break;
                case 10:
                    String memberId = memberList.get(row).getId();
                    String employeeId = new PaymentControl().getLatestPaymentByMemberId(memberId).getEmployeeId();
                    temp = new EmployeeControl().getEmployeeById(employeeId).getName();
                    break;
            }
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
        Class result;

        switch (col) {
            case 4:
            case 7:
            case 8:
                result = Date.class;
                break;
            case 6:
                result = Double.class;
                break;

            default:
                result = String.class;
                break;
        }
        return result;
    }

}
