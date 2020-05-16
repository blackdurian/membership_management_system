package main.java.views.subscription;

import main.java.views.UiControl;
import main.java.models.members.Member;
import main.java.models.members.Subscription;
import main.java.views.payment.PaymentFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubscriptionFrame extends JFrame {
    private JButton btnBack;
    private JLabel lblHeading;
    protected JButton btnBasic;
    protected JButton btnPremium;
    protected JButton btnGeneral;
    protected JPanel rootPanel;
    protected JLabel lblMemberName;
    private JLabel lblBasicDesc;
    private JLabel lblPremiumDesc;
    private JLabel lblGeneralDesc;
    protected Member member;
    protected Subscription subscription;
    protected String plan;

    //ADD
    public SubscriptionFrame(Member member) throws HeadlessException {
        super("Subscription" + UiControl.WINDOW_TITLE);
        add(rootPanel);
        this.member = member;
        setSubscription();
        setButtonText();
        lblMemberName.setText(member.getName());
        btnBasic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processToPayment("basic", 50.0);
            }
        });
        btnPremium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processToPayment("premium", 200.0);
            }
        });
        btnGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processToPayment("general", 100.0);
            }
        });

        btnBack.addActionListener(e -> {
            UiControl.mainFrame.setVisible(true);
            dispose();
        });
    }

    protected void processToPayment(String plan, double amount) {
        subscription.setPlan(plan);
        subscription.setPrice(Subscription.PACKAGE_PRICE.get(plan));
        UiControl.thirdFrame = new PaymentFrame(this.member, this.subscription, amount);
        UiControl.initSubFrame(UiControl.thirdFrame);
        setVisible(false);
    }

protected void setSubscription(){
    if (member.getSubscription()!=null){
        this.subscription = member.getSubscription();
    }else {
        this.subscription = new Subscription();
    }
}
    protected void setButtonText(){
        btnBasic.setText("Select");
        btnGeneral.setText("Select");
        btnPremium.setText("Select");
    }
}
