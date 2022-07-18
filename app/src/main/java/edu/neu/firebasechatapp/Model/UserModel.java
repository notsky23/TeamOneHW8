package edu.neu.firebasechatapp.Model;

public class UserModel {
    public String name;
    public String username;
    public String password;

    public UserModel() {
    }

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
