package com.leadiing.isense.Model;

public class User {

    String UserName, UserPhone,UserEmail,UserId;

    public User(String userName, String userPhone, String userEmail, String userId) {
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
        UserId = userId;
    }

    public User() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
