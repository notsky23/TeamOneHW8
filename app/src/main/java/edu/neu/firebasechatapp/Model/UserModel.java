package edu.neu.firebasechatapp.Model;

public class UserModel {
    public String userid;
    public String name;
    public String username;
    public String password;

    public UserModel() {
    }

    public UserModel(String userid, String name, String username, String password) {
        this.userid = userid;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
