package main.java.models.payments;

import java.util.List;

public class PaymentRepo {
    private List<Payment> paymentList;

    public PaymentRepo(List<Payment> payments) {
        this.paymentList = payments;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
