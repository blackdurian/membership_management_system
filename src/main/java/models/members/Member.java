package main.java.models.members;

import main.java.controllers.Season;
import main.java.models.interfaces.Searchable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Member implements Searchable {
    private Date createdDate;
    private Date modifiedDate;
    private String id, name, ICnum, email, phone;
    private Subscription subscription;

    public Member() {
    }

    public Member(String id) {
        this.id = id;
    }

    public Member(String id, String name, String ICnum, String email, String phone, Subscription subscription) {
        this.id = id;
        this.name = name;
        this.ICnum = ICnum;
        this.email = email;
        this.phone = phone;
        this.subscription = subscription;
    }

    public Member(String id, String name, String ICnum, String email, String phone) {
        this.id = id;
        this.name = name;
        this.ICnum = ICnum;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        String subText = "";
        String createdString;
        if (this.subscription != null) {
            subText = this.subscription.toString();
        }
        createdString = Season.DATE_FORMAT.format(this.createdDate);
        return String.format("%s %s %s %s %s %s", this.name, this.ICnum, this.email, this.phone, createdString, subText);
    }

    @Override
    public Boolean isFound(String searchText) {
        String subText = "";
        if (this.subscription != null) {
            subText = this.subscription.toString();
        }
        String info = String.format("%s %s %s %s %s %s", this.id, this.name, this.ICnum, this.email, this.phone, subText);
        return info.contains(searchText);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getICnum() {
        return ICnum;
    }

    public void setICnum(String ICnum) {
        this.ICnum = ICnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

}






