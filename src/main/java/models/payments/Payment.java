package main.java.models.payments;

import main.java.controllers.Season;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment implements Comparable<Payment> {
    private Date createdDate;
    private Date modifiedDate;
    private Boolean transactionStatus;
    private Double amount;
    private String id, memo, memberId, item, employeeId;

    public Payment() {
    }

    public Payment(Date createdDate, Boolean transactionStatus, Double amount, String id, String memo, String item) {
        this.createdDate = createdDate;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.id = id;
        this.memo = memo;
        this.item = item;
    }

    public Payment(Date createdDate, Boolean transactionStatus, Double amount, String id, String memo, String item, String memberId, String employeeId) {
        this.createdDate = createdDate;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.id = id;
        this.memo = memo;
        this.item = item;
        this.memberId = memberId;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        String createdString;
        createdString = Season.DATE_FORMAT.format(this.createdDate);
        String result = String.format("%s %.2f %s %s", createdString, this.amount, this.id, this.item);
        return result;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Boolean transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int compareTo(Payment payment) {
        if (this.getCreatedDate().equals(payment.getCreatedDate())) {
            return 0;
        } else if (this.getCreatedDate().after(payment.getCreatedDate())) {
            return 1;
        } else {
            return -1;
        }
    }
}
