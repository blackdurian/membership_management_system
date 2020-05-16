package main.java.models.employees;

import main.java.models.interfaces.Searchable;

import java.util.Date;

public class Staff extends Employee implements Searchable {
    private Date createdDate;
    private Date modifiedDate;
    private String ID, name, ICnum, email, phone;

    public Staff() {
    }

    public Staff(String ID, String name, String ICnum, String email, String phone) {
        this.ID = ID;
        this.name = name;
        this.ICnum = ICnum;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public Boolean isFound(String searchText) {
        String Info = String.format("%s %s %s %s %s", this.ID, this.name, this.ICnum, this.email, this.phone);
        return Info.contains(searchText);
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
}
