package main.java.views.subscription;

import main.java.models.members.Member;

import java.awt.*;

public class SubscriptionUpgradeFrame extends SubscriptionFrame {
    public SubscriptionUpgradeFrame(Member member) throws HeadlessException {
        super(member);
    }

    @Override
    protected void setButtonText() {
        if (subscription != null) {
            plan = subscription.getPlan();
            btnBasic.setText("Upgrade");
            btnPremium.setText("Upgrade");
            btnGeneral.setText("Upgrade");
            switch (this.plan) {
                case "basic":
                    btnBasic.setText("Your Plan");
                    btnBasic.setEnabled(false);
                    break;
                case "premium":
                    btnPremium.setText("Your Plan");
                    btnPremium.setEnabled(false);
                    break;
                case "general":
                    btnGeneral.setText("Your Plan");
                    btnGeneral.setEnabled(false);
            }
        }
    }

    @Override
    protected void setSubscription() {
        subscription = member.getSubscription();
    }

    @Override
    protected void processToPayment(String plan, double amount) {
        super.processToPayment(plan, amount);
    }
}
