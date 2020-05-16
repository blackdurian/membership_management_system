package main.java.models.users;


import java.util.Date;

public class UserLogin {
    private Date createdDate;
    private Date modifiedDate;
    private String id, username, password, type;

    public UserLogin() {
    }

    public UserLogin(Date createdDate, String id, String username, String password, String type) {
        this.createdDate = createdDate;
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
