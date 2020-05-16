package main.java.views.payment;

import main.java.controllers.MemberControl;
import main.java.controllers.PaymentControl;
import main.java.controllers.Season;
import main.java.views.UiControl;
import main.java.models.members.Member;
import main.java.models.members.Subscription;
import main.java.views.dashboard.DashboardFrame;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class PaymentFrame extends JFrame {

    private JButton btnPaypal;
    private JButton btnCash;
    private JButton btnCredit;
    private JButton btnBack;
    private JPanel rootPanel;
    private JLabel lblMemberName;
    private JLabel lblPlan;
    private JLabel lblDueDate;
    private JLabel lblAmount;
    private JCheckBox cbAutoRenew;
    private JTextArea txtAreaMemo;
    private JPanel subPanel;
    private double amount;
    private Member member;
    private Subscription subscription;
    private Date dateToday;
    private Date expiredDate;

    // TODO :  inheritance instead of if else
    // TODO: LOGGER
    public PaymentFrame(Member member, Subscription subscription, double amount) {
        super("Payment" + UiControl.WINDOW_TITLE);
        add(rootPanel);
        this.amount = amount;
        this.subscription = subscription;
        this.member = member;
        this.dateToday = new Date();
        setExpiredDate();
        String amountText = String.format("RM %.2f", this.amount);
        lblAmount.setText(amountText);
        lblPlan.setText(subscription.getPlan());
        lblDueDate.setText(expiredDate.toString());
        lblMemberName.setText(member.getName());
        PaymentControl paymentControl = new PaymentControl();
        btnCash.addActionListener(e -> {
            // TODO: new Thread  new Runnable()
            updateSubscription();
            paymentControl.addNewPayment(true, amount, txtAreaMemo.getText() + "\n\n Paid by Cash", subscription.getPlan(), member.getId(), Season.getUserId());
            toNextFrame();
        });
        btnPaypal.addActionListener(e -> {
            // TODO: new Thread  new Runnable()
            updateSubscription();
            paymentControl.addNewPayment(true, amount, txtAreaMemo.getText() + "\n\n\t\t Paid by Paypal", subscription.getPlan(), member.getId(), Season.getUserId());
            toNextFrame();
        });
        btnCredit.addActionListener(e -> {
            // TODO: new Thread  new Runnable()
            updateSubscription();
            paymentControl.addNewPayment(true, amount, txtAreaMemo.getText() + "\n\n\t\t Paid by Credit Card", subscription.getPlan(), member.getId(), Season.getUserId());
            toNextFrame();
        });
        btnBack.addActionListener(e -> toPreviousFrame());
    }

    protected void updateSubscription() {
        MemberControl memberControl = new MemberControl();
        if (cbAutoRenew.isSelected()) {
            subscription.setAutoRenew(true);
        } else {
            subscription.setAutoRenew(false);
        }
        subscription.setRenewDate(dateToday);
        subscription.setCreatedDate(dateToday);
        subscription.setExpiredDate(expiredDate);
        memberControl.setSubscriptionByMemberId(member.getId(), subscription);
    }

    protected void setExpiredDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToday);
        Date myExpiredDate = subscription.getExpiredDate();
        if (myExpiredDate != null) {
            if (myExpiredDate.compareTo(new Date())> 0) {
                calendar.setTime(myExpiredDate);
            }
        }
        calendar.add(Calendar.YEAR, 1);
        this.expiredDate = calendar.getTime();
    }

    private void toNextFrame() {
        UiControl.secondFrame = null;
        DashboardFrame dashboardFrame = new DashboardFrame();
        dashboardFrame.setTabbedSelectedIndex(1);
        dashboardFrame.selectLastPaymentRow();
        UiControl.mainFrame = dashboardFrame;
        UiControl.initMainFrame(UiControl.mainFrame);
        dispose();
    }

    public void toPreviousFrame() {
        UiControl.secondFrame.setVisible(true);
        dispose();
    }
}
