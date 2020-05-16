package main.java.models.members;

import main.java.controllers.Season;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

public class Subscription {
    private String plan;
    private Boolean autoRenew;
    private Date createdDate;
    private Date modifiedDate;
    private Date renewDate;
    private Date expiredDate;
    private Double price;
    public static final Map<String, Double> PACKAGE_PRICE = Map.of("basic", 50.0, "premium", 200.0, "general", 100.0);

    public Subscription() {
    }

    public Subscription(String plan, Boolean autoRenew, Date renewDate, Date expiredDate, Double price) {
        this.plan = plan;
        this.autoRenew = autoRenew;
        this.renewDate = renewDate;
        this.expiredDate = expiredDate;
        this.price = price;
    }

    @Override
    public String toString() {
        String renewString;
        String expiredString;
        renewString = Season.DATE_FORMAT.format(this.renewDate);
        expiredString = Season.DATE_FORMAT.format(this.expiredDate);
        return String.format("%s %s %s %s %f", this.plan, this.autoRenew.toString(), renewString, expiredString, this.price);
    }

    public Boolean getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(Date renewDate) {
        this.renewDate = renewDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
