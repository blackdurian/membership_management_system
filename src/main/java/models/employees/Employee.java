package main.java.models.employees;

import java.util.Date;

    public abstract class Employee  {

    public abstract Date getCreatedDate();

    public abstract void setCreatedDate(Date createdDate);

    public abstract Date getModifiedDate();

    public abstract void setModifiedDate(Date modifiedDate);

    public abstract String getID();

    public abstract void setID(String ID);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getICnum();

    public abstract void setICnum(String ICnum);

    public abstract String getEmail();

    public abstract void setEmail(String email);

    public abstract String getPhone();

    public abstract void setPhone(String phone);

}
