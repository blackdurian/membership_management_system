package main.java.controllers;

import com.google.gson.Gson;
import main.java.models.users.LoginRepo;
import main.java.models.users.UserLogin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginControl {
    String PATH = PathConfig.USER_LOGIN_REPO_PATH;
    LoginRepo loginRepoObject;

    public LoginControl() {
        deserialize();
    }

    public void addNewUser(String id, String username, String password, String type) {
        UserLogin userLogin = new UserLogin(new Date(), id, username, getSHA(password), type);
        userLogin.setModifiedDate(new Date());
        //get exist staff list
        List<UserLogin> userLoginList = this.loginRepoObject.getUserLoginList();
        userLoginList.add(userLogin);
        this.loginRepoObject.setUserLoginList(userLoginList);
        SaveToFile(this.loginRepoObject);
    }

    public void deleteUserById(String id) throws Exception {
        List<UserLogin> userLoginList = new ArrayList<>();
        boolean isFound = false;
        for (UserLogin user : this.loginRepoObject.getUserLoginList()) {
            if (user.getId().equals(id)) {
                isFound = true;
            } else {
                userLoginList.add(user);
            }
        }
        if (isFound) {
            this.loginRepoObject.setUserLoginList(userLoginList);
            SaveToFile(this.loginRepoObject);
        } else {
            throw new Exception("No ID Found");//;'
        }
    }

    public void editPasswordById(String id, String password) {
        List<UserLogin> userLoginList = new ArrayList<>();
        for (UserLogin user : this.loginRepoObject.getUserLoginList()) {
            if (user.getId().equals(id)) {
                user.setPassword(getSHA(password));
                user.setModifiedDate(new Date());
                userLoginList.add(user);
            } else {
                userLoginList.add(user);
            }
        }
        this.loginRepoObject.setUserLoginList(userLoginList);
        SaveToFile(this.loginRepoObject);
    }

    public void editUsernameById(String id, String username) {
        List<UserLogin> userLoginList = new ArrayList<>();
        for (UserLogin user : this.loginRepoObject.getUserLoginList()) {
            if (user.getId().equals(id)) {
                user.setUsername(username);
                user.setModifiedDate(new Date());
                userLoginList.add(user);
            } else {
                userLoginList.add(user);
            }
        }
        this.loginRepoObject.setUserLoginList(userLoginList);
        SaveToFile(this.loginRepoObject);
    }

    public List<UserLogin> getAllUser() {
        return this.loginRepoObject.getUserLoginList();
    }

    public Boolean isPasswordVerify(String username, String password) throws Exception {
        boolean isFoundUsername = false;
        boolean isPasswordValid = false;
        for (UserLogin user : this.loginRepoObject.getUserLoginList()) {
            if (user.getUsername().equals(username)) {
                isFoundUsername = true;
                if (user.getPassword().equals(getSHA(password))) {
                    isPasswordValid = true;
                    break;
                }

            }
        }
        if (!isFoundUsername) {
            throw new Exception("Invalid Username");
        }
        return isPasswordValid;
    }

    public UserLogin getUserById(String id) {
        UserLogin result = new UserLogin();
        try {
            for (UserLogin user : this.loginRepoObject.getUserLoginList()) {
                if (user.getId().equals(id)) {
                    result = user;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserLogin getUserByUsername(String username) {
        UserLogin result = new UserLogin();
        try {
            for (UserLogin user : this.loginRepoObject.getUserLoginList()) {
                if (user.getUsername().equals(username)) {
                    result = user;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getSHA(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");   // Static getInstance method is called with hashing SHA
            byte[] messageDigest = md.digest(input.getBytes()); // and return array of byte
            BigInteger no = new BigInteger(1, messageDigest);  // Convert byte array into signum representation
            String hashtext = no.toString(16);// Convert message digest into hex value
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void SaveToFile(LoginRepo loginRepo) {
        //write serialized
        Gson gson = new Gson();
        String EmployeeJson = gson.toJson(loginRepo);
        //save to file
        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(PATH);
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(EmployeeJson);
            // Always close files.
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void deserialize() {
        String json = readAll(PATH);
        Gson gson = new Gson();
        //read deserialize
        this.loginRepoObject = gson.fromJson(json, LoginRepo.class);
        //if file no exist or empty, initial new login-Repo.txt
        if (this.loginRepoObject == null) {
            initNewFile();
        }
    }

    private void initNewFile() {
        List<UserLogin> userLogins = new ArrayList<>();
        this.loginRepoObject = new LoginRepo();
        this.loginRepoObject.setUserLoginList(userLogins);
        SaveToFile(this.loginRepoObject);
    }

    private static String readAll(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
