package main.java.views.dashboard;

import main.java.controllers.MemberControl;
import main.java.models.payments.Payment;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class PaymentTableModel extends AbstractTableModel {

    private String[] columnNames = {"Date", "Item", "Amount (RM)", "Status", "Pay By", "#"};
    private List<Payment> paymentList;
    private MemberControl memberControl;

    public PaymentTableModel(List<Payment> paymentList) {
        this.paymentList = paymentList;
        this.memberControl = new MemberControl();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        int size;
        if (paymentList == null) {
            size = 0;
        } else {
            size = paymentList.size();
        }
        return size;
    }

    public Object getValueAt(int row, int col) {
        Object temp = null;

        switch (col) {
            case 0:
                temp = paymentList.get(row).getCreatedDate();
                break;
            case 1:
                temp = paymentList.get(row).getItem();
                break;
            case 2:
                temp = String.format("%.2f", paymentList.get(row).getAmount());
                break;
            case 3:
                if (paymentList.get(row).getTransactionStatus()) {
                    temp = "Success";
                } else {
                    temp = "Fail";
                }
                break;
            case 4:
                String memberID = paymentList.get(row).getMemberId();
                temp = memberControl.getMemberById(memberID).getName();
                break;
            case 5:
                temp = paymentList.get(row).getId();
                break;
        }
        return temp;
    }

    public Payment getPayment(int row) {
        return paymentList.get(row);
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        Class result = null;
        switch (col) {
            case 0:
                result = Date.class;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                result = String.class;
                break;

        }
        return result;
    }

}
