package main.java.views.payment;

import main.java.views.UiControl;
import main.java.models.members.Member;
import main.java.models.members.Subscription;

public class PaymentRenewFrame extends PaymentFrame {

    public PaymentRenewFrame(Member member, Subscription subscription, double amount) {
        super(member, subscription, amount);
    }

    @Override
    public void toPreviousFrame() {
        UiControl.secondFrame = null;
        UiControl.mainFrame.setVisible(true);
        dispose();
    }
}
