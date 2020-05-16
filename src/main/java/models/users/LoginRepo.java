package main.java.models.users;

import java.util.List;

public class LoginRepo {
   private List<UserLogin> userLoginList ;

    public List<UserLogin> getUserLoginList() {
        return userLoginList;
    }

    public void setUserLoginList(List<UserLogin> userLoginList) {
        this.userLoginList = userLoginList;
    }
}
