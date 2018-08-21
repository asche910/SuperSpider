package com.bean;

public class User {

    private String user;    // *
    private String password = "abc123456";    // abc123456
    private String name;    // *

    private String imgAvatar;
    private String signature;
    private String email;

    private int userGroup = 0;  // 0
    private int money = 100;  //100


    public User(String user, String password, String name, String imgAvatar, String signature, String email, int userGroup, int money) {
        this.user = user;
        this.password = password;
        this.name = name;
        this.imgAvatar = imgAvatar;
        this.signature = signature;
        this.email = email;
        this.userGroup = userGroup;
        this.money = money;
    }

    public User(){}


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(int userGroup) {
        this.userGroup = userGroup;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
